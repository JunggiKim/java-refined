package io.github.junggikim.refined.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.junggikim.refined.refined.numeric.PositiveInt;
import io.github.junggikim.refined.violation.Violation;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class KotlinInteropBridgeTest {

    @Test
    void refinedGetValueBridgesValueMethod() {
        PositiveInt value = PositiveInt.unsafeOf(1);

        assertEquals(Integer.valueOf(1), value.value());
        assertEquals(Integer.valueOf(1), value.getValue());
    }

    @Test
    void violationBeanGettersMirrorRecordStyleAccessors() {
        Violation violation = Violation.of("code", "message").withMetadata("cause", "invalid");

        assertEquals("code", violation.code());
        assertEquals("code", violation.getCode());
        assertEquals("message", violation.message());
        assertEquals("message", violation.getMessage());
        assertEquals(Collections.singletonMap("cause", "invalid"), violation.getMetadata());
        assertEquals(violation.metadata(), violation.getMetadata());
        assertTrue(violation.toString().contains("code"));
    }
}
