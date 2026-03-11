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

- [Why](#why)
- [Installation](#installation)
- [Coordinates](#coordinates)
- [Basic Usage](#basic-usage)
- [Error Handling](#error-handling)
- [Core Concepts](#core-concepts)
- [Core API](#core-api)
- [Supported Types](#supported-types)
- [Compatibility](#compatibility)
- [Project Status](#project-status)
- [Contributing and Security](#contributing-and-security)
- [License](#license)

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
    implementation("io.github.junggikim:java-refined:1.0.0")
}
```

### Gradle Groovy DSL

```groovy
repositories {
    mavenLocal()
}

dependencies {
    implementation 'io.github.junggikim:java-refined:1.0.0'
}
```

### Maven

```xml
<dependency>
  <groupId>io.github.junggikim</groupId>
  <artifactId>java-refined</artifactId>
  <version>1.0.0</version>
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

## Supported Types

The library ships with refined wrappers, control types, and predicate catalogs.
Each table lists a type and the invariant it enforces.

### Refined Wrappers — Numeric

#### Int

| Type | Description |
| --- | --- |
| `PositiveInt` | `int > 0` |
| `NegativeInt` | `int < 0` |
| `NonNegativeInt` | `int >= 0` |
| `NonPositiveInt` | `int <= 0` |
| `NonZeroInt` | `int != 0` |
| `NaturalInt` | `int >= 0` |

#### Long

| Type | Description |
| --- | --- |
| `PositiveLong` | `long > 0` |
| `NegativeLong` | `long < 0` |
| `NonNegativeLong` | `long >= 0` |
| `NonPositiveLong` | `long <= 0` |
| `NonZeroLong` | `long != 0` |
| `NaturalLong` | `long >= 0` |

#### Byte

| Type | Description |
| --- | --- |
| `PositiveByte` | `byte > 0` |
| `NegativeByte` | `byte < 0` |
| `NonNegativeByte` | `byte >= 0` |
| `NonPositiveByte` | `byte <= 0` |
| `NonZeroByte` | `byte != 0` |
| `NaturalByte` | `byte >= 0` |

#### Short

| Type | Description |
| --- | --- |
| `PositiveShort` | `short > 0` |
| `NegativeShort` | `short < 0` |
| `NonNegativeShort` | `short >= 0` |
| `NonPositiveShort` | `short <= 0` |
| `NonZeroShort` | `short != 0` |
| `NaturalShort` | `short >= 0` |

#### Float

| Type | Description |
| --- | --- |
| `PositiveFloat` | finite float > 0 |
| `NegativeFloat` | finite float < 0 |
| `NonNegativeFloat` | finite float >= 0 |
| `NonPositiveFloat` | finite float <= 0 |
| `NonZeroFloat` | finite float != 0 |
| `FiniteFloat` | finite float (not NaN/Infinity) |
| `NonNaNFloat` | float is not NaN (Infinity allowed) |
| `ZeroToOneFloat` | finite float in [0, 1] |

#### Double

| Type | Description |
| --- | --- |
| `PositiveDouble` | finite double > 0 |
| `NegativeDouble` | finite double < 0 |
| `NonNegativeDouble` | finite double >= 0 |
| `NonPositiveDouble` | finite double <= 0 |
| `NonZeroDouble` | finite double != 0 |
| `FiniteDouble` | finite double (not NaN/Infinity) |
| `NonNaNDouble` | double is not NaN (Infinity allowed) |
| `ZeroToOneDouble` | finite double in [0, 1] |

#### BigInteger

| Type | Description |
| --- | --- |
| `PositiveBigInteger` | `BigInteger > 0` |
| `NegativeBigInteger` | `BigInteger < 0` |
| `NonNegativeBigInteger` | `BigInteger >= 0` |
| `NonPositiveBigInteger` | `BigInteger <= 0` |
| `NonZeroBigInteger` | `BigInteger != 0` |
| `NaturalBigInteger` | `BigInteger >= 0` |

#### BigDecimal

| Type | Description |
| --- | --- |
| `PositiveBigDecimal` | `BigDecimal > 0` |
| `NegativeBigDecimal` | `BigDecimal < 0` |
| `NonNegativeBigDecimal` | `BigDecimal >= 0` |
| `NonPositiveBigDecimal` | `BigDecimal <= 0` |
| `NonZeroBigDecimal` | `BigDecimal != 0` |

### Refined Wrappers — Character

| Type | Description |
| --- | --- |
| `DigitChar` | Unicode digit (`Character.isDigit`) |
| `LetterChar` | Unicode letter (`Character.isLetter`) |
| `LetterOrDigitChar` | Unicode letter or digit (`Character.isLetterOrDigit`) |
| `LowerCaseChar` | Unicode lower-case (`Character.isLowerCase`) |
| `UpperCaseChar` | Unicode upper-case (`Character.isUpperCase`) |
| `WhitespaceChar` | Unicode whitespace (`Character.isWhitespace`) |
| `SpecialChar` | not letter, digit, whitespace, or space |

### Refined Wrappers — String

#### Whitespace and Case

| Type | Description |
| --- | --- |
| `NonEmptyString` | not empty |
| `NonBlankString` | not blank (Unicode whitespace) |
| `TrimmedString` | no leading or trailing Unicode whitespace |
| `LowerCaseString` | equals `toLowerCase(Locale.ROOT)` |
| `UpperCaseString` | equals `toUpperCase(Locale.ROOT)` |

#### Character Sets and Slugs

| Type | Description |
| --- | --- |
| `AsciiString` | ASCII only (U+0000..U+007F) |
| `AlphabeticString` | non-empty; letters only |
| `NumericString` | non-empty; digits only |
| `AlphanumericString` | non-empty; letters or digits only |
| `SlugString` | lower-case slug `a-z0-9` with hyphens |

#### Identifiers and Tokens

| Type | Description |
| --- | --- |
| `UuidString` | valid UUID |
| `UlidString` | valid ULID (Crockford Base32, 26 chars) |
| `JwtString` | three Base64URL parts separated by dots |
| `SemVerString` | Semantic Versioning 2.0.0 pattern |
| `CreditCardString` | Luhn-valid 13-19 digits (spaces/hyphens allowed) |
| `IsbnString` | valid ISBN-10 or ISBN-13 (spaces/hyphens allowed) |

#### URIs and URLs

| Type | Description |
| --- | --- |
| `UriString` | parseable by `java.net.URI` |
| `UrlString` | parseable by `java.net.URL` |

#### Network

| Type | Description |
| --- | --- |
| `Ipv4String` | IPv4 dotted-quad |
| `Ipv6String` | IPv6 address |
| `CidrV4String` | IPv4 CIDR with prefix `/0-32` |
| `CidrV6String` | IPv6 CIDR with prefix `/0-128` |
| `MacAddressString` | MAC address with `:`/`-`/`.` separators |
| `HostnameString` | hostname labels (1-63 chars, letters/digits/hyphen), total length <= 253 |

#### Encodings and Structured Formats

| Type | Description |
| --- | --- |
| `EmailString` | well-formed email shape (basic local@domain rules) |
| `RegexString` | valid regular expression |
| `HexString` | non-empty hex string |
| `HexColorString` | `#RGB`, `#RRGGBB`, or `#RRGGBBAA` |
| `Base64String` | valid Base64 (JDK decoder) |
| `Base64UrlString` | valid Base64 URL-safe (JDK decoder) |
| `JsonString` | valid JSON |
| `XmlString` | well-formed XML |
| `XPathString` | valid XPath expression |

#### Date and Time

| Type | Description |
| --- | --- |
| `Iso8601DateString` | ISO-8601 date (`LocalDate.parse`) |
| `Iso8601TimeString` | ISO-8601 time (`LocalTime.parse`) |
| `Iso8601DateTimeString` | ISO-8601 date-time (`ISO_DATE_TIME`) |
| `Iso8601DurationString` | ISO-8601 duration (`Duration.parse`) |
| `Iso8601PeriodString` | ISO-8601 period (`Period.parse`) |
| `TimeZoneIdString` | valid `ZoneId` |

#### Numeric Parsing

| Type | Description |
| --- | --- |
| `ValidByteString` | parseable by `Byte.parseByte` |
| `ValidShortString` | parseable by `Short.parseShort` |
| `ValidIntString` | parseable by `Integer.parseInt` |
| `ValidLongString` | parseable by `Long.parseLong` |
| `ValidFloatString` | parseable by `Float.parseFloat` (NaN/Infinity allowed) |
| `ValidDoubleString` | parseable by `Double.parseDouble` (NaN/Infinity allowed) |
| `ValidBigIntegerString` | parseable by `new BigInteger(...)` |
| `ValidBigDecimalString` | parseable by `new BigDecimal(...)` |

### Refined Wrappers — Collection

| Type | Description |
| --- | --- |
| `NonEmptyList` | non-empty list with no null elements; immutable snapshot |
| `NonEmptySet` | non-empty set with no null elements; immutable snapshot |
| `NonEmptyMap` | non-empty map with no null keys/values; immutable snapshot |
| `NonEmptyDeque` | non-empty deque snapshot as immutable list; no null elements |
| `NonEmptyQueue` | non-empty queue snapshot as immutable list; no null elements |
| `NonEmptyIterable` | non-empty iterable snapshot as immutable list; no null elements |
| `NonEmptySortedSet` | non-empty sorted set with no null elements; immutable snapshot |
| `NonEmptySortedMap` | non-empty sorted map with no null keys/values; immutable snapshot |
| `NonEmptyNavigableSet` | non-empty navigable set with no null elements; immutable snapshot |
| `NonEmptyNavigableMap` | non-empty navigable map with no null keys/values; immutable snapshot |

### Control Types

| Type | Description |
| --- | --- |
| `Option` | optional value (`Some` or `None`) |
| `Either` | left or right branch |
| `Try` | success or captured exception |
| `Ior` | inclusive-or: left, right, or both |
| `Validated` | error-accumulating validation result |

### Predicates — Numeric (Comparison)

| Predicate | Description |
| --- | --- |
| `GreaterThan` | value > bound |
| `GreaterOrEqual` | value >= bound |
| `LessThan` | value < bound |
| `LessOrEqual` | value <= bound |
| `EqualTo` | value == bound |
| `NotEqualTo` | value != bound |

### Predicates — Numeric (Intervals)

| Predicate | Description |
| --- | --- |
| `OpenInterval` | min < value < max |
| `ClosedInterval` | min <= value <= max |
| `OpenClosedInterval` | min < value <= max |
| `ClosedOpenInterval` | min <= value < max |

### Predicates — Numeric (Parity)

| Predicate | Description |
| --- | --- |
| `EvenInt` | int is even |
| `OddInt` | int is odd |
| `EvenLong` | long is even |
| `OddLong` | long is odd |
| `EvenBigInteger` | BigInteger is even |
| `OddBigInteger` | BigInteger is odd |

### Predicates — Numeric (Divisibility)

| Predicate | Description |
| --- | --- |
| `DivisibleByInt` | value % divisor == 0 |
| `DivisibleByLong` | value % divisor == 0 |
| `DivisibleByBigInteger` | value mod divisor == 0 |
| `NonDivisibleByInt` | value % divisor != 0 |
| `NonDivisibleByLong` | value % divisor != 0 |
| `NonDivisibleByBigInteger` | value mod divisor != 0 |
| `ModuloInt` | value % divisor == remainder |
| `ModuloLong` | value % divisor == remainder |

### Predicates — Numeric (Float/Double)

| Predicate | Description |
| --- | --- |
| `FiniteFloatPredicate` | float is finite |
| `FiniteDoublePredicate` | double is finite |
| `NonNaNFloatPredicate` | float is not NaN (Infinity allowed) |
| `NonNaNDoublePredicate` | double is not NaN (Infinity allowed) |

### Predicates — String

| Predicate | Description |
| --- | --- |
| `NotEmpty` | string not empty |
| `NotBlank` | string not blank (Unicode whitespace) |
| `LengthAtLeast` | length >= minimum |
| `LengthAtMost` | length <= maximum |
| `LengthBetween` | minimum <= length <= maximum |
| `MatchesRegex` | matches the regex pattern |
| `StartsWith` | starts with prefix |
| `EndsWith` | ends with suffix |
| `Contains` | contains infix |

### Predicates — Boolean

| Predicate | Description |
| --- | --- |
| `TrueValue` | value is true |
| `FalseValue` | value is false |
| `And` | all values in a list are true |
| `Or` | at least one value in a list is true |
| `Xor` | odd number of values in a list are true |
| `Nand` | not all values in a list are true |
| `Nor` | no values in a list are true |
| `OneOf` | exactly one value in a list is true |

### Predicates — Character

| Predicate | Description |
| --- | --- |
| `IsDigitChar` | Unicode digit (`Character.isDigit`) |
| `IsLetterChar` | Unicode letter (`Character.isLetter`) |
| `IsLetterOrDigitChar` | Unicode letter or digit (`Character.isLetterOrDigit`) |
| `IsLowerCaseChar` | Unicode lower-case (`Character.isLowerCase`) |
| `IsUpperCaseChar` | Unicode upper-case (`Character.isUpperCase`) |
| `IsWhitespaceChar` | Unicode whitespace (`Character.isWhitespace`) |
| `IsSpecialChar` | not letter, digit, whitespace, or space |

### Predicates — Collection

| Predicate | Description |
| --- | --- |
| `MinSize` | size >= minimum |
| `MaxSize` | size <= maximum |
| `SizeBetween` | minimum <= size <= maximum |
| `SizeEqual` | size == expected |
| `ContainsElement` | collection contains expected element |
| `EmptyCollection` | collection is empty |
| `ForAllElements` | all elements satisfy predicate |
| `ExistsElement` | at least one element satisfies predicate |
| `HeadSatisfies` | first element satisfies predicate (non-empty list) |
| `LastSatisfies` | last element satisfies predicate (non-empty list) |
| `IndexSatisfies` | element at index satisfies predicate |
| `InitSatisfies` | init slice (all but last) satisfies predicate |
| `TailSatisfies` | tail slice (all but first) satisfies predicate |
| `CountMatches` | matching element count equals expected count |
| `AscendingList` | list is non-decreasing |
| `DescendingList` | list is non-increasing |

### Predicates — Logical

| Predicate | Description |
| --- | --- |
| `AllOf` | all delegated constraints must pass |
| `AnyOf` | at least one delegated constraint must pass |
| `Not` | delegated constraint must fail |

The full matrix is kept in [docs/type-matrix.md](docs/type-matrix.md).

## Compatibility

- production baseline: Java 8
- Java 8+ supported
- verification:
  - `./gradlew clean check`
  - `./gradlew testJava8`
  - `./gradlew javadoc generatePomFileForMavenJavaPublication publishToMavenLocal`
- Java 8 runtime compatibility is verified by the `testJava8` toolchain task
- current build uses Java 8 source/target compatibility and Gradle `--release 8` when running on JDK 9+

See [docs/compatibility.md](docs/compatibility.md) for Java version caveats.

## Project Status

- current local version line: `1.0.0`
- distribution: source checkout and local Maven only
- release notes live in [CHANGELOG.md](CHANGELOG.md)

## Contributing and Security

- contribution guide: [CONTRIBUTING.md](CONTRIBUTING.md)
- code of conduct: [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md)
- security policy: [SECURITY.md](SECURITY.md)

## License

MIT. See [LICENSE](LICENSE).
