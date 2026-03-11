package io.github.junggikim.refined.refined;

import static io.github.junggikim.refined.support.TestCollections.listOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.junggikim.refined.core.Refined;
import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.refined.string.AlphanumericString;
import io.github.junggikim.refined.refined.string.AlphabeticString;
import io.github.junggikim.refined.refined.string.AsciiString;
import io.github.junggikim.refined.refined.string.EmailString;
import io.github.junggikim.refined.refined.string.LowerCaseString;
import io.github.junggikim.refined.refined.string.NumericString;
import io.github.junggikim.refined.refined.string.SlugString;
import io.github.junggikim.refined.refined.string.UpperCaseString;
import io.github.junggikim.refined.support.RefinedCase;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class ExtendedStringRefinedTest {

    private static final List<RefinedCase<String>> STRING_CASES = listOf(
        new RefinedCase<String>("EmailString", "email-string", EmailString::of, EmailString::unsafeOf, "hello@example.com", "hello@example"),
        new RefinedCase<String>("AsciiString", "ascii-string", AsciiString::of, AsciiString::unsafeOf, "ASCII-123", "한글"),
        new RefinedCase<String>("AlphabeticString", "alphabetic-string", AlphabeticString::of, AlphabeticString::unsafeOf, "Alphabet", "Alpha1"),
        new RefinedCase<String>("NumericString", "numeric-string", NumericString::of, NumericString::unsafeOf, "12345", "12a45"),
        new RefinedCase<String>("AlphanumericString", "alphanumeric-string", AlphanumericString::of, AlphanumericString::unsafeOf, "Abc123", "abc-123"),
        new RefinedCase<String>("SlugString", "slug-string", SlugString::of, SlugString::unsafeOf, "hello-world-2", "Hello World"),
        new RefinedCase<String>("LowerCaseString", "lower-case-string", LowerCaseString::of, LowerCaseString::unsafeOf, "lower-case", "Lower-Case"),
        new RefinedCase<String>("UpperCaseString", "upper-case-string", UpperCaseString::of, UpperCaseString::unsafeOf, "UPPER-CASE", "Upper-Case")
    );

    @TestFactory
    Stream<DynamicTest> additionalStringTypesSupportFactoriesAndValidation() {
        return STRING_CASES.stream().flatMap(testCase -> Stream.of(
            DynamicTest.dynamicTest(testCase.typeName() + " accepts valid input", () -> assertEquals(testCase.valid(), testCase.of().apply(testCase.valid()).get().value())),
            DynamicTest.dynamicTest(testCase.typeName() + " rejects invalid input", () -> assertEquals(testCase.code(), testCase.of().apply(testCase.invalid()).getError().code())),
            DynamicTest.dynamicTest(testCase.typeName() + " rejects null", () -> assertEquals(testCase.code(), testCase.of().apply(null).getError().code())),
            DynamicTest.dynamicTest(testCase.typeName() + " unsafeOf throws on invalid input", () -> assertThrows(RefinementException.class, () -> testCase.unsafeOf().apply(testCase.invalid()))),
            DynamicTest.dynamicTest(testCase.typeName() + " equality and string are stable", () -> {
                Refined<String> left = testCase.unsafeOf().apply(testCase.valid());
                Refined<String> right = testCase.unsafeOf().apply(testCase.valid());

                assertEquals(left, right);
                assertEquals(testCase.typeName(), left.typeName());
                assertTrue(left.toString().contains(testCase.typeName()));
            })
        ));
    }

    @Test
    void alphaNumericFamiliesRejectEmptyStrings() {
        assertEquals("alphabetic-string", AlphabeticString.of("").getError().code());
        assertEquals("numeric-string", NumericString.of("").getError().code());
        assertEquals("alphanumeric-string", AlphanumericString.of("").getError().code());
    }

    @Test
    void emailStringRejectsCommonFalsePositives() {
        assertEquals("email-string", EmailString.of(".abc@example.com").getError().code());
        assertEquals("email-string", EmailString.of("abc.@example.com").getError().code());
        assertEquals("email-string", EmailString.of("a..b@example.com").getError().code());
        assertEquals("email-string", EmailString.of("abc@example..com").getError().code());
        assertEquals("email-string", EmailString.of("abc@example").getError().code());
        assertEquals("email-string", EmailString.of("abc@.example.com").getError().code());
        assertEquals("email-string", EmailString.of("abc@example.com.").getError().code());
        assertEquals("email-string", EmailString.of("abc@@example.com").getError().code());
        assertEquals("email-string", EmailString.of("abc@example.com@").getError().code());
        assertEquals("email-string", EmailString.of("abc@example!.com").getError().code());
        assertEquals("email-string", EmailString.of("abc@-example.com").getError().code());
        assertEquals("email-string", EmailString.of("abc@example-.com").getError().code());
        assertEquals("email-string", EmailString.of(null).getError().code());
        assertEquals("a+b.c@example-domain.com", EmailString.of("a+b.c@example-domain.com").get().value());
    }
}
