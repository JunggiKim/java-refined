package io.github.junggikim.refined.refined;

import static io.github.junggikim.refined.support.TestCollections.listOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.junggikim.refined.core.Refined;
import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.refined.string.HexString;
import io.github.junggikim.refined.refined.string.Ipv4String;
import io.github.junggikim.refined.refined.string.Ipv6String;
import io.github.junggikim.refined.refined.string.RegexString;
import io.github.junggikim.refined.refined.string.UrlString;
import io.github.junggikim.refined.refined.string.ValidBigDecimalString;
import io.github.junggikim.refined.refined.string.ValidBigIntegerString;
import io.github.junggikim.refined.refined.string.ValidByteString;
import io.github.junggikim.refined.refined.string.ValidDoubleString;
import io.github.junggikim.refined.refined.string.ValidFloatString;
import io.github.junggikim.refined.refined.string.ValidIntString;
import io.github.junggikim.refined.refined.string.ValidLongString;
import io.github.junggikim.refined.refined.string.ValidShortString;
import io.github.junggikim.refined.refined.string.XmlString;
import io.github.junggikim.refined.refined.string.XPathString;
import io.github.junggikim.refined.support.RefinedCase;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class AdditionalStringRefinedTest {

    private static final List<RefinedCase<String>> STRING_CASES = listOf(
        new RefinedCase<String>("RegexString", "regex-string", RegexString::of, RegexString::unsafeOf, "[a-z]+", "[a-z"),
        new RefinedCase<String>("UrlString", "url-string", UrlString::of, UrlString::unsafeOf, "https://example.com/path", "ht^tp://bad"),
        new RefinedCase<String>("Ipv4String", "ipv4-string", Ipv4String::of, Ipv4String::unsafeOf, "192.168.0.1", "256.1.1.1"),
        new RefinedCase<String>("Ipv6String", "ipv6-string", Ipv6String::of, Ipv6String::unsafeOf, "2001:db8::1", "192.168.0.1"),
        new RefinedCase<String>("HexString", "hex-string", HexString::of, HexString::unsafeOf, "deadBEEF", "xyz"),
        new RefinedCase<String>("XmlString", "xml-string", XmlString::of, XmlString::unsafeOf, "<root><item/></root>", "<root>"),
        new RefinedCase<String>("XPathString", "xpath-string", XPathString::of, XPathString::unsafeOf, "/root/item[@id='1']", "//*["),
        new RefinedCase<String>("ValidByteString", "valid-byte-string", ValidByteString::of, ValidByteString::unsafeOf, "127", "128"),
        new RefinedCase<String>("ValidShortString", "valid-short-string", ValidShortString::of, ValidShortString::unsafeOf, "32767", "40000"),
        new RefinedCase<String>("ValidIntString", "valid-int-string", ValidIntString::of, ValidIntString::unsafeOf, "42", "2147483648"),
        new RefinedCase<String>("ValidLongString", "valid-long-string", ValidLongString::of, ValidLongString::unsafeOf, "9223372036854775807", "9223372036854775808"),
        new RefinedCase<String>("ValidFloatString", "valid-float-string", ValidFloatString::of, ValidFloatString::unsafeOf, "1.5", "not-a-float"),
        new RefinedCase<String>("ValidDoubleString", "valid-double-string", ValidDoubleString::of, ValidDoubleString::unsafeOf, "1.5", "not-a-double"),
        new RefinedCase<String>("ValidBigIntegerString", "valid-big-integer-string", ValidBigIntegerString::of, ValidBigIntegerString::unsafeOf, "12345678901234567890", "12.3"),
        new RefinedCase<String>("ValidBigDecimalString", "valid-big-decimal-string", ValidBigDecimalString::of, ValidBigDecimalString::unsafeOf, "1234567890.1234", "12a")
    );

    @TestFactory
    Stream<DynamicTest> parserBackedStringWrappersSupportFactoriesAndValidation() {
        return STRING_CASES.stream().flatMap(this::testsForStringCase);
    }

    @Test
    void xmlStringRejectsDoctypeDeclarations() {
        String xml = "<!DOCTYPE root><root><item/></root>";

        assertEquals("xml-string", XmlString.of(xml).getError().code());
        assertThrows(RefinementException.class, () -> XmlString.unsafeOf(xml));
    }

    @Test
    void xmlStringRejectsExternalEntities() {
        String xml = "<!DOCTYPE root [<!ENTITY xxe SYSTEM \"file:///etc/passwd\">]><root>&xxe;</root>";

        assertEquals("xml-string", XmlString.of(xml).getError().code());
        assertThrows(RefinementException.class, () -> XmlString.unsafeOf(xml));
    }

    private Stream<DynamicTest> testsForStringCase(RefinedCase<String> testCase) {
        return Stream.of(
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
        );
    }
}
