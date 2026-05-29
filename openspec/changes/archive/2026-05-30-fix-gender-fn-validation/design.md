## Context

Validation in this library delegates property-value and parameter-cardinality
rules to `PropertyValidator`/`ValidationRule` (from ical4j core), while
entity-level cardinality rules are hand-coded in `Entity.validate()`.

Two rules are wrong relative to RFC 6350:

1. **GENDER** (`IdentificationPropertyValidators.GENDER`) uses a `ValueMatch`
   rule. `ValueMatch` is evaluated with `String.matches()` — a *full-string*
   match — against `"(?i)" + join("|", "N","F","M","O","U")`, i.e.
   `(?i)N|F|M|O|U`. `Gender.getValue()` returns `sex + ";" + text` when an
   identity component is present, so `M;Fellow` never matches and is rejected.

2. **FN** (`Entity.validate()`) calls `assertOne(PropertyName.FN)`, which throws
   when the count is not exactly 1. RFC 6350 §6.2.1 cardinality for FN is `1*`
   (one or more), so a card with multiple FN values is wrongly rejected.

## Goals / Non-Goals

**Goals:**
- Accept GENDER values of the form `sex`, `sex;identity`, `;identity`, and empty,
  while still rejecting a non-empty sex component outside `{M,F,O,N,U}`.
- Accept one *or more* FN properties per entity; still reject zero FN.
- Add regression tests for both rules.

**Non-Goals:**
- Refactoring the broader throw-vs-accumulate inconsistency in
  `Entity.validate()` (tracked separately as finding #4 from the review).
- Adding the missing `*1` entity-level cardinality checks (N, BDAY, ANNIVERSARY,
  GENDER, PRODID, REV, UID) — separate concern.
- Changing GENDER's `TEXT_VALUE` (VALUE-type) rule, which is correct.

## Decisions

### GENDER: widen the `ValueMatch` regex to the structured value grammar

Replace the alternation-of-tokens regex with one that matches the full
RFC 6350 §6.2.7 value grammar `[sex] [";" identity]`:

```
(?i)(M|F|O|N|U)?(;.*)?
```

Built from the existing `Gender.*` constants so the allowed tokens stay in one
place, e.g.:
`"(?i)(" + String.join("|", Gender.MALE, Gender.FEMALE, Gender.OTHER, Gender.NONE, Gender.UNKNOWN) + ")?(;.*)?"`.

Because `ValueMatch` is a full-string match:
- `M`, `M;Fellow`, `;Agender`, `` (empty) → match → valid.
- `Z;Fellow`, `MM` → no full match → rejected.

**Alternative considered — predicate `ValidationRule`:** split the value on `;`
and check the first component against the allowed set. More explicit about the
structure, but diverges from how every other enumerated value in this package is
validated (all use `ValueMatch` regexes). Chosen the regex for consistency and
minimal surface; the predicate remains a clean future option if the grammar
grows.

### FN: assert "one or more" instead of "exactly one"

Replace `assertOne(PropertyName.FN)` with an at-least-one check. Keep `assertOne`
for VERSION (whose cardinality genuinely is exactly 1). Introduce a sibling
helper mirroring `assertOne`'s existing contract (throws `ValidationException`
when the rule is violated) so behaviour stays consistent with VERSION and the
non-goal of not reworking error reporting holds:

```java
private void assertOneOrMore(final PropertyName propertyId) {
    if (getProperties(propertyId.toString()).isEmpty()) {
        throw new ValidationException("Property [" + propertyId + "] must be specified at least once");
    }
}
```

`assertOne(FN)` → `assertOneOrMore(FN)`.

**Alternative considered:** accumulate a `ValidationEntry` instead of throwing.
Rejected here to keep this change scoped to the cardinality bug; the
throw-vs-accumulate cleanup is finding #4 and would touch VERSION too.

## Risks / Trade-offs

- **Regex permissiveness** → `(;.*)?` accepts any identity text including an
  empty one (`M;`). RFC treats identity as free text, so this is acceptable; the
  sex token remains strictly constrained.
- **Behavioural relaxation for `validate()` callers** → cards previously
  rejected now pass. This is the intended fix and is not an API break; any caller
  asserting the *old* (incorrect) rejection would need updating, but that would
  have been asserting non-conformant behaviour. → Mitigated by adding explicit
  regression tests pinning the new, correct behaviour.
