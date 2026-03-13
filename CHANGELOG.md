# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/)
and this project follows [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.1.0] - 2026-03-13

### Security
- JSON parser now enforces a maximum nesting depth of 512

### Added
- Maven Central deployment pipeline via `com.vanniktech.maven.publish` plugin
- GitHub Actions release workflow triggered by version tags
- PITest mutation testing with 95% kill threshold
- Javadoc for `Try.of()` clarifying Error propagation behavior
- Javadoc for `MatchesRegex` with ReDoS warning

### Changed
- Replaced `maven-publish` plugin with `com.vanniktech.maven.publish` v0.36.0

## [1.1.0] - 2026-03-12

### Added

- optional `java-refined-kotlin` support module for Kotlin/JVM consumers
- Kotlin extension functions for refined scalar and collection factories
- Kotlin read-only adapters for refined non-empty collections
- Kotlin validation helpers such as `getOrNull()`, `errorOrNull()`, and `getOrThrow()`

### Changed

- collection refined wrappers expose richer collection ergonomics while preserving immutable snapshot semantics
- documentation now distinguishes Java-only usage from optional Kotlin/JVM usage and calls out the Kotlin runtime dependency

## [1.0.0] - 2026-03-11

### Added

- Java 8+ refinement type library core
- numeric, character, string, and non-empty collection refined wrappers
- functional control types: `Option`, `Either`, `Try`, `Ior`, `Validated`
- composable predicate catalog
- JUnit 5 TDD suite with full JaCoCo coverage
- standalone OSS repository metadata and contribution files
- GitHub workflow, issue template, and PR template definitions
- refined string types: `Base64String`, `Base64UrlString`, `UlidString`, `HexColorString`, `JsonString`, `CidrV4String`, `CidrV6String`,
  `MacAddressString`, `SemVerString`, `CreditCardString`, `IsbnString`, `HostnameString`, `JwtString`, `Iso8601DateString`,
  `Iso8601TimeString`, `Iso8601DateTimeString`, `Iso8601DurationString`, `Iso8601PeriodString`, `TimeZoneIdString`
- refined numeric types: `ZeroToOneDouble`, `ZeroToOneFloat`; refined character type: `SpecialChar`
- new predicates: `ModuloInt`, `ModuloLong`, `NonDivisibleByInt`, `NonDivisibleByLong`, `NonDivisibleByBigInteger`,
  `AscendingList`, `DescendingList`, `IsSpecialChar`

### Changed

- project license moved to MIT

### Removed

- Maven Central/JReleaser release pipeline and documentation
