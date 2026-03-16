# Java Refined

[![CI](https://github.com/JunggiKim/java-refined/actions/workflows/ci.yml/badge.svg)](https://github.com/JunggiKim/java-refined/actions/workflows/ci.yml)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.junggikim/java-refined)](https://central.sonatype.com/artifact/io.github.junggikim/java-refined)
[![Coverage](https://img.shields.io/badge/coverage-100%25-brightgreen.svg)](https://github.com/JunggiKim/java-refined/actions)
[![Mutation](https://img.shields.io/badge/mutation-95%25+-blue.svg)](https://github.com/JunggiKim/java-refined/actions)

Move validation into the type system. Zero runtime dependencies. Java 8+.

## Before

```java
public void createUser(String name, int age, List<String> roles) {
    if (name == null || name.trim().isEmpty()) {
        throw new IllegalArgumentException("name must not be blank");
    }
    if (age <= 0) {
        throw new IllegalArgumentException("age must be positive");
    }
    if (roles == null || roles.isEmpty()) {
        throw new IllegalArgumentException("roles must not be empty");
    }
    // business logic ...
}
```

## After

```java
public void createUser(NonBlankString name, PositiveInt age, NonEmptyList<String> roles) {
    // no validation needed — the types guarantee it
}
```

Types replace scattered `if`-checks. Invalid data cannot even reach your method.

## Installation

Published to [Maven Central](https://central.sonatype.com/artifact/io.github.junggikim/java-refined). Kotlin/JVM users can optionally add `java-refined-kotlin` for extension functions (adds `kotlin-stdlib` dependency).

### Gradle Kotlin DSL

```kotlin
dependencies {
    implementation("io.github.junggikim:java-refined:1.1.0")

    // optional: Kotlin extensions
    // implementation("io.github.junggikim:java-refined-kotlin:1.1.0")
}
```

### Maven

```xml
<dependency>
  <groupId>io.github.junggikim</groupId>
  <artifactId>java-refined</artifactId>
  <version>1.1.0</version>
</dependency>
```

## Quick Start

```java
import io.github.junggikim.refined.refined.numeric.PositiveInt;
import io.github.junggikim.refined.refined.string.NonBlankString;
import io.github.junggikim.refined.refined.collection.NonEmptyList;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Arrays;

// of() returns Validation — never throws
Validation<Violation, PositiveInt> age = PositiveInt.of(18);
Validation<Violation, NonBlankString> name = NonBlankString.of("Ada");
Validation<Violation, NonEmptyList<String>> tags =
    NonEmptyList.of(Arrays.asList("java", "fp"));  // or List.of() on Java 9+

// combine results
Validation<Violation, String> summary =
    name.zip(age, (n, a) -> n.value() + " (" + a.value() + ")");

// unsafeOf() throws on invalid input — use at trusted boundaries
PositiveInt confirmedAge = PositiveInt.unsafeOf(18);
```

## Kotlin Support

Optional module with Kotlin-idiomatic extensions:

```kotlin
import io.github.junggikim.refined.kotlin.*

val name = "Ada".toNonBlankStringOrThrow()
val tags = listOf("java", "fp").toNonEmptyListOrThrow()
```

## Error Handling

`Validation<E, A>` is fail-fast — stops at the first error. Use it for single-field validation.
`Validated<E, A>` accumulates all errors into a list. Use it when you need every failure at once.

```java
// fail-fast
Validation<Violation, NonBlankString> bad = NonBlankString.of("   ");
String message = bad.fold(
    v -> "invalid: " + v.code() + " - " + v.message(),
    ok -> "ok: " + ok.value()
);

// error-accumulating
Validated<String, Integer> left = Validated.invalid(Arrays.asList("age"));
Validated<String, Integer> right = Validated.invalid(Arrays.asList("name"));
List<String> errors = left.zip(right, Integer::sum).getErrors();
// errors = ["age", "name"]
```

## API

All refined wrappers follow the same pattern:

- `of(value)` — returns `Validation<Violation, T>`, never throws
- `unsafeOf(value)` — throws `RefinementException` on invalid input
- `ofStream(stream)` — collection wrappers only

Collection refined types implement JDK interfaces directly — `NonEmptyList<T>` is a `List<T>`, `NonEmptyMap<K,V>` is a `Map<K,V>`. No unwrapping needed.

`Violation` carries a stable `code`, human-readable `message`, and immutable `metadata` map.

## Supported Types

| Category | Examples | Count |
| --- | --- | --- |
| Numeric | `PositiveInt`, `NegativeLong`, `NonZeroDouble`, `ZeroToOneFloat` | 46 |
| Character | `DigitChar`, `LetterChar`, `UpperCaseChar` | 7 |
| String | `NonBlankString`, `EmailString`, `UuidString`, `Ipv4String`, `JwtString` | 48 |
| Collection | `NonEmptyList`, `NonEmptySet`, `NonEmptyMap`, `NonEmptyDeque`, `NonEmptyNavigableMap`, ... | 10 |
| Control | `Option`, `Either`, `Try`, `Ior`, `Validated` | 5 |
| Predicates | `GreaterThan`, `LengthBetween`, `MatchesRegex`, `AllOf`, `ForAllElements` | 55+ |

Full list: [docs/type-matrix.md](docs/type-matrix.md)

## Contributing

[CONTRIBUTING.md](CONTRIBUTING.md) · [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md) · [SECURITY.md](SECURITY.md) · [docs/compatibility.md](docs/compatibility.md) · [docs/kotlin-support-policy.md](docs/kotlin-support-policy.md)

## License

MIT. See [LICENSE](LICENSE).
