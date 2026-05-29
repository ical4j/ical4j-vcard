## Why

Two validators reject vCards that are valid under RFC 6350, so conformant input fails `validate()`:

- **GENDER**: a value carrying both a sex component and an identity component (e.g. `M;Fellow`) is rejected, even though RFC 6350 §6.2.7 explicitly permits `sex ";" identity`.
- **FN**: `Entity.validate()` requires *exactly one* FN, but RFC 6350 §6.2.1 defines FN cardinality as `1*` (one or more), so a card with multiple FN values is wrongly rejected.

## What Changes

- Fix GENDER value validation so values of the form `sex`, `sex;identity`, `;identity`, and empty are accepted, while the sex component (when present) is still constrained to the allowed tokens (`M`, `F`, `O`, `N`, `U`).
- Fix FN entity-level cardinality so one *or more* FN properties are valid; a card with zero FN remains invalid.
- Align FN-missing reporting with the rest of `Entity.validate()` only insofar as required to express "one or more" — no broader refactor of the throw-vs-accumulate behaviour is in scope here.

## Capabilities

### New Capabilities
- `vcard-validation`: Defines the RFC 6350 conformance rules the library enforces when validating vCard entities and properties. This change establishes the capability with the GENDER value rule and the FN cardinality rule; other property/entity rules already implemented in code can be documented here incrementally by later changes.

### Modified Capabilities
<!-- None: no existing spec captures validation behaviour yet. -->

## Impact

- `src/main/java/net/fortuna/ical4j/vcard/validate/IdentificationPropertyValidators.java` — GENDER validator rule.
- `src/main/java/net/fortuna/ical4j/vcard/Entity.java` — FN cardinality in `validate()`.
- Behavioural change for callers of `VCard.validate()` / `Entity.validate()`: previously-rejected conformant cards now pass. Not a breaking API change; it relaxes overly-strict validation.
- Tests under `src/test/java/.../vcard/` covering GENDER and FN validation.
