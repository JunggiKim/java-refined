package io.github.junggikim.refined.kotlin

import io.github.junggikim.refined.core.Refined
import io.github.junggikim.refined.core.RefinementException
import io.github.junggikim.refined.validation.Validation
import io.github.junggikim.refined.violation.Violation
import java.lang.reflect.InvocationTargetException
import java.math.BigDecimal
import java.math.BigInteger
import java.util.stream.Stream
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows

class ScalarKotlinExtensionsTest {

    @TestFactory
    fun stringExtensionsMirrorJavaFactories(): Stream<DynamicTest> =
        stringCases().flatMap { testsFor(it) }

    @TestFactory
    fun numericExtensionsMirrorJavaFactories(): Stream<DynamicTest> =
        numericCases().flatMap { testsFor(it) }

    @TestFactory
    fun characterExtensionsMirrorJavaFactories(): Stream<DynamicTest> =
        characterCases().flatMap { testsFor(it) }

    private fun testsFor(case: ExtensionCase): Stream<DynamicTest> = Stream.of(
        DynamicTest.dynamicTest("${case.typeName} safe extension accepts valid input") {
            val extensionResult = invokeSafe(case, case.valid)
            val javaResult = invokeJavaOf(case, case.valid)

            check(extensionResult.isValid)
            check(javaResult.isValid)
            check((extensionResult.get() as Refined<*>).value == (javaResult.get() as Refined<*>).value)
        },
        DynamicTest.dynamicTest("${case.typeName} safe extension rejects null with same code") {
            val extensionResult = invokeSafe(case, null)
            val javaResult = invokeJavaOf(case, null)

            check(extensionResult.isInvalid)
            check(javaResult.isInvalid)
            check(extensionResult.error.code == javaResult.error.code)
        },
        DynamicTest.dynamicTest("${case.typeName} unsafe extension returns same refined value") {
            val extensionValue = invokeUnsafe(case, case.valid)
            val javaValue = invokeJavaUnsafe(case, case.valid)

            check(extensionValue == javaValue)
        },
        DynamicTest.dynamicTest("${case.typeName} unsafe extension throws same violation on null") {
            val thrown = assertThrows<InvocationTargetException> {
                invokeUnsafe(case, null)
            }
            val cause = thrown.cause as RefinementException
            val javaResult = invokeJavaOf(case, null)

            check(cause.violation().code == javaResult.error.code)
        }
    )

    @Suppress("UNCHECKED_CAST")
    private fun invokeSafe(case: ExtensionCase, value: Any?): Validation<Violation, *> =
        holderClass(case).getMethod(case.safeName, case.parameterType).invoke(null, value) as Validation<Violation, *>

    @Suppress("UNCHECKED_CAST")
    private fun invokeJavaOf(case: ExtensionCase, value: Any?): Validation<Violation, *> =
        targetClass(case).getMethod("of", case.parameterType).invoke(null, value) as Validation<Violation, *>

    private fun invokeUnsafe(case: ExtensionCase, value: Any?): Any =
        holderClass(case).getMethod(case.unsafeName, case.parameterType).invoke(null, value)

    private fun invokeJavaUnsafe(case: ExtensionCase, value: Any?): Any =
        targetClass(case).getMethod("unsafeOf", case.parameterType).invoke(null, value)

    private fun holderClass(case: ExtensionCase): Class<*> =
        Class.forName("io.github.junggikim.refined.kotlin.${case.holderClassName}")

    private fun targetClass(case: ExtensionCase): Class<*> =
        Class.forName("${case.targetPackage}.${case.typeName}")

    private fun stringCases(): Stream<ExtensionCase> = Stream.of(
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "AlphabeticString", "Alphabet"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "AlphanumericString", "Abc123"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "AsciiString", "ASCII-123"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "Base64String", "SGVsbG8="),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "Base64UrlString", "____"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "CidrV4String", "192.168.1.0/24"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "CidrV6String", "2001:db8::/32"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "CreditCardString", "4111111111111111"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "EmailString", "hello@example.com"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "HexColorString", "#A1B2C3"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "HexString", "deadBEEF"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "HostnameString", "example.com"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "Ipv4String", "192.168.0.1"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "Ipv6String", "2001:db8::1"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "IsbnString", "978-3-16-148410-0"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "Iso8601DateString", "2024-01-15"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "Iso8601DateTimeString", "2024-01-15T14:30:00Z"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "Iso8601DurationString", "PT1H30M"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "Iso8601PeriodString", "P1Y2M3D"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "Iso8601TimeString", "14:30:00"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "JsonString", "{\"key\":\"value\"}"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "JwtString", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIn0.dBjftJeZ4CVP"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "LowerCaseString", "lower-case"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "MacAddressString", "AA:BB:CC:DD:EE:FF"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "NonBlankString", "abc"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "NonEmptyString", "abc"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "NumericString", "12345"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "RegexString", "[a-z]+"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "SemVerString", "1.2.3"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "SlugString", "hello-world-2"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "TimeZoneIdString", "Asia/Seoul"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "TrimmedString", "abc"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "UlidString", "01ARZ3NDEKTSV4RRFFQ69G5FAV"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "UpperCaseString", "UPPER-CASE"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "UriString", "https://example.com"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "UrlString", "https://example.com/path"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "UuidString", "123e4567-e89b-12d3-a456-426614174000"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "ValidBigDecimalString", "1234567890.1234"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "ValidBigIntegerString", "12345678901234567890"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "ValidByteString", "127"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "ValidDoubleString", "1.5"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "ValidFloatString", "1.5"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "ValidIntString", "42"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "ValidLongString", "9223372036854775807"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "ValidShortString", "32767"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "XPathString", "/root/item[@id='1']"),
        ExtensionCase("StringRefinedExtensionsKt", "io.github.junggikim.refined.refined.string", "XmlString", "<root><item/></root>")
    )

    private fun numericCases(): Stream<ExtensionCase> = Stream.of(
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "FiniteDouble", 1.0),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "FiniteFloat", 1.0f),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NaturalBigInteger", BigInteger.ZERO),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NaturalByte", 0.toByte()),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NaturalInt", 0),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NaturalLong", 0L),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NaturalShort", 0.toShort()),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NegativeBigDecimal", BigDecimal.ONE.negate()),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NegativeBigInteger", BigInteger.ONE.negate()),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NegativeByte", (-1).toByte()),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NegativeDouble", -1.5),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NegativeFloat", -1.5f),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NegativeInt", -1),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NegativeLong", -1L),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NegativeShort", (-1).toShort()),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonNaNDouble", Double.POSITIVE_INFINITY),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonNaNFloat", Float.POSITIVE_INFINITY),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonNegativeBigDecimal", BigDecimal.ZERO),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonNegativeBigInteger", BigInteger.ZERO),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonNegativeByte", 0.toByte()),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonNegativeDouble", 0.0),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonNegativeFloat", 0.0f),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonNegativeInt", 0),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonNegativeLong", 0L),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonNegativeShort", 0.toShort()),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonPositiveBigDecimal", BigDecimal.ZERO),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonPositiveBigInteger", BigInteger.ZERO),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonPositiveByte", 0.toByte()),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonPositiveDouble", -0.0),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonPositiveFloat", -0.0f),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonPositiveInt", 0),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonPositiveLong", 0L),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonPositiveShort", 0.toShort()),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonZeroBigDecimal", BigDecimal.ONE),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonZeroBigInteger", BigInteger.ONE),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonZeroByte", 1.toByte()),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonZeroDouble", 1.0),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonZeroFloat", 1.0f),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonZeroInt", -2),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonZeroLong", -1L),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "NonZeroShort", 1.toShort()),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "PositiveBigDecimal", BigDecimal.ONE),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "PositiveBigInteger", BigInteger.ONE),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "PositiveByte", 1.toByte()),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "PositiveDouble", 1.5),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "PositiveFloat", 1.5f),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "PositiveInt", 1),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "PositiveLong", 1L),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "PositiveShort", 1.toShort()),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "ZeroToOneDouble", 0.5),
        ExtensionCase("NumericRefinedExtensionsKt", "io.github.junggikim.refined.refined.numeric", "ZeroToOneFloat", 0.5f)
    )

    private fun characterCases(): Stream<ExtensionCase> = Stream.of(
        ExtensionCase("CharacterRefinedExtensionsKt", "io.github.junggikim.refined.refined.character", "DigitChar", '5'),
        ExtensionCase("CharacterRefinedExtensionsKt", "io.github.junggikim.refined.refined.character", "LetterChar", 'a'),
        ExtensionCase("CharacterRefinedExtensionsKt", "io.github.junggikim.refined.refined.character", "LetterOrDigitChar", '1'),
        ExtensionCase("CharacterRefinedExtensionsKt", "io.github.junggikim.refined.refined.character", "LowerCaseChar", 'a'),
        ExtensionCase("CharacterRefinedExtensionsKt", "io.github.junggikim.refined.refined.character", "SpecialChar", '!'),
        ExtensionCase("CharacterRefinedExtensionsKt", "io.github.junggikim.refined.refined.character", "UpperCaseChar", 'A'),
        ExtensionCase("CharacterRefinedExtensionsKt", "io.github.junggikim.refined.refined.character", "WhitespaceChar", ' ')
    )

    private data class ExtensionCase(
        val holderClassName: String,
        val targetPackage: String,
        val typeName: String,
        val valid: Any
    ) {
        val safeName: String = "to$typeName"
        val unsafeName: String = "to${typeName}OrThrow"
        val parameterType: Class<*> = valid.javaClass
    }
}
