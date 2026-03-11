package io.github.junggikim.refined.refined;

import static io.github.junggikim.refined.support.TestCollections.listOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.junggikim.refined.core.Refined;
import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.refined.numeric.NaturalBigInteger;
import io.github.junggikim.refined.refined.numeric.NaturalInt;
import io.github.junggikim.refined.refined.numeric.NaturalLong;
import io.github.junggikim.refined.refined.numeric.NegativeBigDecimal;
import io.github.junggikim.refined.refined.numeric.NegativeBigInteger;
import io.github.junggikim.refined.refined.numeric.NegativeInt;
import io.github.junggikim.refined.refined.numeric.NegativeLong;
import io.github.junggikim.refined.refined.numeric.NonNegativeBigDecimal;
import io.github.junggikim.refined.refined.numeric.NonNegativeBigInteger;
import io.github.junggikim.refined.refined.numeric.NonNegativeLong;
import io.github.junggikim.refined.refined.numeric.NonPositiveBigDecimal;
import io.github.junggikim.refined.refined.numeric.NonPositiveBigInteger;
import io.github.junggikim.refined.refined.numeric.NonPositiveInt;
import io.github.junggikim.refined.refined.numeric.NonPositiveLong;
import io.github.junggikim.refined.refined.numeric.NonZeroBigDecimal;
import io.github.junggikim.refined.refined.numeric.NonZeroBigInteger;
import io.github.junggikim.refined.refined.numeric.NonZeroLong;
import io.github.junggikim.refined.refined.numeric.PositiveBigDecimal;
import io.github.junggikim.refined.refined.numeric.PositiveBigInteger;
import io.github.junggikim.refined.refined.numeric.PositiveLong;
import io.github.junggikim.refined.support.RefinedCase;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

class ExtendedNumericRefinedTest {

    private static final List<RefinedCase<Integer>> INT_CASES = listOf(
        new RefinedCase<Integer>("NegativeInt", "negative-int", NegativeInt::of, NegativeInt::unsafeOf, -1, 0),
        new RefinedCase<Integer>("NonPositiveInt", "non-positive-int", NonPositiveInt::of, NonPositiveInt::unsafeOf, 0, 1),
        new RefinedCase<Integer>("NaturalInt", "natural-int", NaturalInt::of, NaturalInt::unsafeOf, 0, -1)
    );

    private static final List<RefinedCase<Long>> LONG_CASES = listOf(
        new RefinedCase<Long>("PositiveLong", "positive-long", PositiveLong::of, PositiveLong::unsafeOf, 1L, 0L),
        new RefinedCase<Long>("NegativeLong", "negative-long", NegativeLong::of, NegativeLong::unsafeOf, -1L, 0L),
        new RefinedCase<Long>("NonNegativeLong", "non-negative-long", NonNegativeLong::of, NonNegativeLong::unsafeOf, 0L, -1L),
        new RefinedCase<Long>("NonPositiveLong", "non-positive-long", NonPositiveLong::of, NonPositiveLong::unsafeOf, 0L, 1L),
        new RefinedCase<Long>("NonZeroLong", "non-zero-long", NonZeroLong::of, NonZeroLong::unsafeOf, -1L, 0L),
        new RefinedCase<Long>("NaturalLong", "natural-long", NaturalLong::of, NaturalLong::unsafeOf, 0L, -1L)
    );

    private static final List<RefinedCase<BigInteger>> BIG_INTEGER_CASES = listOf(
        new RefinedCase<BigInteger>("PositiveBigInteger", "positive-big-integer", PositiveBigInteger::of, PositiveBigInteger::unsafeOf, BigInteger.ONE, BigInteger.ZERO),
        new RefinedCase<BigInteger>("NegativeBigInteger", "negative-big-integer", NegativeBigInteger::of, NegativeBigInteger::unsafeOf, BigInteger.ONE.negate(), BigInteger.ZERO),
        new RefinedCase<BigInteger>("NonNegativeBigInteger", "non-negative-big-integer", NonNegativeBigInteger::of, NonNegativeBigInteger::unsafeOf, BigInteger.ZERO, BigInteger.ONE.negate()),
        new RefinedCase<BigInteger>("NonPositiveBigInteger", "non-positive-big-integer", NonPositiveBigInteger::of, NonPositiveBigInteger::unsafeOf, BigInteger.ZERO, BigInteger.ONE),
        new RefinedCase<BigInteger>("NonZeroBigInteger", "non-zero-big-integer", NonZeroBigInteger::of, NonZeroBigInteger::unsafeOf, BigInteger.ONE, BigInteger.ZERO),
        new RefinedCase<BigInteger>("NaturalBigInteger", "natural-big-integer", NaturalBigInteger::of, NaturalBigInteger::unsafeOf, BigInteger.ZERO, BigInteger.ONE.negate())
    );

    private static final List<RefinedCase<BigDecimal>> BIG_DECIMAL_CASES = listOf(
        new RefinedCase<BigDecimal>("PositiveBigDecimal", "positive-big-decimal", PositiveBigDecimal::of, PositiveBigDecimal::unsafeOf, BigDecimal.ONE, BigDecimal.ZERO),
        new RefinedCase<BigDecimal>("NegativeBigDecimal", "negative-big-decimal", NegativeBigDecimal::of, NegativeBigDecimal::unsafeOf, BigDecimal.ONE.negate(), BigDecimal.ZERO),
        new RefinedCase<BigDecimal>("NonNegativeBigDecimal", "non-negative-big-decimal", NonNegativeBigDecimal::of, NonNegativeBigDecimal::unsafeOf, BigDecimal.ZERO, BigDecimal.ONE.negate()),
        new RefinedCase<BigDecimal>("NonPositiveBigDecimal", "non-positive-big-decimal", NonPositiveBigDecimal::of, NonPositiveBigDecimal::unsafeOf, BigDecimal.ZERO, BigDecimal.ONE),
        new RefinedCase<BigDecimal>("NonZeroBigDecimal", "non-zero-big-decimal", NonZeroBigDecimal::of, NonZeroBigDecimal::unsafeOf, BigDecimal.ONE, BigDecimal.ZERO)
    );

    @TestFactory
    Stream<DynamicTest> intTypesSupportFactoriesAndValidation() {
        return INT_CASES.stream().flatMap(this::testsForCase);
    }

    @TestFactory
    Stream<DynamicTest> longTypesSupportFactoriesAndValidation() {
        return LONG_CASES.stream().flatMap(this::testsForCase);
    }

    @TestFactory
    Stream<DynamicTest> bigIntegerTypesSupportFactoriesAndValidation() {
        return BIG_INTEGER_CASES.stream().flatMap(this::testsForCase);
    }

    @TestFactory
    Stream<DynamicTest> bigDecimalTypesSupportFactoriesAndValidation() {
        return BIG_DECIMAL_CASES.stream().flatMap(this::testsForCase);
    }

    private <T> Stream<DynamicTest> testsForCase(RefinedCase<T> testCase) {
        return Stream.of(
            DynamicTest.dynamicTest(testCase.typeName() + " accepts valid input", () -> assertEquals(testCase.valid(), testCase.of().apply(testCase.valid()).get().value())),
            DynamicTest.dynamicTest(testCase.typeName() + " rejects invalid input", () -> assertEquals(testCase.code(), testCase.of().apply(testCase.invalid()).getError().code())),
            DynamicTest.dynamicTest(testCase.typeName() + " rejects null", () -> assertEquals(testCase.code(), testCase.of().apply(null).getError().code())),
            DynamicTest.dynamicTest(testCase.typeName() + " unsafeOf throws on invalid input", () -> assertThrows(RefinementException.class, () -> testCase.unsafeOf().apply(testCase.invalid()))),
            DynamicTest.dynamicTest(testCase.typeName() + " equality and string are stable", () -> {
                Refined<T> left = testCase.unsafeOf().apply(testCase.valid());
                Refined<T> right = testCase.unsafeOf().apply(testCase.valid());

                assertEquals(left, right);
                assertEquals(testCase.typeName(), left.typeName());
                assertTrue(left.toString().contains(testCase.typeName()));
            })
        );
    }

    @org.junit.jupiter.api.Test
    void numericFamiliesCoverExtremeValuesAndScientificNotation() {
        assertEquals(Long.MAX_VALUE, PositiveLong.of(Long.MAX_VALUE).get().value());
        assertEquals("negative-long", NegativeLong.of(Long.MAX_VALUE).getError().code());
        assertEquals(new BigDecimal("1E+3"), PositiveBigDecimal.of(new BigDecimal("1E+3")).get().value());
        assertEquals(new BigDecimal("-1E-3"), NegativeBigDecimal.of(new BigDecimal("-1E-3")).get().value());
        assertEquals("non-zero-big-decimal", NonZeroBigDecimal.of(new BigDecimal("0E-8")).getError().code());
    }
}
