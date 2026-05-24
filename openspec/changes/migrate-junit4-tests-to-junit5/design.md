## Context

The Gradle test task uses `useJUnitPlatform()`. The test runtime classpath currently contains:
- `org.junit.jupiter:junit-jupiter:5.14.1` (declared in `libs.versions.toml` under the misleading version key `junitVintage`)
- `org.junit.platform:junit-platform-launcher` (declared as `testRuntimeOnly`)
- `junit:junit:4.13.2` (transitive via `org.codehaus.groovy:groovy-test`, which is why the JUnit 4 tests compile)
- `org.spockframework:spock-core` (runs Groovy specs)

What is **not** on the classpath: `org.junit.vintage:junit-vintage-engine`. The JUnit Platform launcher only discovers tests via registered engines, so every class annotated with `org.junit.Test` (~67 files) is silently skipped at the discovery phase. The Groovy/Spock specs are unaffected and stay healthy throughout this change.

Two parameterized base classes carry the bulk of the property/parameter coverage:
- `net.fortuna.ical4j.vcard.PropertyTest` — extended by 41 subclasses under `property/`.
- `net.fortuna.ical4j.vcard.ParameterTest` — extended by 6 subclasses under `parameter/`.

In the JUnit 4 model, each subclass overrides a `static Collection<Object[]> parameters()` annotated with `@Parameters`, and the inherited `@Test` methods consume the parameter set via constructor injection. JUnit 5 has no class-level parameterized runner, so this inheritance shape needs to change.

## Goals / Non-Goals

**Goals:**
- All Java test classes use only `org.junit.jupiter.api` and `org.junit.jupiter.params` APIs.
- Tests previously hidden by missing vintage engine are visible in CI reports as either passing, failing, or explicitly disabled.
- A single test framework remains in active use (Jupiter); vintage engine is removed once unused.
- The parameterized base-class pattern is preserved but simplified — base classes hold the test methods, subclasses provide data only.
- The misleading `junitVintage` version key is renamed to `junit`.

**Non-Goals:**
- Migrating or restructuring Groovy/Spock specs.
- Adding new test coverage. Tests that surface as failing under the vintage bridge are triaged (fix the test or the underlying code) but no new scenarios are written as part of this change.
- Bumping the JUnit Jupiter or other framework versions.
- Removing the transitive `junit:junit:4.13.2` brought in by Groovy. It is harmless once nothing imports it.

## Decisions

### Decision 1: Bridge with vintage engine before migrating

**Decision:** Add `org.junit.vintage:junit-vintage-engine` as `testRuntimeOnly` first, triage the failures it surfaces, then migrate file-by-file, then drop vintage.

**Rationale:** The current state is undetected silent skips. The fastest path to "we know what's actually broken" is registering the engine that runs those tests. Skipping this step means migrating ~67 files blind — every Jupiter rewrite would simultaneously be the first execution of that test in months, mixing two failure modes (migration bugs vs. pre-existing failures).

**Alternatives considered:**
- *Big-bang rewrite without vintage*: One large PR rewrites all tests. Rejected — large diff, high review cost, conflates "broken before" with "broken by migration."
- *Permanently keep vintage*: Smallest patch. Rejected — leaves the codebase with two test frameworks indefinitely and contradicts the stated goal of a single modern stack.

### Decision 2: Reshape the parameterized base classes

**Decision:** `PropertyTest` and `ParameterTest` become abstract classes containing `@ParameterizedTest` + `@MethodSource("parameters")` methods that accept arguments directly. Each subclass overrides `static Stream<Arguments> parameters()` to supply data. No constructor state, no `@RunWith`.

**Rationale:** Jupiter resolves `@MethodSource` by name at the test class being executed, including methods inherited from `static` providers defined in subclasses. This keeps the "one place to add a property test, one method to supply data" ergonomics of the current design while removing the JUnit 4 runner machinery and the awkward constructor-stored fields.

**Alternatives considered:**
- *Flatten each subclass*: Inline test methods into every subclass. Rejected — would multiply ~7 methods × 47 subclasses (~330 method copies). The base-class pattern earned its keep here.
- *`@TestFactory` returning `DynamicTest`s*: Possible but obscures the parameter set in stack traces and Gradle's test reports. Rejected for ergonomics.

### Decision 3: Phase boundaries are PR boundaries

**Decision:** Each phase lands as its own PR.
- **Phase 1 PR:** Add vintage engine; rename version key; triage and resolve any test failures (fix or `@Disabled` with linked issue). CI green at end.
- **Phase 2 PRs:** Migrate tests in batches. Suggested batch boundaries: (a) `parameter/` directory (6 files + `ParameterTest` base), (b) the 21 root `vcard/` non-base tests, (c) `property/` directory in 2-3 sub-batches grouped by similarity (41 files + `PropertyTest` base). Each batch keeps the vintage engine in place so unmigrated tests still run.
- **Phase 3 PR:** Remove `junit-vintage-engine` from `build.gradle` and `libs.versions.toml`. Verify no `org.junit.Test`/`org.junit.Assert`/`org.junit.runner.*` imports remain.

**Rationale:** Reviewable diffs, CI stays green throughout, easy to bisect if a regression appears.

### Decision 4: Rename `junitVintage` to `junit` now (phase 1)

**Decision:** Rename the version reference in `libs.versions.toml` during phase 1, even though it's cosmetic.

**Rationale:** Phase 1 is the natural moment — we're touching the JUnit section, and the misleading name (vintage points at jupiter) actively misleads anyone reading the build file. Leaving it would also create confusion when we *actually* add a vintage entry alongside it.

## Risks / Trade-offs

- **[Risk] Phase 1 surfaces many failing tests at once** → Time-box triage. For genuinely broken-by-drift tests with no quick fix, mark `@Ignore` (JUnit 4) during phase 1 with a TODO and a linked issue; convert to `@Disabled` in phase 2 when the file is migrated. Don't let triage block the bridge from landing.
- **[Risk] The `PropertyTest`/`ParameterTest` `@MethodSource` reshape regresses parameter coverage** → Migrate the base + one subclass first as a pilot; verify report XML shows the same count of test method × parameter row combinations as before (under vintage). Only then proceed with the bulk.
- **[Risk] Long-lived branch drifts if phase 2 is spread over many PRs** → Each phase 2 PR is independently mergeable because vintage stays in place. Rebase friction is minimal — the test files don't overlap with active feature work.
- **[Risk] External consumers depend on `PropertyTest`/`ParameterTest` as a test base** → Low probability (test classes aren't published in the main jar), but worth a note in the release/CHANGELOG entry. If anyone has been (re)using them, they get the same migration recipe.
- **[Trade-off] Keeping the transitive `junit:junit:4.13.2` from Groovy** → Acceptable. It costs nothing at runtime and removing it would mean restructuring Groovy test dependencies for no real gain.

## Migration Plan

1. **Phase 1 (this change, PR #1):** vintage bridge + version-key rename + triage. CI green with all previously-hidden tests now running.
2. **Phase 2 (subsequent PRs):** mechanical Jupiter migration in batches. Vintage stays.
3. **Phase 3 (final PR):** drop vintage; verify no `org.junit.*` (non-jupiter) imports remain via grep gate.

Rollback: each phase is independently revertable. Phase 1 revert restores the silent-skip state (no harm); phase 2 reverts restore vintage execution of the affected files; phase 3 revert puts vintage back.
