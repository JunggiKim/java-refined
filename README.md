# Java Refined

[![CI](https://github.com/JunggiKim/java-refined/actions/workflows/ci.yml/badge.svg)](https://github.com/JunggiKim/java-refined/actions/workflows/ci.yml)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

Java Refined is a Java 8+ library for refinement types, non-empty collections,
and small functional control types without runtime dependencies.

It lets you move validation out of scattered `if` checks and into explicit
types such as `PositiveInt`, `NonBlankString`, and `NonEmptyList`.

## Why

Java applications often validate the same invariants repeatedly:

- positive numeric identifiers
- non-blank names and codes
- non-empty collections in domain logic
- fail-fast versus error-accumulating validation

Java Refined packages those invariants into reusable types and predicates so
that "already validated" becomes part of the type system at the library level.

## Installation

`0.1.0` has not been published to Maven Central yet.
Until the first public release lands, use a local snapshot build or source checkout.
From a source checkout, run `./gradlew publishToMavenLocal` first and resolve the dependency through the local Maven repository.

## Coordinates

```text
io.github.junggikim:java-refined
```

### Gradle Kotlin DSL

```kotlin
repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("io.github.junggikim:java-refined:0.1.0-SNAPSHOT")
}
```

### Gradle Groovy DSL

```groovy
repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation 'io.github.junggikim:java-refined:0.1.0-SNAPSHOT'
}
```

### Maven

```xml
<dependency>
  <groupId>io.github.junggikim</groupId>
  <artifactId>java-refined</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>
```

Ensure the artifact has been published to the local Maven repository before resolving it from Maven.

## Quick Start

```java
import io.github.junggikim.refined.refined.collection.NonEmptyList;
import io.github.junggikim.refined.refined.numeric.PositiveInt;
import io.github.junggikim.refined.refined.string.NonBlankString;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Arrays;

Validation<Violation, PositiveInt> age = PositiveInt.of(18);
Validation<Violation, NonBlankString> name = NonBlankString.of("Ada");
Validation<Violation, NonEmptyList<String>> tags = NonEmptyList.of(Arrays.asList("java", "fp"));
```

## Core API

- `of(value)`
  - safe constructor
  - returns `Validation<Violation, T>`
- `unsafeOf(value)`
  - validates first
  - throws `RefinementException` on failure
  - use only at trusted boundaries after validation has already happened
- `Violation`
  - stable `code`, human-readable `message`, immutable `metadata`
  - collection constructors distinguish `*-empty`, `*-null-element`, `*-null-key`, `*-null-value`, and sorted/navigable `*-invalid-*` failures

## Design Rules

- Java-only production code
- Java 8 baseline bytecode and API compatibility
- runtime dependency `0`
- JUnit 5-based TDD
- `checkstyle`, `spotbugs`, and `pmd` quality gates in `check`
- JaCoCo `100%` coverage across instruction, branch, line, complexity, method, and class
- immutable value types and defensive collection snapshots

## Supported Surface

- refined numeric wrappers
- refined character wrappers
- refined string wrappers, including parser-backed string types
- non-empty collection wrappers
- functional control types: `Option`, `Either`, `Try`, `Ior`, `Validated`
- composable predicates for numeric, string, character, boolean, collection, and logical composition

The full type matrix is documented in [docs/type-matrix.md](docs/type-matrix.md).

## Compatibility

- production baseline: Java 8
- run Gradle on JDK 17+ (JDK 21 verified locally)
- verification:
  - `./gradlew clean check`
  - `./gradlew testJava8`
  - `./gradlew javadoc generatePomFileForMavenJavaPublication publishToMavenLocal`
  - `./gradlew prepareRelease`
- Java 8 runtime compatibility is verified by the `testJava8` toolchain task
- current build uses Java 8 source/target compatibility and Gradle `--release 8` when running on JDK 9+

See [docs/compatibility.md](docs/compatibility.md) for Java version caveats.

## Project Status

- current local version line: `0.1.0-SNAPSHOT`
- Maven Central publication is not live yet
- API may still evolve before `1.0.0`
- release notes live in [CHANGELOG.md](CHANGELOG.md)

## Contributing and Security

- contribution guide: [CONTRIBUTING.md](CONTRIBUTING.md)
- code of conduct: [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md)
- security policy: [SECURITY.md](SECURITY.md)
- release workflow: [docs/release-process.md](docs/release-process.md)
- repository checklist: [docs/repository-checklist.md](docs/repository-checklist.md)

## License

Apache-2.0. See [LICENSE](LICENSE).
