package io.github.junggikim.refined.validation;

import static io.github.junggikim.refined.support.TestCollections.mapOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.junggikim.refined.violation.Violation;
import org.junit.jupiter.api.Test;

class ViolationTest {

    @Test
    void createsImmutableMetadataEvenWhenNullIsPassed() {
        Violation violation = new Violation("code", "message", null);

        assertEquals(mapOf(), violation.metadata());
        assertThrows(UnsupportedOperationException.class, () -> violation.metadata().put("x", 1));
    }

    @Test
    void rejectsNullCodeAndMessage() {
        assertThrows(NullPointerException.class, () -> new Violation(null, "message", mapOf()));
        assertThrows(NullPointerException.class, () -> new Violation("code", null, mapOf()));
    }

    @Test
    void withMetadataReturnsNewImmutableViolation() {
        Violation original = Violation.of("code", "message");
        Violation updated = original.withMetadata("k", "v");

        assertEquals(mapOf(), original.metadata());
        assertEquals(mapOf("k", "v"), updated.metadata());
    }

    @Test
    void implementsEqualityHashCodeToStringAndMetadataNullGuards() {
        Violation violation = new Violation("code", "message", mapOf("k", "v"));

        assertTrue(violation.equals(violation));
        assertEquals(violation, new Violation("code", "message", mapOf("k", "v")));
        assertEquals(violation.hashCode(), new Violation("code", "message", mapOf("k", "v")).hashCode());
        assertNotEquals(violation, new Violation("other", "message", mapOf("k", "v")));
        assertNotEquals(violation, new Violation("code", "other", mapOf("k", "v")));
        assertNotEquals(violation, new Violation("code", "message", mapOf("k", "other")));
        assertNotEquals(violation, "violation");
        assertEquals("Violation[code=code, message=message, metadata={k=v}]", violation.toString());
        assertThrows(NullPointerException.class, () -> new Violation("code", "message", mapOf(null, "v")));
        assertThrows(NullPointerException.class, () -> new Violation("code", "message", mapOf("k", null)));
        assertThrows(NullPointerException.class, () -> assertEquals("code", violation.withMetadata(null, "v").code()));
        assertThrows(NullPointerException.class, () -> assertEquals("code", violation.withMetadata("k2", null).code()));
    }
}
