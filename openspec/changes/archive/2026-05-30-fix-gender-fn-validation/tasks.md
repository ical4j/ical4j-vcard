## 1. Fix GENDER value validation

- [x] 1.1 In `IdentificationPropertyValidators.GENDER`, replace the `ValueMatch` regex with one matching the structured value `[sex] [";" identity]`, built from the `Gender.*` constants: `"(?i)(" + String.join("|", Gender.MALE, Gender.FEMALE, Gender.OTHER, Gender.NONE, Gender.UNKNOWN) + ")?(;.*)?"`
- [x] 1.2 Confirm the `TEXT_VALUE` rule on GENDER is unchanged and still applied

## 2. Fix FN entity cardinality

- [x] 2.1 Add a private `assertOneOrMore(PropertyName)` helper to `Entity` that throws `ValidationException` only when the property count is zero
- [x] 2.2 In `Entity.validate()`, change `assertOne(PropertyName.FN)` to `assertOneOrMore(PropertyName.FN)`; leave `assertOne(PropertyName.VERSION)` unchanged

## 3. Tests

- [x] 3.1 Add GENDER validation tests: `M;Fellow`, bare `F`, `;Agender`, and empty value all validate without error; `Z;Fellow` produces an error
- [x] 3.2 Add FN cardinality tests: an entity with one FN and an entity with two FN both validate without an FN-cardinality error; an entity with no FN is rejected
- [x] 3.3 Run the test suite (`./gradlew test`) and confirm all tests pass

## 4. Verify against spec

- [x] 4.1 Confirm each scenario in `specs/vcard-validation/spec.md` is covered by a test
- [x] 4.2 Run `openspec validate fix-gender-fn-validation` and resolve any issues
