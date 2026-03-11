# Java Refined

[![CI](https://github.com/JunggiKim/java-refined/actions/workflows/ci.yml/badge.svg)](https://github.com/JunggiKim/java-refined/actions/workflows/ci.yml)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Java Refined is a Java 8+ library for refinement types, non-empty collections,
and small functional control types without runtime dependencies.

It lets you move validation out of scattered `if` checks and into explicit
types such as `PositiveInt`, `NonBlankString`, and `NonEmptyList`.

This project is open source under the MIT license, so you can use, modify, and
distribute it in personal or commercial codebases.

## Features

- zero runtime dependencies
- Java 8 bytecode baseline with Java 8+ APIs
- refined wrappers with safe `of` and throwing `unsafeOf` constructors
- fail-fast `Validation` and error-accumulating `Validated`
- non-empty collection wrappers with defensive snapshots
- structured `Violation` errors with code/message/metadata
- rich predicate catalog across numeric/string/collection/boolean/logical domains

## Contents

- Why
- Installation
- Basic Usage
- Error Handling
- Core Concepts
- Core API
- Supported Types
- Design Rules
- Compatibility
- Project Status
- Contributing and Security
- License

## Why

Java applications often validate the same invariants repeatedly:

- positive numeric identifiers
- non-blank names and codes
- non-empty collections in domain logic
- fail-fast versus error-accumulating validation

Java Refined packages those invariants into reusable types and predicates so
that "already validated" becomes part of the type system at the library level.

## Installation

This library is currently distributed via source checkout and local Maven builds only.
From a source checkout, run `./gradlew publishToMavenLocal` and resolve the dependency through the local Maven repository.

Local install checklist:

1. Run `./gradlew publishToMavenLocal`.
2. Add `mavenLocal()` to your repositories.
3. Add the dependency coordinates shown below.

## Coordinates

```text
io.github.junggikim:java-refined
```

### Gradle Kotlin DSL

```kotlin
repositories {
    mavenLocal()
}

dependencies {
    implementation("io.github.junggikim:java-refined:0.1.0-SNAPSHOT")
}
```

### Gradle Groovy DSL

```groovy
repositories {
    mavenLocal()
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

## Basic Usage

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

Validation<Violation, String> summary =
    name.zip(age, (n, a) -> n.value() + " (" + a.value() + ")");
```

## Error Handling

```java
import io.github.junggikim.refined.refined.string.NonBlankString;
import io.github.junggikim.refined.validation.Validated;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Arrays;
import java.util.List;

Validation<Violation, NonBlankString> bad = NonBlankString.of("   ");
String message = bad.fold(
    v -> "invalid: " + v.code() + " - " + v.message(),
    ok -> "ok: " + ok.value()
);

Validated<String, Integer> left = Validated.invalid(Arrays.asList("age"));
Validated<String, Integer> right = Validated.invalid(Arrays.asList("name"));
List<String> errors = left.zip(right, Integer::sum).getErrors();
```

## Core Concepts

Refined wrappers expose two constructors:

- `of(value)` returns `Validation<Violation, T>` and never throws.
- `unsafeOf(value)` throws `RefinementException` on invalid input.

`Validation` is fail-fast and stops at the first error. `Validated` accumulates multiple errors into a non-empty list.

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

## Supported Types

### Refined Wrappers — Numeric

- `PositiveInt`, `NegativeInt`, `NonNegativeInt`, `NonPositiveInt`, `NonZeroInt`, `NaturalInt`
- `PositiveLong`, `NegativeLong`, `NonNegativeLong`, `NonPositiveLong`, `NonZeroLong`, `NaturalLong`
- `PositiveByte`, `NegativeByte`, `NonNegativeByte`, `NonPositiveByte`, `NonZeroByte`, `NaturalByte`
- `PositiveShort`, `NegativeShort`, `NonNegativeShort`, `NonPositiveShort`, `NonZeroShort`, `NaturalShort`
- `PositiveFloat`, `NegativeFloat`, `NonNegativeFloat`, `NonPositiveFloat`, `NonZeroFloat`, `FiniteFloat`, `NonNaNFloat`, `ZeroToOneFloat`
- `PositiveDouble`, `NegativeDouble`, `NonNegativeDouble`, `NonPositiveDouble`, `NonZeroDouble`, `FiniteDouble`, `NonNaNDouble`, `ZeroToOneDouble`
- `PositiveBigInteger`, `NegativeBigInteger`, `NonNegativeBigInteger`, `NonPositiveBigInteger`, `NonZeroBigInteger`, `NaturalBigInteger`
- `PositiveBigDecimal`, `NegativeBigDecimal`, `NonNegativeBigDecimal`, `NonPositiveBigDecimal`, `NonZeroBigDecimal`

### Refined Wrappers — Character

- `DigitChar`, `LetterChar`, `LetterOrDigitChar`, `LowerCaseChar`, `UpperCaseChar`, `WhitespaceChar`, `SpecialChar`

### Refined Wrappers — String

- `NonEmptyString`, `NonBlankString`, `TrimmedString`, `UuidString`, `UriString`
- `EmailString`, `AsciiString`, `AlphabeticString`, `NumericString`, `AlphanumericString`
- `SlugString`, `LowerCaseString`, `UpperCaseString`
- `RegexString`, `UrlString`, `Ipv4String`, `Ipv6String`, `HexString`, `HexColorString`, `XmlString`, `XPathString`
- `Base64String`, `Base64UrlString`, `UlidString`, `JsonString`, `CidrV4String`, `CidrV6String`, `MacAddressString`, `SemVerString`
- `CreditCardString`, `IsbnString`, `HostnameString`, `JwtString`
- `Iso8601DateString`, `Iso8601TimeString`, `Iso8601DateTimeString`, `Iso8601DurationString`, `Iso8601PeriodString`, `TimeZoneIdString`
- `ValidByteString`, `ValidShortString`, `ValidIntString`, `ValidLongString`
- `ValidFloatString`, `ValidDoubleString`, `ValidBigIntegerString`, `ValidBigDecimalString`

### Refined Wrappers — Collection

- `NonEmptyList`, `NonEmptySet`, `NonEmptyMap`, `NonEmptyDeque`, `NonEmptyIterable`
- `NonEmptyQueue`, `NonEmptySortedSet`, `NonEmptySortedMap`, `NonEmptyNavigableSet`, `NonEmptyNavigableMap`

### Control Types

- `Option`
- `Either`
- `Try`
- `Ior`
- `Validated`

### Predicates — Numeric

- `GreaterThan`, `GreaterOrEqual`, `LessThan`, `LessOrEqual`, `EqualTo`, `NotEqualTo`
- `OpenInterval`, `ClosedInterval`, `OpenClosedInterval`, `ClosedOpenInterval`
- `EvenInt`, `OddInt`, `EvenLong`, `OddLong`, `EvenBigInteger`, `OddBigInteger`
- `DivisibleByInt`, `DivisibleByLong`, `DivisibleByBigInteger`
- `ModuloInt`, `ModuloLong`, `NonDivisibleByInt`, `NonDivisibleByLong`, `NonDivisibleByBigInteger`
- `FiniteFloatPredicate`, `FiniteDoublePredicate`, `NonNaNFloatPredicate`, `NonNaNDoublePredicate`

### Predicates — String

- `NotEmpty`, `NotBlank`, `LengthAtLeast`, `LengthAtMost`, `LengthBetween`
- `MatchesRegex`, `StartsWith`, `EndsWith`, `Contains`

### Predicates — Boolean

- `TrueValue`, `FalseValue`, `And`, `Or`, `Xor`, `Nand`, `Nor`, `OneOf`

### Predicates — Character

- `IsDigitChar`, `IsLetterChar`, `IsLetterOrDigitChar`, `IsLowerCaseChar`, `IsUpperCaseChar`, `IsWhitespaceChar`, `IsSpecialChar`

### Predicates — Collection

- `MinSize`, `MaxSize`, `SizeBetween`, `SizeEqual`
- `ContainsElement`, `EmptyCollection`, `ForAllElements`, `ExistsElement`
- `HeadSatisfies`, `LastSatisfies`, `IndexSatisfies`, `InitSatisfies`, `TailSatisfies`, `CountMatches`
- `AscendingList`, `DescendingList`

### Predicates — Logical

- `AllOf`, `AnyOf`, `Not`

The full matrix is kept in [docs/type-matrix.md](docs/type-matrix.md).

## Compatibility

- production baseline: Java 8
- run Gradle on JDK 17+ (JDK 21 verified locally)
- verification:
  - `./gradlew clean check`
  - `./gradlew testJava8`
  - `./gradlew javadoc generatePomFileForMavenJavaPublication publishToMavenLocal`
- Java 8 runtime compatibility is verified by the `testJava8` toolchain task
- current build uses Java 8 source/target compatibility and Gradle `--release 8` when running on JDK 9+

See [docs/compatibility.md](docs/compatibility.md) for Java version caveats.

## Project Status

- current local version line: `0.1.0-SNAPSHOT`
- distribution: source checkout and local Maven only
- API may still evolve before `1.0.0`
- release notes live in [CHANGELOG.md](CHANGELOG.md)

## Contributing and Security

- contribution guide: [CONTRIBUTING.md](CONTRIBUTING.md)
- code of conduct: [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md)
- security policy: [SECURITY.md](SECURITY.md)

## License

MIT. See [LICENSE](LICENSE).
