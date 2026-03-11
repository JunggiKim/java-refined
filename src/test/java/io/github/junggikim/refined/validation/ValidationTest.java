package io.github.junggikim.refined.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.junggikim.refined.violation.Violation;
import org.junit.jupiter.api.Test;

class ValidationTest {

    @Test
    void validSupportsMapFlatMapFoldAndZip() {
        Validation<Violation, Integer> valid = Validation.valid(10);

        assertTrue(valid.isValid());
        assertFalse(valid.isInvalid());
        assertEquals(20, valid.map(it -> it * 2).get());
        assertEquals(15, valid.flatMap(it -> Validation.valid(it + 5)).get());
        assertEquals("valid-10", valid.fold(err -> "invalid", value -> "valid-" + value));
        assertEquals(15, valid.zip(Validation.valid(5), Integer::sum).get());
        assertEquals(10, valid.get());
        assertThrows(IllegalStateException.class, valid::getError);
    }

    @Test
    void invalidShortCircuitsAndReturnsOwnErrorOnZip() {
        Violation left = Violation.of("left", "left");
        Validation<Violation, Integer> invalid = Validation.invalid(left);

        assertFalse(invalid.isValid());
        assertTrue(invalid.isInvalid());
        assertEquals(left, invalid.map(it -> it * 2).getError());
        assertEquals(left, invalid.flatMap(it -> Validation.valid(it + 1)).getError());
        assertEquals("invalid-left", invalid.fold(err -> "invalid-" + err.code(), value -> "valid"));
        assertEquals(left, invalid.zip(Validation.valid(5), Integer::sum).getError());
        assertThrows(IllegalStateException.class, invalid::get);
    }

    @Test
    void zipReturnsOtherErrorWhenLeftSideIsValidAndRightSideIsInvalid() {
        Violation right = Violation.of("right", "right");
        Validation<Violation, Integer> result = Validation.<Violation, Integer>valid(10)
            .zip(Validation.invalid(right), Integer::sum);

        assertEquals(right, result.getError());
    }

    @Test
    void validImplementsEqualityHashCodeToStringAndNullGuards() {
        Validation<Violation, Integer> valid = Validation.valid(10);

        assertEquals(valid, valid);
        assertEquals(valid, Validation.valid(10));
        assertEquals(valid.hashCode(), Validation.valid(10).hashCode());
        assertNotEquals(valid, Validation.valid(11));
        assertNotEquals(valid, Validation.invalid(Violation.of("code", "message")));
        assertNotEquals(valid, "valid");
        assertEquals("Valid[value=10]", valid.toString());
        assertThrows(NullPointerException.class, () -> Validation.valid(null));
        assertThrows(NullPointerException.class, () -> valid.map(value -> null));
        assertThrows(NullPointerException.class, () -> valid.flatMap(value -> null));
    }

    @Test
    void invalidImplementsEqualityHashCodeToStringAndNullGuards() {
        Violation error = Violation.of("code", "message");
        Validation<Violation, Integer> invalid = Validation.invalid(error);

        assertEquals(invalid, invalid);
        assertEquals(invalid, Validation.invalid(error));
        assertEquals(invalid.hashCode(), Validation.invalid(error).hashCode());
        assertNotEquals(invalid, Validation.invalid(Violation.of("other", "message")));
        assertNotEquals(invalid, Validation.valid(10));
        assertNotEquals(invalid, "invalid");
        assertEquals("Invalid[error=" + error + "]", invalid.toString());
        assertThrows(NullPointerException.class, () -> Validation.invalid(null));
    }
}
