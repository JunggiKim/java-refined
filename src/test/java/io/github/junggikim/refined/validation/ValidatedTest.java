package io.github.junggikim.refined.validation;

import static io.github.junggikim.refined.support.TestCollections.listOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ValidatedTest {

    @Test
    void validSupportsMapAndZip() {
        Validated<String, Integer> valid = Validated.valid(3);

        assertEquals(6, valid.map(it -> it * 2).get());
        assertEquals(7, valid.zip(Validated.valid(4), Integer::sum).get());
        assertThrows(IllegalStateException.class, valid::getErrors);
    }

    @Test
    void invalidPreservesErrorsAndRequiresNonEmptyErrors() {
        assertThrows(IllegalArgumentException.class, () -> Validated.invalid(listOf()));

        Validated<String, Integer> invalid = Validated.invalid(listOf("e1"));

        assertEquals(listOf("e1"), invalid.map(it -> it * 2).getErrors());
        assertThrows(IllegalStateException.class, invalid::get);
    }

    @Test
    void zipAccumulatesErrorsAndKeepsSingleSideErrors() {
        Validated<String, Integer> leftInvalid = Validated.invalid(listOf("left"));
        Validated<String, Integer> rightInvalid = Validated.invalid(listOf("right"));

        assertEquals(listOf("left", "right"), leftInvalid.zip(rightInvalid, Integer::sum).getErrors());
        assertEquals(listOf("left"), leftInvalid.zip(Validated.valid(1), Integer::sum).getErrors());
        assertEquals(listOf("right"), Validated.<String, Integer>valid(1).zip(rightInvalid, Integer::sum).getErrors());
    }

    @Test
    void validImplementsEqualityHashCodeToStringAndNullGuards() {
        Validated<String, Integer> valid = Validated.valid(3);

        assertEquals(valid, valid);
        assertEquals(valid, Validated.valid(3));
        assertEquals(valid.hashCode(), Validated.valid(3).hashCode());
        assertNotEquals(valid, Validated.valid(4));
        assertNotEquals(valid, Validated.invalid(listOf("e1")));
        assertNotEquals(valid, "valid");
        assertEquals("Valid[value=3]", valid.toString());
        assertThrows(NullPointerException.class, () -> Validated.valid(null));
        assertThrows(NullPointerException.class, () -> valid.map(value -> null));
    }

    @Test
    void invalidImplementsEqualityHashCodeToStringAndNullGuards() {
        Validated<String, Integer> invalid = Validated.invalid(listOf("e1"));

        assertEquals(invalid, invalid);
        assertEquals(invalid, Validated.invalid(listOf("e1")));
        assertEquals(invalid.hashCode(), Validated.invalid(listOf("e1")).hashCode());
        assertNotEquals(invalid, Validated.invalid(listOf("e2")));
        assertNotEquals(invalid, Validated.valid(1));
        assertNotEquals(invalid, "invalid");
        assertEquals("Invalid[errors=[e1]]", invalid.toString());
        assertThrows(NullPointerException.class, () -> Validated.invalid(null));
        assertThrows(NullPointerException.class, () -> Validated.invalid(listOf("e1", null)));
    }
}
