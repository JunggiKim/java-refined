package io.github.junggikim.refined.internal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.junggikim.refined.core.Constraint;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Verifies that every factory method in {@link RefinedSupport} returns a non-null,
 * functional {@link Constraint}. These tests exist to kill NullReturnValsMutator
 * mutations that replace factory returns with {@code null}.
 */
class RefinedSupportFactoryTest {

    // --- Numeric: Int ---

    @Test
    void positiveIntReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.positiveInt(), 1);
    }

    @Test
    void nonNegativeIntReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonNegativeInt(), 0);
    }

    @Test
    void negativeIntReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.negativeInt(), -1);
    }

    @Test
    void nonPositiveIntReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonPositiveInt(), 0);
    }

    @Test
    void naturalIntReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.naturalInt(), 0);
    }

    @Test
    void nonZeroIntReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonZeroInt(), 1);
    }

    // --- Numeric: Long ---

    @Test
    void positiveLongReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.positiveLong(), 1L);
    }

    @Test
    void negativeLongReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.negativeLong(), -1L);
    }

    @Test
    void nonNegativeLongReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonNegativeLong(), 0L);
    }

    @Test
    void nonPositiveLongReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonPositiveLong(), 0L);
    }

    @Test
    void nonZeroLongReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonZeroLong(), 1L);
    }

    @Test
    void naturalLongReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.naturalLong(), 0L);
    }

    // --- Numeric: BigInteger ---

    @Test
    void positiveBigIntegerReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.positiveBigInteger(), BigInteger.ONE);
    }

    @Test
    void negativeBigIntegerReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.negativeBigInteger(), BigInteger.ONE.negate());
    }

    @Test
    void nonNegativeBigIntegerReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonNegativeBigInteger(), BigInteger.ZERO);
    }

    @Test
    void nonPositiveBigIntegerReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonPositiveBigInteger(), BigInteger.ZERO);
    }

    @Test
    void nonZeroBigIntegerReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonZeroBigInteger(), BigInteger.ONE);
    }

    @Test
    void naturalBigIntegerReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.naturalBigInteger(), BigInteger.ZERO);
    }

    // --- Numeric: BigDecimal ---

    @Test
    void positiveBigDecimalReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.positiveBigDecimal(), BigDecimal.ONE);
    }

    @Test
    void negativeBigDecimalReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.negativeBigDecimal(), BigDecimal.ONE.negate());
    }

    @Test
    void nonNegativeBigDecimalReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonNegativeBigDecimal(), BigDecimal.ZERO);
    }

    @Test
    void nonPositiveBigDecimalReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonPositiveBigDecimal(), BigDecimal.ZERO);
    }

    @Test
    void nonZeroBigDecimalReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonZeroBigDecimal(), BigDecimal.ONE);
    }

    // --- Numeric: Byte ---

    @Test
    void positiveByteReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.positiveByte(), (byte) 1);
    }

    @Test
    void negativeByteReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.negativeByte(), (byte) -1);
    }

    @Test
    void nonNegativeByteReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonNegativeByte(), (byte) 0);
    }

    @Test
    void nonPositiveByteReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonPositiveByte(), (byte) 0);
    }

    @Test
    void nonZeroByteReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonZeroByte(), (byte) 1);
    }

    @Test
    void naturalByteReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.naturalByte(), (byte) 0);
    }

    // --- Numeric: Short ---

    @Test
    void positiveShortReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.positiveShort(), (short) 1);
    }

    @Test
    void negativeShortReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.negativeShort(), (short) -1);
    }

    @Test
    void nonNegativeShortReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonNegativeShort(), (short) 0);
    }

    @Test
    void nonPositiveShortReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonPositiveShort(), (short) 0);
    }

    @Test
    void nonZeroShortReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonZeroShort(), (short) 1);
    }

    @Test
    void naturalShortReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.naturalShort(), (short) 0);
    }

    // --- Numeric: Float ---

    @Test
    void positiveFloatReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.positiveFloat(), 1.0f);
    }

    @Test
    void negativeFloatReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.negativeFloat(), -1.0f);
    }

    @Test
    void nonNegativeFloatReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonNegativeFloat(), 0.0f);
    }

    @Test
    void nonPositiveFloatReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonPositiveFloat(), 0.0f);
    }

    @Test
    void nonZeroFloatReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonZeroFloat(), 1.0f);
    }

    @Test
    void zeroToOneFloatReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.zeroToOneFloat(), 0.5f);
    }

    // --- Numeric: Double ---

    @Test
    void positiveDoubleReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.positiveDouble(), 1.0d);
    }

    @Test
    void negativeDoubleReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.negativeDouble(), -1.0d);
    }

    @Test
    void nonNegativeDoubleReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonNegativeDouble(), 0.0d);
    }

    @Test
    void nonPositiveDoubleReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonPositiveDouble(), 0.0d);
    }

    @Test
    void nonZeroDoubleReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonZeroDouble(), 1.0d);
    }

    @Test
    void zeroToOneDoubleReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.zeroToOneDouble(), 0.5d);
    }

    // --- Parameterized factories ---

    @Test
    void greaterOrEqualReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.greaterOrEqual(0, "c", "m"), 1);
    }

    @Test
    void lessThanReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.lessThan(10, "c", "m"), 5);
    }

    @Test
    void lessOrEqualReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.lessOrEqual(10, "c", "m"), 10);
    }

    @Test
    void nonZeroReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonZero(0, "c", "m"), 1);
    }

    // --- String ---

    @Test
    void nonEmptyStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonEmptyString(), "a");
    }

    @Test
    void nonBlankStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nonBlankString(), "a");
    }

    @Test
    void trimmedStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.trimmedString(), "abc");
    }

    @Test
    void emailStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.emailString(), "a@b.com");
    }

    @Test
    void asciiStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.asciiString(), "abc");
    }

    @Test
    void alphabeticStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.alphabeticString(), "abc");
    }

    @Test
    void numericStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.numericString(), "123");
    }

    @Test
    void alphanumericStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.alphanumericString(), "abc123");
    }

    @Test
    void slugStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.slugString(), "hello-world");
    }

    @Test
    void lowerCaseStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.lowerCaseString(), "abc");
    }

    @Test
    void upperCaseStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.upperCaseString(), "ABC");
    }

    @Test
    void uuidStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.uuidString(), "550e8400-e29b-41d4-a716-446655440000");
    }

    @Test
    void uriStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.uriString(), "https://example.com");
    }

    @Test
    void regexStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.regexString(), "[a-z]+");
    }

    @Test
    void urlStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.urlString(), "https://example.com");
    }

    @Test
    void ipv4StringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.ipv4String(), "127.0.0.1");
    }

    @Test
    void hexStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.hexString(), "deadbeef");
    }

    @Test
    void hexColorStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.hexColorString(), "#FF00FF");
    }

    @Test
    void base64StringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.base64String(), "aGVsbG8=");
    }

    @Test
    void base64UrlStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.base64UrlString(), "aGVsbG8");
    }

    @Test
    void ulidStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.ulidString(), "01ARZ3NDEKTSV4RRFFQ69G5FAV");
    }

    @Test
    void jsonStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.jsonString(), "{}");
    }

    @Test
    void cidrV4StringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.cidrV4String(), "192.168.0.0/24");
    }

    @Test
    void cidrV6StringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.cidrV6String(), "2001:db8::/32");
    }

    @Test
    void macAddressStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.macAddressString(), "00:11:22:33:44:55");
    }

    @Test
    void semVerStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.semVerString(), "1.0.0");
    }

    @Test
    void creditCardStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.creditCardString(), "4111111111111111");
    }

    @Test
    void isbnStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.isbnString(), "9780306406157");
    }

    @Test
    void hostnameStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.hostnameString(), "example.com");
    }

    @Test
    void jwtStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.jwtString(), "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIn0.abc-123_456");
    }

    @Test
    void iso8601DateStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.iso8601DateString(), "2026-01-01");
    }

    @Test
    void iso8601TimeStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.iso8601TimeString(), "12:00:00");
    }

    @Test
    void iso8601DateTimeStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.iso8601DateTimeString(), "2026-01-01T12:00:00");
    }

    @Test
    void iso8601DurationStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.iso8601DurationString(), "PT1H");
    }

    @Test
    void iso8601PeriodStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.iso8601PeriodString(), "P1Y");
    }

    @Test
    void timeZoneIdStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.timeZoneIdString(), "UTC");
    }

    @Test
    void validByteStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.validByteString(), "1");
    }

    @Test
    void validShortStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.validShortString(), "1");
    }

    @Test
    void validIntStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.validIntString(), "1");
    }

    @Test
    void validLongStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.validLongString(), "1");
    }

    @Test
    void validFloatStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.validFloatString(), "1.0");
    }

    @Test
    void validDoubleStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.validDoubleString(), "1.0");
    }

    @Test
    void validBigIntegerStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.validBigIntegerString(), "1");
    }

    @Test
    void validBigDecimalStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.validBigDecimalString(), "1.0");
    }

    @Test
    void xpathStringReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.xpathString(), "/root");
    }

    // --- Character ---

    @Test
    void letterCharReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.letterChar(), 'a');
    }

    @Test
    void letterOrDigitCharReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.letterOrDigitChar(), 'a');
    }

    @Test
    void lowerCaseCharReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.lowerCaseChar(), 'a');
    }

    @Test
    void upperCaseCharReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.upperCaseChar(), 'A');
    }

    @Test
    void whitespaceCharReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.whitespaceChar(), ' ');
    }

    // --- Boolean ---

    @Test
    void falseValueReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.falseValue(), false);
    }

    @Test
    void andBooleanListReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.andBooleanList(), Arrays.asList(true, true));
    }

    @Test
    void orBooleanListReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.orBooleanList(), Arrays.asList(false, true));
    }

    @Test
    void xorBooleanListReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.xorBooleanList(), Arrays.asList(true, false));
    }

    @Test
    void nandBooleanListReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.nandBooleanList(), Arrays.asList(true, false));
    }

    @Test
    void norBooleanListReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.norBooleanList(), Arrays.asList(false, false));
    }

    @Test
    void oneOfBooleanListReturnsNonNullConstraint() {
        assertConstraint(RefinedSupport.oneOfBooleanList(), Arrays.asList(true, false));
    }

    private <T> void assertConstraint(Constraint<T> constraint, T validInput) {
        assertNotNull(constraint, "factory method must not return null");
        assertTrue(constraint.validate(validInput).isValid(),
            "constraint must accept valid input: " + validInput);
    }
}
