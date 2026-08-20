# vcard-validation Specification

## Purpose

Defines validation rules for vCard property values and entity-level property cardinality in accordance with RFC 6350.

## Requirements

### Requirement: GENDER value accepts an optional identity component
The library SHALL validate a GENDER property value as the RFC 6350 §6.2.7 form `[sex] [";" identity]`, where `sex`, when present, is exactly one of `M`, `F`, `O`, `N`, or `U` (case-insensitive), and `identity` is free text. Validation SHALL accept a bare sex component, a sex component followed by an identity component, an identity component with an empty sex component, and an empty value. Validation SHALL reject a value whose non-empty sex component is not one of the allowed tokens.

#### Scenario: Sex component with identity text is valid
- **WHEN** a GENDER property with value `M;Fellow` is validated
- **THEN** validation produces no error for the GENDER property

#### Scenario: Bare sex component is valid
- **WHEN** a GENDER property with value `F` is validated
- **THEN** validation produces no error for the GENDER property

#### Scenario: Empty sex component with identity text is valid
- **WHEN** a GENDER property with value `;Agender` is validated
- **THEN** validation produces no error for the GENDER property

#### Scenario: Invalid sex token is rejected
- **WHEN** a GENDER property with value `Z;Fellow` is validated
- **THEN** validation produces an error for the GENDER property

### Requirement: FN cardinality is one or more per entity
An entity SHALL be valid only if it contains at least one FN property, in accordance with the RFC 6350 §6.2.1 cardinality of `1*` (one or more). The library SHALL NOT reject an entity solely because it contains more than one FN property. The library SHALL report an error for an entity that contains no FN property.

#### Scenario: Single FN is valid
- **WHEN** an entity containing exactly one FN property is validated
- **THEN** validation produces no FN-cardinality error

#### Scenario: Multiple FN properties are valid
- **WHEN** an entity containing two or more FN properties is validated
- **THEN** validation produces no FN-cardinality error

#### Scenario: Missing FN is invalid
- **WHEN** an entity containing no FN property is validated
- **THEN** validation reports that an FN property is required
