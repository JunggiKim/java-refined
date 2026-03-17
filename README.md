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

## Quick Start

```java
// Trusted data — just unwrap
PositiveInt confirmedAge = PositiveInt.unsafeOf(18);

// Untrusted input — safe fallback
PositiveInt safeAge = PositiveInt.ofOrElse(userInput, 1);
```

```java
// Branch on success/failure
EmailString.of(email).fold(
    error -> badRequest(error.message()),
    valid -> ok(register(valid))
);
```

## Kotlin Support

Optional module with Kotlin-idiomatic extensions:

```kotlin
import io.github.junggikim.refined.kotlin.*

val name = "Ada".toNonBlankStringOrThrow()
val tags = listOf("java", "fp").toNonEmptyListOrThrow()
```

```kotlin
// Safe fallback
val safeAge = PositiveInt.ofOrElse(input, 1)
```

```kotlin
// Kotlin-idiomatic nullable
val age = PositiveInt.of(input).getOrNull() ?: PositiveInt.unsafeOf(1)
```

```kotlin
// Convert to kotlin.Result
val result: Result<PositiveInt> = PositiveInt.of(input).toResult()
```

## API

All refined wrappers follow the same pattern:

- `of(value)` — returns `Validation<Violation, T>`, never throws
- `unsafeOf(value)` — throws `RefinementException` on invalid input
- `ofOrElse(value, default)` — returns validated instance or falls back to `default`
- `ofStream(stream)` — collection wrappers only

Collection refined types implement JDK interfaces directly — `NonEmptyList<T>` is a `List<T>`, `NonEmptyMap<K,V>` is a `Map<K,V>`. No unwrapping needed.

## Installation

> *"I can just implement this myself. Why add a dependency?"*
>
> Totally fair! Copy the source into your project — it's MIT licensed.
> But if it saved you some time... give me a ⭐ 🥺

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

## Docs

- [Type Matrix](docs/type-matrix.md)
- [Compatibility](docs/compatibility.md)
- [Kotlin Support Policy](docs/kotlin-support-policy.md)

## Contributing

[CONTRIBUTING.md](CONTRIBUTING.md) · [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md) · [SECURITY.md](SECURITY.md)

## License

MIT. See [LICENSE](LICENSE).
