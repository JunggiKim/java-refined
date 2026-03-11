package io.github.junggikim.refined.refined;

import static io.github.junggikim.refined.support.TestCollections.listOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.junggikim.refined.core.Refined;
import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.refined.character.DigitChar;
import io.github.junggikim.refined.refined.character.LetterChar;
import io.github.junggikim.refined.refined.character.LetterOrDigitChar;
import io.github.junggikim.refined.refined.character.LowerCaseChar;
import io.github.junggikim.refined.refined.character.UpperCaseChar;
import io.github.junggikim.refined.refined.character.WhitespaceChar;
import io.github.junggikim.refined.refined.numeric.FiniteDouble;
import io.github.junggikim.refined.refined.numeric.FiniteFloat;
import io.github.junggikim.refined.refined.numeric.NaturalByte;
import io.github.junggikim.refined.refined.numeric.NaturalShort;
import io.github.junggikim.refined.refined.numeric.NegativeByte;
import io.github.junggikim.refined.refined.numeric.NegativeDouble;
import io.github.junggikim.refined.refined.numeric.NegativeFloat;
import io.github.junggikim.refined.refined.numeric.NegativeShort;
import io.github.junggikim.refined.refined.numeric.NonNaNDouble;
import io.github.junggikim.refined.refined.numeric.NonNaNFloat;
import io.github.junggikim.refined.refined.numeric.NonNegativeByte;
import io.github.junggikim.refined.refined.numeric.NonNegativeDouble;
import io.github.junggikim.refined.refined.numeric.NonNegativeFloat;
import io.github.junggikim.refined.refined.numeric.NonNegativeShort;
import io.github.junggikim.refined.refined.numeric.NonPositiveByte;
import io.github.junggikim.refined.refined.numeric.NonPositiveDouble;
import io.github.junggikim.refined.refined.numeric.NonPositiveFloat;
import io.github.junggikim.refined.refined.numeric.NonPositiveShort;
import io.github.junggikim.refined.refined.numeric.NonZeroByte;
import io.github.junggikim.refined.refined.numeric.NonZeroDouble;
import io.github.junggikim.refined.refined.numeric.NonZeroFloat;
import io.github.junggikim.refined.refined.numeric.NonZeroShort;
import io.github.junggikim.refined.refined.numeric.PositiveByte;
import io.github.junggikim.refined.refined.numeric.PositiveDouble;
import io.github.junggikim.refined.refined.numeric.PositiveFloat;
import io.github.junggikim.refined.refined.numeric.PositiveShort;
import io.github.junggikim.refined.support.RefinedCase;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class AdditionalNumericAndCharacterRefinedTest {

    private static final List<RefinedCase<Character>> CHARACTER_CASES = listOf(
        new RefinedCase<Character>("DigitChar", "digit-char", DigitChar::of, DigitChar::unsafeOf, '5', 'a'),
        new RefinedCase<Character>("LetterChar", "letter-char", LetterChar::of, LetterChar::unsafeOf, 'a', '1'),
        new RefinedCase<Character>("LetterOrDigitChar", "letter-or-digit-char", LetterOrDigitChar::of, LetterOrDigitChar::unsafeOf, '1', '-'),
        new RefinedCase<Character>("LowerCaseChar", "lower-case-char", LowerCaseChar::of, LowerCaseChar::unsafeOf, 'a', 'A'),
        new RefinedCase<Character>("UpperCaseChar", "upper-case-char", UpperCaseChar::of, UpperCaseChar::unsafeOf, 'A', 'a'),
        new RefinedCase<Character>("WhitespaceChar", "whitespace-char", WhitespaceChar::of, WhitespaceChar::unsafeOf, ' ', 'a')
    );

    private static final List<RefinedCase<Byte>> BYTE_CASES = listOf(
        new RefinedCase<Byte>("PositiveByte", "positive-byte", PositiveByte::of, PositiveByte::unsafeOf, (byte) 1, (byte) 0),
        new RefinedCase<Byte>("NegativeByte", "negative-byte", NegativeByte::of, NegativeByte::unsafeOf, (byte) -1, (byte) 0),
        new RefinedCase<Byte>("NonNegativeByte", "non-negative-byte", NonNegativeByte::of, NonNegativeByte::unsafeOf, (byte) 0, (byte) -1),
        new RefinedCase<Byte>("NonPositiveByte", "non-positive-byte", NonPositiveByte::of, NonPositiveByte::unsafeOf, (byte) 0, (byte) 1),
        new RefinedCase<Byte>("NonZeroByte", "non-zero-byte", NonZeroByte::of, NonZeroByte::unsafeOf, (byte) 1, (byte) 0),
        new RefinedCase<Byte>("NaturalByte", "natural-byte", NaturalByte::of, NaturalByte::unsafeOf, (byte) 0, (byte) -1)
    );

    private static final List<RefinedCase<Short>> SHORT_CASES = listOf(
        new RefinedCase<Short>("PositiveShort", "positive-short", PositiveShort::of, PositiveShort::unsafeOf, (short) 1, (short) 0),
        new RefinedCase<Short>("NegativeShort", "negative-short", NegativeShort::of, NegativeShort::unsafeOf, (short) -1, (short) 0),
        new RefinedCase<Short>("NonNegativeShort", "non-negative-short", NonNegativeShort::of, NonNegativeShort::unsafeOf, (short) 0, (short) -1),
        new RefinedCase<Short>("NonPositiveShort", "non-positive-short", NonPositiveShort::of, NonPositiveShort::unsafeOf, (short) 0, (short) 1),
        new RefinedCase<Short>("NonZeroShort", "non-zero-short", NonZeroShort::of, NonZeroShort::unsafeOf, (short) 1, (short) 0),
        new RefinedCase<Short>("NaturalShort", "natural-short", NaturalShort::of, NaturalShort::unsafeOf, (short) 0, (short) -1)
    );

    private static final List<RefinedCase<Float>> FLOAT_CASES = listOf(
        new RefinedCase<Float>("PositiveFloat", "positive-float", PositiveFloat::of, PositiveFloat::unsafeOf, 1.5f, 0.0f),
        new RefinedCase<Float>("NegativeFloat", "negative-float", NegativeFloat::of, NegativeFloat::unsafeOf, -1.5f, 0.0f),
        new RefinedCase<Float>("NonNegativeFloat", "non-negative-float", NonNegativeFloat::of, NonNegativeFloat::unsafeOf, 0.0f, -1.0f),
        new RefinedCase<Float>("NonPositiveFloat", "non-positive-float", NonPositiveFloat::of, NonPositiveFloat::unsafeOf, -0.0f, 1.0f),
        new RefinedCase<Float>("NonZeroFloat", "non-zero-float", NonZeroFloat::of, NonZeroFloat::unsafeOf, 1.0f, 0.0f),
        new RefinedCase<Float>("FiniteFloat", "finite-float", FiniteFloat::of, FiniteFloat::unsafeOf, 1.0f, Float.POSITIVE_INFINITY),
        new RefinedCase<Float>("NonNaNFloat", "non-nan-float", NonNaNFloat::of, NonNaNFloat::unsafeOf, Float.POSITIVE_INFINITY, Float.NaN)
    );

    private static final List<RefinedCase<Double>> DOUBLE_CASES = listOf(
        new RefinedCase<Double>("PositiveDouble", "positive-double", PositiveDouble::of, PositiveDouble::unsafeOf, 1.5d, 0.0d),
        new RefinedCase<Double>("NegativeDouble", "negative-double", NegativeDouble::of, NegativeDouble::unsafeOf, -1.5d, 0.0d),
        new RefinedCase<Double>("NonNegativeDouble", "non-negative-double", NonNegativeDouble::of, NonNegativeDouble::unsafeOf, 0.0d, -1.0d),
        new RefinedCase<Double>("NonPositiveDouble", "non-positive-double", NonPositiveDouble::of, NonPositiveDouble::unsafeOf, -0.0d, 1.0d),
        new RefinedCase<Double>("NonZeroDouble", "non-zero-double", NonZeroDouble::of, NonZeroDouble::unsafeOf, 1.0d, 0.0d),
        new RefinedCase<Double>("FiniteDouble", "finite-double", FiniteDouble::of, FiniteDouble::unsafeOf, 1.0d, Double.POSITIVE_INFINITY),
        new RefinedCase<Double>("NonNaNDouble", "non-nan-double", NonNaNDouble::of, NonNaNDouble::unsafeOf, Double.POSITIVE_INFINITY, Double.NaN)
    );

    @TestFactory
    Stream<DynamicTest> characterWrappersSupportFactoriesAndValidation() {
        return CHARACTER_CASES.stream().flatMap(this::testsForCase);
    }

    @TestFactory
    Stream<DynamicTest> byteWrappersSupportFactoriesAndValidation() {
        return BYTE_CASES.stream().flatMap(this::testsForCase);
    }

    @TestFactory
    Stream<DynamicTest> shortWrappersSupportFactoriesAndValidation() {
        return SHORT_CASES.stream().flatMap(this::testsForCase);
    }

    @TestFactory
    Stream<DynamicTest> floatWrappersSupportFactoriesAndValidation() {
        return FLOAT_CASES.stream().flatMap(this::testsForCase);
    }

    @TestFactory
    Stream<DynamicTest> doubleWrappersSupportFactoriesAndValidation() {
        return DOUBLE_CASES.stream().flatMap(this::testsForCase);
    }

    @Test
    void finiteAndSignBasedFloatWrappersRejectNaNAndInfinity() {
        assertEquals("positive-float", PositiveFloat.of(Float.POSITIVE_INFINITY).getError().code());
        assertEquals("negative-float", NegativeFloat.of(Float.NEGATIVE_INFINITY).getError().code());
        assertEquals("non-negative-float", NonNegativeFloat.of(Float.NaN).getError().code());
        assertEquals("non-positive-float", NonPositiveFloat.of(Float.NaN).getError().code());
        assertEquals("non-zero-float", NonZeroFloat.of(Float.NEGATIVE_INFINITY).getError().code());
        assertEquals("finite-float", FiniteFloat.of(Float.NaN).getError().code());
        assertEquals("non-nan-float", NonNaNFloat.of(Float.NaN).getError().code());
        assertEquals(-0.0f, NonNegativeFloat.of(-0.0f).get().value());
        assertEquals(-0.0f, NonPositiveFloat.of(-0.0f).get().value());
        assertEquals("non-zero-float", NonZeroFloat.of(-0.0f).getError().code());
    }

    @Test
    void finiteAndSignBasedDoubleWrappersRejectNaNAndInfinity() {
        assertEquals("positive-double", PositiveDouble.of(Double.POSITIVE_INFINITY).getError().code());
        assertEquals("negative-double", NegativeDouble.of(Double.NEGATIVE_INFINITY).getError().code());
        assertEquals("non-negative-double", NonNegativeDouble.of(Double.NaN).getError().code());
        assertEquals("non-positive-double", NonPositiveDouble.of(Double.NaN).getError().code());
        assertEquals("non-zero-double", NonZeroDouble.of(Double.NEGATIVE_INFINITY).getError().code());
        assertEquals("finite-double", FiniteDouble.of(Double.NaN).getError().code());
        assertEquals("non-nan-double", NonNaNDouble.of(Double.NaN).getError().code());
        assertEquals(-0.0d, NonNegativeDouble.of(-0.0d).get().value());
        assertEquals(-0.0d, NonPositiveDouble.of(-0.0d).get().value());
        assertEquals("non-zero-double", NonZeroDouble.of(-0.0d).getError().code());
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

    @Test
    void primitiveFloatingAndBoundaryWrappersCoverExtremeValues() {
        assertEquals(Byte.MAX_VALUE, PositiveByte.of(Byte.MAX_VALUE).get().value());
        assertEquals("positive-byte", PositiveByte.of(Byte.MIN_VALUE).getError().code());
        assertEquals(Short.MAX_VALUE, PositiveShort.of(Short.MAX_VALUE).get().value());
        assertEquals("positive-short", PositiveShort.of(Short.MIN_VALUE).getError().code());
        assertEquals(Float.MAX_VALUE, PositiveFloat.of(Float.MAX_VALUE).get().value());
        assertEquals(Float.MIN_VALUE, PositiveFloat.of(Float.MIN_VALUE).get().value());
        assertEquals(Double.MAX_VALUE, PositiveDouble.of(Double.MAX_VALUE).get().value());
        assertEquals(Double.MIN_VALUE, PositiveDouble.of(Double.MIN_VALUE).get().value());
    }
}
