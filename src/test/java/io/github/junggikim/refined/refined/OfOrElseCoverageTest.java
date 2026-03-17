package io.github.junggikim.refined.refined;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.junggikim.refined.refined.character.DigitChar;
import io.github.junggikim.refined.refined.character.LetterChar;
import io.github.junggikim.refined.refined.character.LetterOrDigitChar;
import io.github.junggikim.refined.refined.character.LowerCaseChar;
import io.github.junggikim.refined.refined.character.SpecialChar;
import io.github.junggikim.refined.refined.character.UpperCaseChar;
import io.github.junggikim.refined.refined.character.WhitespaceChar;
import io.github.junggikim.refined.refined.numeric.FiniteDouble;
import io.github.junggikim.refined.refined.numeric.FiniteFloat;
import io.github.junggikim.refined.refined.numeric.NaturalBigInteger;
import io.github.junggikim.refined.refined.numeric.NaturalByte;
import io.github.junggikim.refined.refined.numeric.NaturalInt;
import io.github.junggikim.refined.refined.numeric.NaturalLong;
import io.github.junggikim.refined.refined.numeric.NaturalShort;
import io.github.junggikim.refined.refined.numeric.NegativeBigDecimal;
import io.github.junggikim.refined.refined.numeric.NegativeBigInteger;
import io.github.junggikim.refined.refined.numeric.NegativeByte;
import io.github.junggikim.refined.refined.numeric.NegativeDouble;
import io.github.junggikim.refined.refined.numeric.NegativeFloat;
import io.github.junggikim.refined.refined.numeric.NegativeInt;
import io.github.junggikim.refined.refined.numeric.NegativeLong;
import io.github.junggikim.refined.refined.numeric.NegativeShort;
import io.github.junggikim.refined.refined.numeric.NonNaNDouble;
import io.github.junggikim.refined.refined.numeric.NonNaNFloat;
import io.github.junggikim.refined.refined.numeric.NonNegativeBigDecimal;
import io.github.junggikim.refined.refined.numeric.NonNegativeBigInteger;
import io.github.junggikim.refined.refined.numeric.NonNegativeByte;
import io.github.junggikim.refined.refined.numeric.NonNegativeDouble;
import io.github.junggikim.refined.refined.numeric.NonNegativeFloat;
import io.github.junggikim.refined.refined.numeric.NonNegativeInt;
import io.github.junggikim.refined.refined.numeric.NonNegativeLong;
import io.github.junggikim.refined.refined.numeric.NonNegativeShort;
import io.github.junggikim.refined.refined.numeric.NonPositiveBigDecimal;
import io.github.junggikim.refined.refined.numeric.NonPositiveBigInteger;
import io.github.junggikim.refined.refined.numeric.NonPositiveByte;
import io.github.junggikim.refined.refined.numeric.NonPositiveDouble;
import io.github.junggikim.refined.refined.numeric.NonPositiveFloat;
import io.github.junggikim.refined.refined.numeric.NonPositiveInt;
import io.github.junggikim.refined.refined.numeric.NonPositiveLong;
import io.github.junggikim.refined.refined.numeric.NonPositiveShort;
import io.github.junggikim.refined.refined.numeric.NonZeroBigDecimal;
import io.github.junggikim.refined.refined.numeric.NonZeroBigInteger;
import io.github.junggikim.refined.refined.numeric.NonZeroByte;
import io.github.junggikim.refined.refined.numeric.NonZeroDouble;
import io.github.junggikim.refined.refined.numeric.NonZeroFloat;
import io.github.junggikim.refined.refined.numeric.NonZeroInt;
import io.github.junggikim.refined.refined.numeric.NonZeroLong;
import io.github.junggikim.refined.refined.numeric.NonZeroShort;
import io.github.junggikim.refined.refined.numeric.PositiveBigDecimal;
import io.github.junggikim.refined.refined.numeric.PositiveBigInteger;
import io.github.junggikim.refined.refined.numeric.PositiveByte;
import io.github.junggikim.refined.refined.numeric.PositiveDouble;
import io.github.junggikim.refined.refined.numeric.PositiveFloat;
import io.github.junggikim.refined.refined.numeric.PositiveInt;
import io.github.junggikim.refined.refined.numeric.PositiveLong;
import io.github.junggikim.refined.refined.numeric.PositiveShort;
import io.github.junggikim.refined.refined.numeric.ZeroToOneDouble;
import io.github.junggikim.refined.refined.numeric.ZeroToOneFloat;
import io.github.junggikim.refined.refined.string.AlphabeticString;
import io.github.junggikim.refined.refined.string.AlphanumericString;
import io.github.junggikim.refined.refined.string.AsciiString;
import io.github.junggikim.refined.refined.string.Base64String;
import io.github.junggikim.refined.refined.string.Base64UrlString;
import io.github.junggikim.refined.refined.string.CidrV4String;
import io.github.junggikim.refined.refined.string.CidrV6String;
import io.github.junggikim.refined.refined.string.CreditCardString;
import io.github.junggikim.refined.refined.string.EmailString;
import io.github.junggikim.refined.refined.string.HexColorString;
import io.github.junggikim.refined.refined.string.HexString;
import io.github.junggikim.refined.refined.string.HostnameString;
import io.github.junggikim.refined.refined.string.Ipv4String;
import io.github.junggikim.refined.refined.string.Ipv6String;
import io.github.junggikim.refined.refined.string.IsbnString;
import io.github.junggikim.refined.refined.string.Iso8601DateString;
import io.github.junggikim.refined.refined.string.Iso8601DateTimeString;
import io.github.junggikim.refined.refined.string.Iso8601DurationString;
import io.github.junggikim.refined.refined.string.Iso8601PeriodString;
import io.github.junggikim.refined.refined.string.Iso8601TimeString;
import io.github.junggikim.refined.refined.string.JsonString;
import io.github.junggikim.refined.refined.string.JwtString;
import io.github.junggikim.refined.refined.string.LowerCaseString;
import io.github.junggikim.refined.refined.string.MacAddressString;
import io.github.junggikim.refined.refined.string.NonBlankString;
import io.github.junggikim.refined.refined.string.NonEmptyString;
import io.github.junggikim.refined.refined.string.NumericString;
import io.github.junggikim.refined.refined.string.RegexString;
import io.github.junggikim.refined.refined.string.SemVerString;
import io.github.junggikim.refined.refined.string.SlugString;
import io.github.junggikim.refined.refined.string.TimeZoneIdString;
import io.github.junggikim.refined.refined.string.TrimmedString;
import io.github.junggikim.refined.refined.string.UlidString;
import io.github.junggikim.refined.refined.string.UpperCaseString;
import io.github.junggikim.refined.refined.string.UriString;
import io.github.junggikim.refined.refined.string.UrlString;
import io.github.junggikim.refined.refined.string.UuidString;
import io.github.junggikim.refined.refined.string.ValidBigDecimalString;
import io.github.junggikim.refined.refined.string.ValidBigIntegerString;
import io.github.junggikim.refined.refined.string.ValidByteString;
import io.github.junggikim.refined.refined.string.ValidDoubleString;
import io.github.junggikim.refined.refined.string.ValidFloatString;
import io.github.junggikim.refined.refined.string.ValidIntString;
import io.github.junggikim.refined.refined.string.ValidLongString;
import io.github.junggikim.refined.refined.string.ValidShortString;
import io.github.junggikim.refined.refined.string.XPathString;
import io.github.junggikim.refined.refined.string.XmlString;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;

/**
 * Ensures every ofOrElse method is executed for JaCoCo coverage.
 * Each type has both a valid-input and an invalid-input (fallback) test path.
 */
class OfOrElseCoverageTest {

    // --- Numeric: Int ---
    @Test void positiveIntValid() { assertEquals(5, PositiveInt.ofOrElse(5, 1).value()); }
    @Test void positiveIntFallback() { assertEquals(1, PositiveInt.ofOrElse(0, 1).value()); }
    @Test void negativeIntValid() { assertEquals(-5, NegativeInt.ofOrElse(-5, -1).value()); }
    @Test void negativeIntFallback() { assertEquals(-1, NegativeInt.ofOrElse(0, -1).value()); }
    @Test void nonNegativeIntValid() { assertEquals(0, NonNegativeInt.ofOrElse(0, 1).value()); }
    @Test void nonNegativeIntFallback() { assertEquals(1, NonNegativeInt.ofOrElse(-1, 1).value()); }
    @Test void nonPositiveIntValid() { assertEquals(0, NonPositiveInt.ofOrElse(0, -1).value()); }
    @Test void nonPositiveIntFallback() { assertEquals(-1, NonPositiveInt.ofOrElse(1, -1).value()); }
    @Test void nonZeroIntValid() { assertEquals(5, NonZeroInt.ofOrElse(5, 1).value()); }
    @Test void nonZeroIntFallback() { assertEquals(1, NonZeroInt.ofOrElse(0, 1).value()); }
    @Test void naturalIntValid() { assertEquals(0, NaturalInt.ofOrElse(0, 1).value()); }
    @Test void naturalIntFallback() { assertEquals(1, NaturalInt.ofOrElse(-1, 1).value()); }

    // --- Numeric: Long ---
    @Test void positiveLongValid() { assertEquals(5L, PositiveLong.ofOrElse(5L, 1L).value()); }
    @Test void positiveLongFallback() { assertEquals(1L, PositiveLong.ofOrElse(0L, 1L).value()); }
    @Test void negativeLongValid() { assertEquals(-5L, NegativeLong.ofOrElse(-5L, -1L).value()); }
    @Test void negativeLongFallback() { assertEquals(-1L, NegativeLong.ofOrElse(0L, -1L).value()); }
    @Test void nonNegativeLongValid() { assertEquals(0L, NonNegativeLong.ofOrElse(0L, 1L).value()); }
    @Test void nonNegativeLongFallback() { assertEquals(1L, NonNegativeLong.ofOrElse(-1L, 1L).value()); }
    @Test void nonPositiveLongValid() { assertEquals(0L, NonPositiveLong.ofOrElse(0L, -1L).value()); }
    @Test void nonPositiveLongFallback() { assertEquals(-1L, NonPositiveLong.ofOrElse(1L, -1L).value()); }
    @Test void nonZeroLongValid() { assertEquals(5L, NonZeroLong.ofOrElse(5L, 1L).value()); }
    @Test void nonZeroLongFallback() { assertEquals(1L, NonZeroLong.ofOrElse(0L, 1L).value()); }
    @Test void naturalLongValid() { assertEquals(0L, NaturalLong.ofOrElse(0L, 1L).value()); }
    @Test void naturalLongFallback() { assertEquals(1L, NaturalLong.ofOrElse(-1L, 1L).value()); }

    // --- Numeric: Byte ---
    @Test void positiveByteValid() { assertEquals((byte) 5, PositiveByte.ofOrElse((byte) 5, (byte) 1).value()); }
    @Test void positiveByteFallback() { assertEquals((byte) 1, PositiveByte.ofOrElse((byte) 0, (byte) 1).value()); }
    @Test void negativeByteValid() { assertEquals((byte) -5, NegativeByte.ofOrElse((byte) -5, (byte) -1).value()); }
    @Test void negativeByteFallback() { assertEquals((byte) -1, NegativeByte.ofOrElse((byte) 0, (byte) -1).value()); }
    @Test void nonNegativeByteValid() { assertEquals((byte) 0, NonNegativeByte.ofOrElse((byte) 0, (byte) 1).value()); }
    @Test void nonNegativeByteFallback() { assertEquals((byte) 1, NonNegativeByte.ofOrElse((byte) -1, (byte) 1).value()); }
    @Test void nonPositiveByteValid() { assertEquals((byte) 0, NonPositiveByte.ofOrElse((byte) 0, (byte) -1).value()); }
    @Test void nonPositiveByteFallback() { assertEquals((byte) -1, NonPositiveByte.ofOrElse((byte) 1, (byte) -1).value()); }
    @Test void nonZeroByteValid() { assertEquals((byte) 5, NonZeroByte.ofOrElse((byte) 5, (byte) 1).value()); }
    @Test void nonZeroByteFallback() { assertEquals((byte) 1, NonZeroByte.ofOrElse((byte) 0, (byte) 1).value()); }
    @Test void naturalByteValid() { assertEquals((byte) 0, NaturalByte.ofOrElse((byte) 0, (byte) 1).value()); }
    @Test void naturalByteFallback() { assertEquals((byte) 1, NaturalByte.ofOrElse((byte) -1, (byte) 1).value()); }

    // --- Numeric: Short ---
    @Test void positiveShortValid() { assertEquals((short) 5, PositiveShort.ofOrElse((short) 5, (short) 1).value()); }
    @Test void positiveShortFallback() { assertEquals((short) 1, PositiveShort.ofOrElse((short) 0, (short) 1).value()); }
    @Test void negativeShortValid() { assertEquals((short) -5, NegativeShort.ofOrElse((short) -5, (short) -1).value()); }
    @Test void negativeShortFallback() { assertEquals((short) -1, NegativeShort.ofOrElse((short) 0, (short) -1).value()); }
    @Test void nonNegativeShortValid() { assertEquals((short) 0, NonNegativeShort.ofOrElse((short) 0, (short) 1).value()); }
    @Test void nonNegativeShortFallback() { assertEquals((short) 1, NonNegativeShort.ofOrElse((short) -1, (short) 1).value()); }
    @Test void nonPositiveShortValid() { assertEquals((short) 0, NonPositiveShort.ofOrElse((short) 0, (short) -1).value()); }
    @Test void nonPositiveShortFallback() { assertEquals((short) -1, NonPositiveShort.ofOrElse((short) 1, (short) -1).value()); }
    @Test void nonZeroShortValid() { assertEquals((short) 5, NonZeroShort.ofOrElse((short) 5, (short) 1).value()); }
    @Test void nonZeroShortFallback() { assertEquals((short) 1, NonZeroShort.ofOrElse((short) 0, (short) 1).value()); }
    @Test void naturalShortValid() { assertEquals((short) 0, NaturalShort.ofOrElse((short) 0, (short) 1).value()); }
    @Test void naturalShortFallback() { assertEquals((short) 1, NaturalShort.ofOrElse((short) -1, (short) 1).value()); }

    // --- Numeric: Float ---
    @Test void positiveFloatValid() { assertEquals(1.5f, PositiveFloat.ofOrElse(1.5f, 1.0f).value()); }
    @Test void positiveFloatFallback() { assertEquals(1.0f, PositiveFloat.ofOrElse(0.0f, 1.0f).value()); }
    @Test void negativeFloatValid() { assertEquals(-1.5f, NegativeFloat.ofOrElse(-1.5f, -1.0f).value()); }
    @Test void negativeFloatFallback() { assertEquals(-1.0f, NegativeFloat.ofOrElse(0.0f, -1.0f).value()); }
    @Test void nonNegativeFloatValid() { assertEquals(0.0f, NonNegativeFloat.ofOrElse(0.0f, 1.0f).value()); }
    @Test void nonNegativeFloatFallback() { assertEquals(1.0f, NonNegativeFloat.ofOrElse(-1.0f, 1.0f).value()); }
    @Test void nonPositiveFloatValid() { assertEquals(-0.0f, NonPositiveFloat.ofOrElse(-0.0f, -1.0f).value()); }
    @Test void nonPositiveFloatFallback() { assertEquals(-1.0f, NonPositiveFloat.ofOrElse(1.0f, -1.0f).value()); }
    @Test void nonZeroFloatValid() { assertEquals(1.0f, NonZeroFloat.ofOrElse(1.0f, 2.0f).value()); }
    @Test void nonZeroFloatFallback() { assertEquals(2.0f, NonZeroFloat.ofOrElse(0.0f, 2.0f).value()); }
    @Test void finiteFloatValid() { assertEquals(1.0f, FiniteFloat.ofOrElse(1.0f, 2.0f).value()); }
    @Test void finiteFloatFallback() { assertEquals(2.0f, FiniteFloat.ofOrElse(Float.POSITIVE_INFINITY, 2.0f).value()); }
    @Test void nonNaNFloatValid() { assertEquals(1.0f, NonNaNFloat.ofOrElse(1.0f, 2.0f).value()); }
    @Test void nonNaNFloatFallback() { assertEquals(2.0f, NonNaNFloat.ofOrElse(Float.NaN, 2.0f).value()); }
    @Test void zeroToOneFloatValid() { assertEquals(0.5f, ZeroToOneFloat.ofOrElse(0.5f, 0.0f).value()); }
    @Test void zeroToOneFloatFallback() { assertEquals(0.0f, ZeroToOneFloat.ofOrElse(2.0f, 0.0f).value()); }

    // --- Numeric: Double ---
    @Test void positiveDoubleValid() { assertEquals(1.5d, PositiveDouble.ofOrElse(1.5d, 1.0d).value()); }
    @Test void positiveDoubleFallback() { assertEquals(1.0d, PositiveDouble.ofOrElse(0.0d, 1.0d).value()); }
    @Test void negativeDoubleValid() { assertEquals(-1.5d, NegativeDouble.ofOrElse(-1.5d, -1.0d).value()); }
    @Test void negativeDoubleFallback() { assertEquals(-1.0d, NegativeDouble.ofOrElse(0.0d, -1.0d).value()); }
    @Test void nonNegativeDoubleValid() { assertEquals(0.0d, NonNegativeDouble.ofOrElse(0.0d, 1.0d).value()); }
    @Test void nonNegativeDoubleFallback() { assertEquals(1.0d, NonNegativeDouble.ofOrElse(-1.0d, 1.0d).value()); }
    @Test void nonPositiveDoubleValid() { assertEquals(-0.0d, NonPositiveDouble.ofOrElse(-0.0d, -1.0d).value()); }
    @Test void nonPositiveDoubleFallback() { assertEquals(-1.0d, NonPositiveDouble.ofOrElse(1.0d, -1.0d).value()); }
    @Test void nonZeroDoubleValid() { assertEquals(1.0d, NonZeroDouble.ofOrElse(1.0d, 2.0d).value()); }
    @Test void nonZeroDoubleFallback() { assertEquals(2.0d, NonZeroDouble.ofOrElse(0.0d, 2.0d).value()); }
    @Test void finiteDoubleValid() { assertEquals(1.0d, FiniteDouble.ofOrElse(1.0d, 2.0d).value()); }
    @Test void finiteDoubleFallback() { assertEquals(2.0d, FiniteDouble.ofOrElse(Double.POSITIVE_INFINITY, 2.0d).value()); }
    @Test void nonNaNDoubleValid() { assertEquals(1.0d, NonNaNDouble.ofOrElse(1.0d, 2.0d).value()); }
    @Test void nonNaNDoubleFallback() { assertEquals(2.0d, NonNaNDouble.ofOrElse(Double.NaN, 2.0d).value()); }
    @Test void zeroToOneDoubleValid() { assertEquals(0.5d, ZeroToOneDouble.ofOrElse(0.5d, 0.0d).value()); }
    @Test void zeroToOneDoubleFallback() { assertEquals(0.0d, ZeroToOneDouble.ofOrElse(2.0d, 0.0d).value()); }

    // --- Numeric: BigInteger ---
    @Test void positiveBigIntegerValid() { assertEquals(BigInteger.TEN, PositiveBigInteger.ofOrElse(BigInteger.TEN, BigInteger.ONE).value()); }
    @Test void positiveBigIntegerFallback() { assertEquals(BigInteger.ONE, PositiveBigInteger.ofOrElse(BigInteger.ZERO, BigInteger.ONE).value()); }
    @Test void negativeBigIntegerValid() { assertEquals(BigInteger.valueOf(-5), NegativeBigInteger.ofOrElse(BigInteger.valueOf(-5), BigInteger.valueOf(-1)).value()); }
    @Test void negativeBigIntegerFallback() { assertEquals(BigInteger.valueOf(-1), NegativeBigInteger.ofOrElse(BigInteger.ZERO, BigInteger.valueOf(-1)).value()); }
    @Test void nonNegativeBigIntegerValid() { assertEquals(BigInteger.ZERO, NonNegativeBigInteger.ofOrElse(BigInteger.ZERO, BigInteger.ONE).value()); }
    @Test void nonNegativeBigIntegerFallback() { assertEquals(BigInteger.ONE, NonNegativeBigInteger.ofOrElse(BigInteger.valueOf(-1), BigInteger.ONE).value()); }
    @Test void nonPositiveBigIntegerValid() { assertEquals(BigInteger.ZERO, NonPositiveBigInteger.ofOrElse(BigInteger.ZERO, BigInteger.valueOf(-1)).value()); }
    @Test void nonPositiveBigIntegerFallback() { assertEquals(BigInteger.valueOf(-1), NonPositiveBigInteger.ofOrElse(BigInteger.ONE, BigInteger.valueOf(-1)).value()); }
    @Test void nonZeroBigIntegerValid() { assertEquals(BigInteger.TEN, NonZeroBigInteger.ofOrElse(BigInteger.TEN, BigInteger.ONE).value()); }
    @Test void nonZeroBigIntegerFallback() { assertEquals(BigInteger.ONE, NonZeroBigInteger.ofOrElse(BigInteger.ZERO, BigInteger.ONE).value()); }
    @Test void naturalBigIntegerValid() { assertEquals(BigInteger.ZERO, NaturalBigInteger.ofOrElse(BigInteger.ZERO, BigInteger.ONE).value()); }
    @Test void naturalBigIntegerFallback() { assertEquals(BigInteger.ONE, NaturalBigInteger.ofOrElse(BigInteger.valueOf(-1), BigInteger.ONE).value()); }

    // --- Numeric: BigDecimal ---
    @Test void positiveBigDecimalValid() { assertEquals(new BigDecimal("5.0"), PositiveBigDecimal.ofOrElse(new BigDecimal("5.0"), BigDecimal.ONE).value()); }
    @Test void positiveBigDecimalFallback() { assertEquals(BigDecimal.ONE, PositiveBigDecimal.ofOrElse(BigDecimal.ZERO, BigDecimal.ONE).value()); }
    @Test void negativeBigDecimalValid() { assertEquals(new BigDecimal("-5.0"), NegativeBigDecimal.ofOrElse(new BigDecimal("-5.0"), new BigDecimal("-1.0")).value()); }
    @Test void negativeBigDecimalFallback() { assertEquals(new BigDecimal("-1.0"), NegativeBigDecimal.ofOrElse(BigDecimal.ZERO, new BigDecimal("-1.0")).value()); }
    @Test void nonNegativeBigDecimalValid() { assertEquals(BigDecimal.ZERO, NonNegativeBigDecimal.ofOrElse(BigDecimal.ZERO, BigDecimal.ONE).value()); }
    @Test void nonNegativeBigDecimalFallback() { assertEquals(BigDecimal.ONE, NonNegativeBigDecimal.ofOrElse(new BigDecimal("-1"), BigDecimal.ONE).value()); }
    @Test void nonPositiveBigDecimalValid() { assertEquals(BigDecimal.ZERO, NonPositiveBigDecimal.ofOrElse(BigDecimal.ZERO, new BigDecimal("-1")).value()); }
    @Test void nonPositiveBigDecimalFallback() { assertEquals(new BigDecimal("-1"), NonPositiveBigDecimal.ofOrElse(BigDecimal.ONE, new BigDecimal("-1")).value()); }
    @Test void nonZeroBigDecimalValid() { assertEquals(BigDecimal.TEN, NonZeroBigDecimal.ofOrElse(BigDecimal.TEN, BigDecimal.ONE).value()); }
    @Test void nonZeroBigDecimalFallback() { assertEquals(BigDecimal.ONE, NonZeroBigDecimal.ofOrElse(BigDecimal.ZERO, BigDecimal.ONE).value()); }

    // --- Character ---
    @Test void digitCharValid() { assertEquals('5', DigitChar.ofOrElse('5', '0').value()); }
    @Test void digitCharFallback() { assertEquals('0', DigitChar.ofOrElse('a', '0').value()); }
    @Test void letterCharValid() { assertEquals('a', LetterChar.ofOrElse('a', 'b').value()); }
    @Test void letterCharFallback() { assertEquals('b', LetterChar.ofOrElse('1', 'b').value()); }
    @Test void letterOrDigitCharValid() { assertEquals('a', LetterOrDigitChar.ofOrElse('a', 'b').value()); }
    @Test void letterOrDigitCharFallback() { assertEquals('b', LetterOrDigitChar.ofOrElse('-', 'b').value()); }
    @Test void lowerCaseCharValid() { assertEquals('a', LowerCaseChar.ofOrElse('a', 'b').value()); }
    @Test void lowerCaseCharFallback() { assertEquals('b', LowerCaseChar.ofOrElse('A', 'b').value()); }
    @Test void upperCaseCharValid() { assertEquals('A', UpperCaseChar.ofOrElse('A', 'B').value()); }
    @Test void upperCaseCharFallback() { assertEquals('B', UpperCaseChar.ofOrElse('a', 'B').value()); }
    @Test void whitespaceCharValid() { assertEquals(' ', WhitespaceChar.ofOrElse(' ', '\t').value()); }
    @Test void whitespaceCharFallback() { assertEquals('\t', WhitespaceChar.ofOrElse('a', '\t').value()); }
    @Test void specialCharValid() { assertEquals('!', SpecialChar.ofOrElse('!', '@').value()); }
    @Test void specialCharFallback() { assertEquals('@', SpecialChar.ofOrElse('a', '@').value()); }

    // --- String ---
    @Test void nonBlankStringValid() { assertEquals("hello", NonBlankString.ofOrElse("hello", "x").value()); }
    @Test void nonBlankStringFallback() { assertEquals("x", NonBlankString.ofOrElse("   ", "x").value()); }
    @Test void nonEmptyStringValid() { assertEquals("a", NonEmptyString.ofOrElse("a", "x").value()); }
    @Test void nonEmptyStringFallback() { assertEquals("x", NonEmptyString.ofOrElse("", "x").value()); }
    @Test void trimmedStringValid() { assertEquals("abc", TrimmedString.ofOrElse("abc", "x").value()); }
    @Test void trimmedStringFallback() { assertEquals("x", TrimmedString.ofOrElse(" abc ", "x").value()); }
    @Test void alphabeticStringValid() { assertEquals("abc", AlphabeticString.ofOrElse("abc", "x").value()); }
    @Test void alphabeticStringFallback() { assertEquals("x", AlphabeticString.ofOrElse("123", "x").value()); }
    @Test void alphanumericStringValid() { assertEquals("abc123", AlphanumericString.ofOrElse("abc123", "x").value()); }
    @Test void alphanumericStringFallback() { assertEquals("x", AlphanumericString.ofOrElse("abc-", "x").value()); }
    @Test void asciiStringValid() { assertEquals("hello", AsciiString.ofOrElse("hello", "x").value()); }
    @Test void asciiStringFallback() { assertEquals("x", AsciiString.ofOrElse("\u00E9", "x").value()); }
    @Test void numericStringValid() { assertEquals("123", NumericString.ofOrElse("123", "0").value()); }
    @Test void numericStringFallback() { assertEquals("0", NumericString.ofOrElse("abc", "0").value()); }
    @Test void lowerCaseStringValid() { assertEquals("abc", LowerCaseString.ofOrElse("abc", "x").value()); }
    @Test void lowerCaseStringFallback() { assertEquals("x", LowerCaseString.ofOrElse("ABC", "x").value()); }
    @Test void upperCaseStringValid() { assertEquals("ABC", UpperCaseString.ofOrElse("ABC", "X").value()); }
    @Test void upperCaseStringFallback() { assertEquals("X", UpperCaseString.ofOrElse("abc", "X").value()); }
    @Test void emailStringValid() { assertEquals("a@b.com", EmailString.ofOrElse("a@b.com", "x@y.com").value()); }
    @Test void emailStringFallback() { assertEquals("x@y.com", EmailString.ofOrElse("invalid", "x@y.com").value()); }
    @Test void uuidStringValid() { assertEquals("123e4567-e89b-12d3-a456-426614174000", UuidString.ofOrElse("123e4567-e89b-12d3-a456-426614174000", "00000000-0000-0000-0000-000000000000").value()); }
    @Test void uuidStringFallback() { assertEquals("00000000-0000-0000-0000-000000000000", UuidString.ofOrElse("bad", "00000000-0000-0000-0000-000000000000").value()); }
    @Test void uriStringValid() { assertEquals("https://x.com", UriString.ofOrElse("https://x.com", "https://y.com").value()); }
    @Test void uriStringFallback() { assertEquals("https://y.com", UriString.ofOrElse("://bad", "https://y.com").value()); }
    @Test void urlStringValid() { assertEquals("https://x.com/p", UrlString.ofOrElse("https://x.com/p", "https://y.com").value()); }
    @Test void urlStringFallback() { assertEquals("https://y.com", UrlString.ofOrElse("ht^tp://bad", "https://y.com").value()); }
    @Test void ipv4StringValid() { assertEquals("192.168.0.1", Ipv4String.ofOrElse("192.168.0.1", "127.0.0.1").value()); }
    @Test void ipv4StringFallback() { assertEquals("127.0.0.1", Ipv4String.ofOrElse("256.1.1.1", "127.0.0.1").value()); }
    @Test void ipv6StringValid() { assertEquals("2001:db8::1", Ipv6String.ofOrElse("2001:db8::1", "::1").value()); }
    @Test void ipv6StringFallback() { assertEquals("::1", Ipv6String.ofOrElse("bad", "::1").value()); }
    @Test void hexStringValid() { assertEquals("deadBEEF", HexString.ofOrElse("deadBEEF", "00").value()); }
    @Test void hexStringFallback() { assertEquals("00", HexString.ofOrElse("xyz", "00").value()); }
    @Test void hexColorStringValid() { assertEquals("#AABBCC", HexColorString.ofOrElse("#AABBCC", "#000000").value()); }
    @Test void hexColorStringFallback() { assertEquals("#000000", HexColorString.ofOrElse("bad", "#000000").value()); }
    @Test void regexStringValid() { assertEquals("[a-z]+", RegexString.ofOrElse("[a-z]+", ".*").value()); }
    @Test void regexStringFallback() { assertEquals(".*", RegexString.ofOrElse("[a-z", ".*").value()); }
    @Test void xmlStringValid() { assertEquals("<r/>", XmlString.ofOrElse("<r/>", "<x/>").value()); }
    @Test void xmlStringFallback() { assertEquals("<x/>", XmlString.ofOrElse("<r>", "<x/>").value()); }
    @Test void xpathStringValid() { assertEquals("/root", XPathString.ofOrElse("/root", "/x").value()); }
    @Test void xpathStringFallback() { assertEquals("/x", XPathString.ofOrElse("//*[", "/x").value()); }
    @Test void base64StringValid() { assertEquals("aGVsbG8=", Base64String.ofOrElse("aGVsbG8=", "eA==").value()); }
    @Test void base64StringFallback() { assertEquals("eA==", Base64String.ofOrElse("!!!!", "eA==").value()); }
    @Test void base64UrlStringValid() { assertEquals("aGVsbG8", Base64UrlString.ofOrElse("aGVsbG8", "eA").value()); }
    @Test void base64UrlStringFallback() { assertEquals("eA", Base64UrlString.ofOrElse("!!!", "eA").value()); }
    @Test void jsonStringValid() { assertEquals("{}", JsonString.ofOrElse("{}", "[]").value()); }
    @Test void jsonStringFallback() { assertEquals("[]", JsonString.ofOrElse("{bad", "[]").value()); }
    @Test void slugStringValid() { assertEquals("hello-world", SlugString.ofOrElse("hello-world", "x").value()); }
    @Test void slugStringFallback() { assertEquals("x", SlugString.ofOrElse("Hello World!", "x").value()); }
    @Test void semVerStringValid() { assertEquals("1.0.0", SemVerString.ofOrElse("1.0.0", "0.0.1").value()); }
    @Test void semVerStringFallback() { assertEquals("0.0.1", SemVerString.ofOrElse("bad", "0.0.1").value()); }
    @Test void hostnameStringValid() { assertEquals("example.com", HostnameString.ofOrElse("example.com", "localhost").value()); }
    @Test void hostnameStringFallback() { assertEquals("localhost", HostnameString.ofOrElse("-bad", "localhost").value()); }
    @Test void macAddressStringValid() { assertEquals("00:11:22:33:44:55", MacAddressString.ofOrElse("00:11:22:33:44:55", "00:00:00:00:00:00").value()); }
    @Test void macAddressStringFallback() { assertEquals("00:00:00:00:00:00", MacAddressString.ofOrElse("bad", "00:00:00:00:00:00").value()); }
    @Test void cidrV4StringValid() { assertEquals("192.168.0.0/24", CidrV4String.ofOrElse("192.168.0.0/24", "10.0.0.0/8").value()); }
    @Test void cidrV4StringFallback() { assertEquals("10.0.0.0/8", CidrV4String.ofOrElse("bad", "10.0.0.0/8").value()); }
    @Test void cidrV6StringValid() { assertEquals("2001:db8::/32", CidrV6String.ofOrElse("2001:db8::/32", "::1/128").value()); }
    @Test void cidrV6StringFallback() { assertEquals("::1/128", CidrV6String.ofOrElse("bad", "::1/128").value()); }
    @Test void creditCardStringValid() { assertEquals("4111111111111111", CreditCardString.ofOrElse("4111111111111111", "5500000000000004").value()); }
    @Test void creditCardStringFallback() { assertEquals("5500000000000004", CreditCardString.ofOrElse("bad", "5500000000000004").value()); }
    @Test void isbnStringValid() { assertEquals("978-3-16-148410-0", IsbnString.ofOrElse("978-3-16-148410-0", "0-306-40615-2").value()); }
    @Test void isbnStringFallback() { assertEquals("0-306-40615-2", IsbnString.ofOrElse("bad", "0-306-40615-2").value()); }
    @Test void iso8601DateStringValid() { assertEquals("2026-01-01", Iso8601DateString.ofOrElse("2026-01-01", "2000-01-01").value()); }
    @Test void iso8601DateStringFallback() { assertEquals("2000-01-01", Iso8601DateString.ofOrElse("bad", "2000-01-01").value()); }
    @Test void iso8601TimeStringValid() { assertEquals("10:30:00", Iso8601TimeString.ofOrElse("10:30:00", "00:00:00").value()); }
    @Test void iso8601TimeStringFallback() { assertEquals("00:00:00", Iso8601TimeString.ofOrElse("bad", "00:00:00").value()); }
    @Test void iso8601DateTimeStringValid() { assertEquals("2026-01-01T10:30:00", Iso8601DateTimeString.ofOrElse("2026-01-01T10:30:00", "2000-01-01T00:00:00").value()); }
    @Test void iso8601DateTimeStringFallback() { assertEquals("2000-01-01T00:00:00", Iso8601DateTimeString.ofOrElse("bad", "2000-01-01T00:00:00").value()); }
    @Test void iso8601DurationStringValid() { assertEquals("PT1H", Iso8601DurationString.ofOrElse("PT1H", "PT0S").value()); }
    @Test void iso8601DurationStringFallback() { assertEquals("PT0S", Iso8601DurationString.ofOrElse("bad", "PT0S").value()); }
    @Test void iso8601PeriodStringValid() { assertEquals("P1Y", Iso8601PeriodString.ofOrElse("P1Y", "P0D").value()); }
    @Test void iso8601PeriodStringFallback() { assertEquals("P0D", Iso8601PeriodString.ofOrElse("bad", "P0D").value()); }
    @Test void timeZoneIdStringValid() { assertEquals("UTC", TimeZoneIdString.ofOrElse("UTC", "GMT").value()); }
    @Test void timeZoneIdStringFallback() { assertEquals("GMT", TimeZoneIdString.ofOrElse("Invalid/Zone", "GMT").value()); }
    @Test void ulidStringValid() { assertEquals("01ARZ3NDEKTSV4RRFFQ69G5FAV", UlidString.ofOrElse("01ARZ3NDEKTSV4RRFFQ69G5FAV", "00000000000000000000000000").value()); }
    @Test void ulidStringFallback() { assertEquals("00000000000000000000000000", UlidString.ofOrElse("bad", "00000000000000000000000000").value()); }
    @Test void jwtStringValid() {
        String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIn0.dozjgNryP4J3jVmNHl0w5N_XgL0n3I9PlFUP0THsR8U";
        String def = "eyJhbGciOiJub25lIn0.eyJzdWIiOiIxIn0.";
        assertEquals(jwt, JwtString.ofOrElse(jwt, def).value());
    }
    @Test void jwtStringFallback() {
        String def = "eyJhbGciOiJub25lIn0.eyJzdWIiOiIxIn0.";
        assertEquals(def, JwtString.ofOrElse("bad", def).value());
    }

    // --- Numeric string parseable types ---
    @Test void validByteStringValid() { assertEquals("127", ValidByteString.ofOrElse("127", "0").value()); }
    @Test void validByteStringFallback() { assertEquals("0", ValidByteString.ofOrElse("128", "0").value()); }
    @Test void validShortStringValid() { assertEquals("32767", ValidShortString.ofOrElse("32767", "0").value()); }
    @Test void validShortStringFallback() { assertEquals("0", ValidShortString.ofOrElse("40000", "0").value()); }
    @Test void validIntStringValid() { assertEquals("42", ValidIntString.ofOrElse("42", "0").value()); }
    @Test void validIntStringFallback() { assertEquals("0", ValidIntString.ofOrElse("2147483648", "0").value()); }
    @Test void validLongStringValid() { assertEquals("42", ValidLongString.ofOrElse("42", "0").value()); }
    @Test void validLongStringFallback() { assertEquals("0", ValidLongString.ofOrElse("9223372036854775808", "0").value()); }
    @Test void validFloatStringValid() { assertEquals("1.5", ValidFloatString.ofOrElse("1.5", "0").value()); }
    @Test void validFloatStringFallback() { assertEquals("0", ValidFloatString.ofOrElse("not-a-float", "0").value()); }
    @Test void validDoubleStringValid() { assertEquals("1.5", ValidDoubleString.ofOrElse("1.5", "0").value()); }
    @Test void validDoubleStringFallback() { assertEquals("0", ValidDoubleString.ofOrElse("not-a-double", "0").value()); }
    @Test void validBigIntegerStringValid() { assertEquals("123", ValidBigIntegerString.ofOrElse("123", "0").value()); }
    @Test void validBigIntegerStringFallback() { assertEquals("0", ValidBigIntegerString.ofOrElse("12.3", "0").value()); }
    @Test void validBigDecimalStringValid() { assertEquals("123.45", ValidBigDecimalString.ofOrElse("123.45", "0").value()); }
    @Test void validBigDecimalStringFallback() { assertEquals("0", ValidBigDecimalString.ofOrElse("12a", "0").value()); }
}
