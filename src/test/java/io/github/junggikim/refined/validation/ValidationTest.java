package io.github.junggikim.refined.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.junggikim.refined.violation.Violation;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
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

    @Test
    void validationHashCodesAreNonZero() {
        assertNotEquals(0, Validation.valid(3).hashCode());
        assertNotEquals(0, Validation.invalid(Violation.of("code", "msg")).hashCode());
    }

    @Test
    void getOrElseWithValueReturnsValueWhenValid() {
        assertEquals(10, Validation.<Violation, Integer>valid(10).getOrElse(99));
    }

    @Test
    void getOrElseWithValueReturnsFallbackWhenInvalid() {
        Validation<Violation, Integer> invalid = Validation.invalid(Violation.of("e", "e"));

        assertEquals(99, invalid.getOrElse(99));
    }

    @Test
    void getOrElseWithValueRejectsNullOther() {
        Validation<Violation, Integer> valid = Validation.valid(10);

        assertThrows(NullPointerException.class, () -> valid.getOrElse((Integer) null));
    }

    @Test
    void getOrElseWithHandlerReturnsValueWhenValid() {
        assertEquals(10, Validation.<Violation, Integer>valid(10).getOrElse(err -> 99));
    }

    @Test
    void getOrElseWithHandlerReturnsHandlerResultWhenInvalid() {
        Validation<Violation, String> invalid = Validation.invalid(Violation.of("e", "msg"));

        assertEquals("msg", invalid.getOrElse(err -> err.message()));
    }

    @Test
    void getOrElseWithHandlerRejectsNullHandler() {
        Validation<Violation, Integer> valid = Validation.valid(10);

        assertThrows(NullPointerException.class,
            () -> valid.getOrElse((java.util.function.Function<Violation, Integer>) null));
    }

    @Test
    void getOrElseWithHandlerRejectsNullHandlerResult() {
        Validation<Violation, Integer> invalid = Validation.invalid(Violation.of("e", "e"));

        assertThrows(NullPointerException.class, () -> invalid.getOrElse(err -> null));
    }

    @Test
    void getOrNullReturnsValueWhenValid() {
        assertEquals(10, Validation.<Violation, Integer>valid(10).getOrNull());
    }

    @Test
    void getOrNullReturnsNullWhenInvalid() {
        Validation<Violation, Integer> invalid = Validation.invalid(Violation.of("e", "e"));

        assertNull(invalid.getOrNull());
    }

    @Test
    void toOptionalReturnsPresentWhenValid() {
        Optional<Integer> result = Validation.<Violation, Integer>valid(10).toOptional();

        assertTrue(result.isPresent());
        assertEquals(10, result.get());
    }

    @Test
    void toOptionalReturnsEmptyWhenInvalid() {
        Optional<Integer> result = Validation.<Violation, Integer>invalid(Violation.of("e", "e")).toOptional();

        assertFalse(result.isPresent());
    }

    @Test
    void onValidExecutesActionWhenValidAndReturnsSameInstance() {
        Validation<Violation, Integer> valid = Validation.valid(10);
        AtomicReference<Integer> captured = new AtomicReference<>();

        Validation<Violation, Integer> returned = valid.onValid(captured::set);

        assertSame(valid, returned);
        assertEquals(10, captured.get());
    }

    @Test
    void onValidSkipsActionWhenInvalidAndReturnsSameInstance() {
        Validation<Violation, Integer> invalid = Validation.invalid(Violation.of("e", "e"));
        AtomicReference<Integer> captured = new AtomicReference<>();

        Validation<Violation, Integer> returned = invalid.onValid(captured::set);

        assertSame(invalid, returned);
        assertNull(captured.get());
    }

    @Test
    void onValidRejectsNullAction() {
        assertThrows(NullPointerException.class,
            () -> Validation.<Violation, Integer>valid(10).onValid(null));
    }

    @Test
    void onInvalidExecutesActionWhenInvalidAndReturnsSameInstance() {
        Violation error = Violation.of("e", "e");
        Validation<Violation, Integer> invalid = Validation.invalid(error);
        AtomicReference<Violation> captured = new AtomicReference<>();

        Validation<Violation, Integer> returned = invalid.onInvalid(captured::set);

        assertSame(invalid, returned);
        assertEquals(error, captured.get());
    }

    @Test
    void onInvalidSkipsActionWhenValidAndReturnsSameInstance() {
        Validation<Violation, Integer> valid = Validation.valid(10);
        AtomicReference<Violation> captured = new AtomicReference<>();

        Validation<Violation, Integer> returned = valid.onInvalid(captured::set);

        assertSame(valid, returned);
        assertNull(captured.get());
    }

    @Test
    void onInvalidRejectsNullAction() {
        assertThrows(NullPointerException.class,
            () -> Validation.<Violation, Integer>invalid(Violation.of("e", "e")).onInvalid(null));
    }

    @Test
    void mapErrorPassesThroughWhenValid() {
        Validation<String, Integer> result = Validation.<Violation, Integer>valid(10)
            .mapError(Violation::code);

        assertTrue(result.isValid());
        assertEquals(10, result.get());
    }

    @Test
    void mapErrorTransformsErrorWhenInvalid() {
        Validation<String, Integer> result = Validation.<Violation, Integer>invalid(Violation.of("e", "msg"))
            .mapError(Violation::code);

        assertTrue(result.isInvalid());
        assertEquals("e", result.getError());
    }

    @Test
    void mapErrorRejectsNullFunction() {
        assertThrows(NullPointerException.class,
            () -> Validation.<Violation, Integer>valid(10).mapError(null));
    }

    @Test
    void recoverReturnsSelfWhenValid() {
        Validation<Violation, Integer> valid = Validation.valid(10);
        Validation<Violation, Integer> result = valid.recover(err -> 99);

        assertSame(valid, result);
        assertEquals(10, result.get());
    }

    @Test
    void recoverConvertsErrorToSuccessWhenInvalid() {
        Validation<Violation, Integer> result = Validation.<Violation, Integer>invalid(Violation.of("e", "e"))
            .recover(err -> 99);

        assertTrue(result.isValid());
        assertEquals(99, result.get());
    }

    @Test
    void recoverRejectsNullFunction() {
        assertThrows(NullPointerException.class,
            () -> Validation.<Violation, Integer>valid(10).recover(null));
    }
}
