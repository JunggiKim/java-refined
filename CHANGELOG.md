# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/)
and this project follows [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

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

## [0.1.0] - Planned

### Added

- Java 8+ refinement type library core
- numeric, character, string, and non-empty collection refined wrappers
- functional control types: `Option`, `Either`, `Try`, `Ior`, `Validated`
- composable predicate catalog
- JUnit 5 TDD suite with full JaCoCo coverage
