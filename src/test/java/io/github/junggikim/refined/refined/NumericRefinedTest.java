package io.github.junggikim.refined.refined;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.refined.numeric.NonNegativeInt;
import io.github.junggikim.refined.refined.numeric.NonZeroInt;
import io.github.junggikim.refined.refined.numeric.PositiveInt;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class NumericRefinedTest {

    @ParameterizedTest
    @CsvSource({
        "1,true",
        "10,true",
        "0,false",
        "-1,false"
    })
    void positiveIntMatrix(int value, boolean valid) {
        Validation<Violation, PositiveInt> result = PositiveInt.of(value);

        assertEquals(valid, result.isValid());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 42})
    void nonNegativeIntAcceptsZeroAndPositive(int value) {
        assertEquals(value, NonNegativeInt.unsafeOf(value).value());
    }

    @Test
    void nonNegativeIntRejectsNegativeValues() {
        assertEquals("non-negative-int", NonNegativeInt.of(-1).getError().code());
        assertThrows(RefinementException.class, () -> NonNegativeInt.unsafeOf(-1));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 1, 42})
    void nonZeroIntRejectsOnlyZero(int value) {
        assertEquals(value, NonZeroInt.unsafeOf(value).value());
    }

    @Test
    void nonZeroIntRejectsZero() {
        assertEquals("non-zero-int", NonZeroInt.of(0).getError().code());
        assertThrows(RefinementException.class, () -> NonZeroInt.unsafeOf(0));
    }

    @Test
    void positiveIntSupportsTypeNameEqualityHashCodeAndToString() {
        PositiveInt one = PositiveInt.unsafeOf(1);
        PositiveInt anotherOne = PositiveInt.unsafeOf(1);

        assertEquals("PositiveInt", one.typeName());
        assertTrue(one.equals(one));
        assertEquals(one, anotherOne);
        assertEquals(one.hashCode(), anotherOne.hashCode());
        assertTrue(one.toString().contains("PositiveInt"));
        assertTrue(one.toString().contains("1"));
    }

    @Test
    void positiveIntRejectsInvalidValueAndExposesViolationThroughException() {
        Validation<Violation, PositiveInt> invalid = PositiveInt.of(0);
        RefinementException exception = assertThrows(RefinementException.class, () -> PositiveInt.unsafeOf(0));

        assertEquals("positive-int", invalid.getError().code());
        assertEquals("positive-int", exception.violation().code());
    }

    @Test
    void supportUtilityCoversNullAndRefineHelpers() {
        Constraint<String> constraint = RefinedSupport.nonNull("required", "required");

        assertEquals("required", constraint.validate(null).getError().code());
        assertEquals("ok", RefinedSupport.refine("ok", constraint, value -> value).get());
        assertThrows(RefinementException.class, () -> RefinedSupport.unsafeRefine(null, constraint, value -> value));
    }

    @Test
    void constraintDescriptionDefaultsToSimpleClassName() {
        class NamedConstraint implements Constraint<Integer> {
            @Override
            public Validation<Violation, Integer> validate(Integer value) {
                return Validation.valid(value);
            }
        }

        Constraint<Integer> constraint = new NamedConstraint();

        assertEquals("NamedConstraint", constraint.description());
    }

    @RepeatedTest(3)
    void nonZeroIntUnsafeOfIsDeterministic() {
        assertEquals(7, NonZeroInt.unsafeOf(7).value());
    }

    @TestFactory
    Stream<DynamicTest> invalidInputMatrixForNumericTypes() {
        return Stream.of(
            DynamicTest.dynamicTest("PositiveInt rejects null", () -> assertEquals("positive-int", PositiveInt.of(null).getError().code())),
            DynamicTest.dynamicTest("NonNegativeInt rejects null", () -> assertEquals("non-negative-int", NonNegativeInt.of(null).getError().code())),
            DynamicTest.dynamicTest("NonZeroInt rejects null", () -> assertEquals("non-zero-int", NonZeroInt.of(null).getError().code())),
            DynamicTest.dynamicTest("PositiveInt of accepts", () -> assertEquals(2, PositiveInt.of(2).get().value())),
            DynamicTest.dynamicTest("NonNegativeInt of accepts", () -> assertEquals(0, NonNegativeInt.of(0).get().value())),
            DynamicTest.dynamicTest("NonZeroInt of accepts", () -> assertEquals(-2, NonZeroInt.of(-2).get().value()))
        );
    }

    @Test
    void abstractRefinedRejectsDifferentClassAndNullOnEquals() {
        PositiveInt positive = PositiveInt.unsafeOf(1);

        assertTrue(!positive.equals(null));
        assertTrue(!positive.equals(NonNegativeInt.unsafeOf(1)));
        assertInstanceOf(Integer.class, positive.value());
    }
}
