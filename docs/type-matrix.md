# Type Matrix

Java Refined currently exposes these type families.

## Refined Wrappers

### Numeric

- `PositiveInt`, `NegativeInt`, `NonNegativeInt`, `NonPositiveInt`, `NonZeroInt`, `NaturalInt`
- `PositiveLong`, `NegativeLong`, `NonNegativeLong`, `NonPositiveLong`, `NonZeroLong`, `NaturalLong`
- `PositiveByte`, `NegativeByte`, `NonNegativeByte`, `NonPositiveByte`, `NonZeroByte`, `NaturalByte`
- `PositiveShort`, `NegativeShort`, `NonNegativeShort`, `NonPositiveShort`, `NonZeroShort`, `NaturalShort`
- `PositiveFloat`, `NegativeFloat`, `NonNegativeFloat`, `NonPositiveFloat`, `NonZeroFloat`, `FiniteFloat`, `NonNaNFloat`, `ZeroToOneFloat`
- `PositiveDouble`, `NegativeDouble`, `NonNegativeDouble`, `NonPositiveDouble`, `NonZeroDouble`, `FiniteDouble`, `NonNaNDouble`, `ZeroToOneDouble`
- `PositiveBigInteger`, `NegativeBigInteger`, `NonNegativeBigInteger`, `NonPositiveBigInteger`, `NonZeroBigInteger`, `NaturalBigInteger`
- `PositiveBigDecimal`, `NegativeBigDecimal`, `NonNegativeBigDecimal`, `NonPositiveBigDecimal`, `NonZeroBigDecimal`

### Character

- `DigitChar`, `LetterChar`, `LetterOrDigitChar`, `LowerCaseChar`, `UpperCaseChar`, `WhitespaceChar`, `SpecialChar`

### String

- `NonEmptyString`, `NonBlankString`, `TrimmedString`, `UuidString`, `UriString`
- `EmailString`, `AsciiString`, `AlphabeticString`, `NumericString`, `AlphanumericString`
- `SlugString`, `LowerCaseString`, `UpperCaseString`
- `RegexString`, `UrlString`, `Ipv4String`, `Ipv6String`, `HexString`, `HexColorString`, `XmlString`, `XPathString`
- `Base64String`, `Base64UrlString`, `UlidString`, `JsonString`, `CidrV4String`, `CidrV6String`, `MacAddressString`, `SemVerString`
- `CreditCardString`, `IsbnString`, `HostnameString`, `JwtString`
- `Iso8601DateString`, `Iso8601TimeString`, `Iso8601DateTimeString`, `Iso8601DurationString`, `Iso8601PeriodString`, `TimeZoneIdString`
- `ValidByteString`, `ValidShortString`, `ValidIntString`, `ValidLongString`
- `ValidFloatString`, `ValidDoubleString`, `ValidBigIntegerString`, `ValidBigDecimalString`

### Collection

- `NonEmptyList`, `NonEmptySet`, `NonEmptyMap`, `NonEmptyDeque`, `NonEmptyIterable`
- `NonEmptyQueue`, `NonEmptySortedSet`, `NonEmptySortedMap`, `NonEmptyNavigableSet`, `NonEmptyNavigableMap`

## Control Types

- `Option`
- `Either`
- `Try`
- `Ior`
- `Validated`

## Predicates

### Numeric

- `GreaterThan`, `GreaterOrEqual`, `LessThan`, `LessOrEqual`, `EqualTo`, `NotEqualTo`
- `OpenInterval`, `ClosedInterval`, `OpenClosedInterval`, `ClosedOpenInterval`
- `EvenInt`, `OddInt`, `EvenLong`, `OddLong`, `EvenBigInteger`, `OddBigInteger`
- `DivisibleByInt`, `DivisibleByLong`, `DivisibleByBigInteger`
- `ModuloInt`, `ModuloLong`, `NonDivisibleByInt`, `NonDivisibleByLong`, `NonDivisibleByBigInteger`
- `FiniteFloatPredicate`, `FiniteDoublePredicate`, `NonNaNFloatPredicate`, `NonNaNDoublePredicate`

### String

- `NotEmpty`, `NotBlank`, `LengthAtLeast`, `LengthAtMost`, `LengthBetween`
- `MatchesRegex`, `StartsWith`, `EndsWith`, `Contains`

### Boolean

- `TrueValue`, `FalseValue`, `And`, `Or`, `Xor`, `Nand`, `Nor`, `OneOf`

### Character

- `IsDigitChar`, `IsLetterChar`, `IsLetterOrDigitChar`, `IsLowerCaseChar`, `IsUpperCaseChar`, `IsWhitespaceChar`, `IsSpecialChar`

### Collection

- `MinSize`, `MaxSize`, `SizeBetween`, `SizeEqual`
- `ContainsElement`, `EmptyCollection`, `ForAllElements`, `ExistsElement`
- `HeadSatisfies`, `LastSatisfies`, `IndexSatisfies`, `InitSatisfies`, `TailSatisfies`, `CountMatches`
- `AscendingList`, `DescendingList`

### Logical

- `AllOf`, `AnyOf`, `Not`
