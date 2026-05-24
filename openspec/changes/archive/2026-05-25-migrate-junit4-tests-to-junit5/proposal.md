## Why

The project's Java test suite is written in JUnit 4 style (`@RunWith(Parameterized.class)`, `org.junit.Test`, `org.junit.Assert`), but the Gradle build runs with `useJUnitPlatform()` and only the JUnit Jupiter engine on the test runtime classpath. There is no `junit-vintage-engine`, so the JUnit Platform launcher silently skips every JUnit 4 test class at discovery time. Build reports under `build/test-results/test/` confirm only Spock specs and the handful of true Jupiter tests are executed; ~67 JUnit 4 test classes — including the parameterized `PropertyTest`/`ParameterTest` hierarchies covering most vCard property and parameter behavior — are not running at all. This silently masks regressions. The end state is a single, modern test framework (JUnit 5 Jupiter) with no vintage shim, but we will get there in phases so CI is green before the bulk migration begins.

## What Changes

- Add `junit-vintage-engine` to the test runtime classpath as a temporary bridge so the existing JUnit 4 tests are discovered and executed under JUnit Platform. Fix any failures that surface.
- Rename the misleading version key `junitVintage` in `gradle/libs.versions.toml` to `junit` (it points at `junit-jupiter` and was never wired to vintage).
- Migrate all ~67 JUnit 4 test classes to JUnit 5 Jupiter:
  - Replace `@RunWith(Parameterized.class)` + `@Parameters` with `@ParameterizedTest` + `@MethodSource`.
  - Replace `org.junit.Test`/`Before`/`After`/`Assert` with the `org.junit.jupiter.api` equivalents.
  - Restructure the `PropertyTest` / `ParameterTest` parameterized base-class pattern: the base class provides `@ParameterizedTest` methods that take arguments directly; the 41 `PropertyTest` subclasses and 6 `ParameterTest` subclasses become `@MethodSource` data providers (no constructor state).
- Remove `junit-vintage-engine` once no JUnit 4 imports remain. **BREAKING** for downstream test inheritance: any external consumer extending `PropertyTest`/`ParameterTest` would need to follow the same migration. (The test classes are not published, so external impact is unlikely.)

## Capabilities

### New Capabilities
- `test-framework`: Defines the test framework, engines on the JUnit Platform classpath, and the conventions (Jupiter APIs, parameterized test pattern, base-class shape) that test code in this repository must follow.

### Modified Capabilities
<!-- None. No published-behavior specs exist; this change reshapes test infrastructure, captured by the new `test-framework` capability. -->

## Impact

- **Code**: `src/test/java/**` — ~67 Java test classes rewritten. Groovy/Spock specs under `src/test/groovy/**` are unaffected (already run via spock-core on JUnit Platform).
- **Build**: `gradle/libs.versions.toml` (add vintage engine entry, rename key); `build.gradle` (add `testRuntimeOnly libs.junit.vintage` during phase 1, remove in phase 3).
- **CI**: Phase 1 will likely surface previously-hidden test failures. Each must be triaged (fix vs. mark `@Disabled` with an issue link) before phase 2 begins.
- **Dependencies**: Temporary addition of `org.junit.vintage:junit-vintage-engine`. The transitive `junit:junit:4.13.2` from `groovy-test` continues to satisfy compile-time JUnit 4 imports during phase 2 and disappears as a usage once phase 2 completes (the artifact itself remains on the classpath via Groovy, which is fine).
- **Risk**: Low. Vintage is the JUnit team's supported bridge; migration is mechanical per file. The base-class restructure is the only design choice and is contained to two files plus their subclasses.
