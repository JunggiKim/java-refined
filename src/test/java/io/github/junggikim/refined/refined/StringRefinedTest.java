package io.github.junggikim.refined.refined;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.refined.string.NonBlankString;
import io.github.junggikim.refined.refined.string.NonEmptyString;
import io.github.junggikim.refined.refined.string.TrimmedString;
import io.github.junggikim.refined.refined.string.UriString;
import io.github.junggikim.refined.refined.string.UuidString;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class StringRefinedTest {

    @ParameterizedTest
    @NullSource
    @EmptySource
    void nonEmptyStringRejectsNullAndEmpty(String value) {
        assertEquals("non-empty-string", NonEmptyString.of(value).getError().code());
    }

    @Test
    void nonEmptyStringAcceptsWhitespaceOnlyString() {
        assertEquals("   ", NonEmptyString.unsafeOf("   ").value());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "\n", "\t", "\u00A0", "\u2007", "\u202F"})
    void nonBlankStringRejectsBlankValues(String value) {
        assertEquals("non-blank-string", NonBlankString.of(value).getError().code());
    }

    @Test
    void trimmedStringAcceptsOnlyAlreadyTrimmedInput() {
        assertEquals("abc", TrimmedString.unsafeOf("abc").value());
        assertEquals("trimmed-string", TrimmedString.of(null).getError().code());
        assertEquals("trimmed-string", TrimmedString.of(" abc ").getError().code());
        assertEquals("trimmed-string", TrimmedString.of("\u00A0abc\u00A0").getError().code());
        assertThrows(RefinementException.class, () -> TrimmedString.unsafeOf(" abc "));
    }

    @Test
    void uuidAndUriStringsValidateFormat() {
        String uuid = UUID.randomUUID().toString();

        assertEquals(uuid, UuidString.unsafeOf(uuid).value());
        assertEquals("uuid-string", UuidString.of(null).getError().code());
        assertEquals("uuid-string", UuidString.of("not-a-uuid").getError().code());
        assertEquals("https://example.com", UriString.unsafeOf("https://example.com").value());
        assertEquals("uri-string", UriString.of("://bad").getError().code());
    }

    @TestFactory
    Stream<DynamicTest> stringTypeToStringAndEquality() {
        return Stream.of(
            DynamicTest.dynamicTest("NonBlankString equality", () -> assertEquals(NonBlankString.unsafeOf("a"), NonBlankString.unsafeOf("a"))),
            DynamicTest.dynamicTest("NonBlankString toString contains type", () -> assertTrue(NonBlankString.unsafeOf("a").toString().contains("NonBlankString"))),
            DynamicTest.dynamicTest("UriString null invalid", () -> assertEquals("uri-string", UriString.of(null).getError().code())),
            DynamicTest.dynamicTest("NonEmptyString of accepts", () -> assertEquals("abc", NonEmptyString.of("abc").get().value())),
            DynamicTest.dynamicTest("NonBlankString of accepts", () -> assertEquals("abc", NonBlankString.of("abc").get().value())),
            DynamicTest.dynamicTest("TrimmedString of accepts", () -> assertEquals("abc", TrimmedString.of("abc").get().value())),
            DynamicTest.dynamicTest("UuidString of accepts", () -> assertEquals(uuidLike(), UuidString.of(uuidLike()).get().value())),
            DynamicTest.dynamicTest("UriString of accepts", () -> assertEquals("https://example.com", UriString.of("https://example.com").get().value()))
        );
    }

    private static String uuidLike() {
        return "123e4567-e89b-12d3-a456-426614174000";
    }
}
