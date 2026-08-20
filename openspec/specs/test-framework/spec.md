# Test Framework

This capability defines the test framework, engines on the JUnit Platform classpath, and the conventions that test code in this repository must follow.

## Requirements

### Requirement: JUnit Platform discovers all Java tests
The build SHALL ensure every Java test class under `src/test/java/` is discovered and executed by the configured JUnit Platform engines. No Java test class shall be silently skipped due to a missing engine on the test runtime classpath.

#### Scenario: Every Java test class appears in build reports
- **WHEN** the Gradle `test` task runs to completion
- **THEN** `build/test-results/test/` contains a `TEST-*.xml` report for every non-abstract Java test class under `src/test/java/`
- **AND** no test class is present in source but absent from the report directory

#### Scenario: A new test framework annotation requires a registered engine
- **WHEN** a test class is added that uses a framework whose engine is not on the test runtime classpath
- **THEN** the build either registers the appropriate engine or rejects the test class
- **AND** the situation where the class compiles but is never executed must not occur

### Requirement: Java tests use JUnit 5 Jupiter APIs
All Java test classes under `src/test/java/` SHALL use only `org.junit.jupiter.api.*` and `org.junit.jupiter.params.*` for test annotations, lifecycle hooks, assertions, and parameterized data. Imports from `org.junit.Test`, `org.junit.Assert`, `org.junit.Before`, `org.junit.After`, `org.junit.runner.*`, or `org.junit.runners.*` shall not appear in any test source file.

#### Scenario: No JUnit 4 imports remain
- **WHEN** a grep is run for `org.junit.Test`, `org.junit.Assert`, `org.junit.Before`, `org.junit.After`, `org.junit.runner`, or `org.junit.runners` under `src/test/java/`
- **THEN** zero matches are returned

#### Scenario: Single test method
- **WHEN** a test class has a single test method
- **THEN** the method is annotated with `org.junit.jupiter.api.Test`
- **AND** assertions use `org.junit.jupiter.api.Assertions.*`

#### Scenario: Setup and teardown
- **WHEN** a test class needs per-test setup or teardown
- **THEN** lifecycle methods are annotated with `@BeforeEach` / `@AfterEach`
- **AND** per-class lifecycle uses `@BeforeAll` / `@AfterAll` on `static` methods

### Requirement: Parameterized tests use Jupiter `@ParameterizedTest` + `@MethodSource`
Parameterized test classes SHALL use `@ParameterizedTest` together with `@MethodSource` referencing a `static` method that returns `Stream<Arguments>` (or equivalent). Subclasses that contribute only data SHALL override the source method; the inherited test method definitions live in the abstract base class.

#### Scenario: Base class defines parameterized test methods
- **WHEN** a parameterized base class such as `PropertyTest` or `ParameterTest` defines a test method
- **THEN** the method is annotated with `@ParameterizedTest` and `@MethodSource("parameters")`
- **AND** test data flows in as method arguments
- **AND** the class is `abstract` and holds no instance fields populated from constructor parameters

#### Scenario: Subclass supplies parameter data
- **WHEN** a subclass extends a parameterized base class to test a specific property or parameter
- **THEN** the subclass defines `static Stream<Arguments> parameters()` returning the test rows
- **AND** the subclass does not declare a constructor that accepts test parameters
- **AND** the subclass inherits all `@ParameterizedTest` methods from the base without redefinition

### Requirement: Test framework dependencies are declared explicitly
The `gradle/libs.versions.toml` catalog SHALL declare JUnit dependencies with accurate, non-misleading names. Version reference keys SHALL match the artifact they resolve.

#### Scenario: Version reference key matches the artifact it resolves
- **WHEN** a version reference is declared for a JUnit dependency
- **THEN** the key name reflects the artifact it points at (e.g., a key named `junit` resolves a JUnit artifact; a key named `junit-vintage` resolves the vintage engine)
- **AND** no key resolves an artifact whose name contradicts the key

#### Scenario: Vintage engine is only present while needed
- **WHEN** any Java test class still uses JUnit 4 APIs
- **THEN** `org.junit.vintage:junit-vintage-engine` is declared as `testRuntimeOnly`
- **WHEN** zero Java test classes use JUnit 4 APIs
- **THEN** the vintage engine declaration is removed from the build
