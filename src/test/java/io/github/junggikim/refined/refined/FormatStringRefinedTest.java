package io.github.junggikim.refined.refined;

import static io.github.junggikim.refined.support.TestCollections.listOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.junggikim.refined.core.Refined;
import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.refined.character.SpecialChar;
import io.github.junggikim.refined.refined.numeric.ZeroToOneDouble;
import io.github.junggikim.refined.refined.numeric.ZeroToOneFloat;
import io.github.junggikim.refined.refined.string.Base64UrlString;
import io.github.junggikim.refined.refined.string.Base64String;
import io.github.junggikim.refined.refined.string.CidrV4String;
import io.github.junggikim.refined.refined.string.CidrV6String;
import io.github.junggikim.refined.refined.string.CreditCardString;
import io.github.junggikim.refined.refined.string.HexColorString;
import io.github.junggikim.refined.refined.string.HostnameString;
import io.github.junggikim.refined.refined.string.IsbnString;
import io.github.junggikim.refined.refined.string.Iso8601DateString;
import io.github.junggikim.refined.refined.string.Iso8601DateTimeString;
import io.github.junggikim.refined.refined.string.Iso8601DurationString;
import io.github.junggikim.refined.refined.string.Iso8601PeriodString;
import io.github.junggikim.refined.refined.string.Iso8601TimeString;
import io.github.junggikim.refined.refined.string.JsonString;
import io.github.junggikim.refined.refined.string.JwtString;
import io.github.junggikim.refined.refined.string.MacAddressString;
import io.github.junggikim.refined.refined.string.SemVerString;
import io.github.junggikim.refined.refined.string.TimeZoneIdString;
import io.github.junggikim.refined.refined.string.UlidString;
import io.github.junggikim.refined.support.RefinedCase;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class FormatStringRefinedTest {

    private static final List<RefinedCase<String>> FORMAT_CASES = listOf(
        new RefinedCase<String>("Base64String", "base64-string", Base64String::of, Base64String::unsafeOf, "SGVsbG8=", "Not!Valid@#"),
        new RefinedCase<String>("Base64UrlString", "base64-url-string", Base64UrlString::of, Base64UrlString::unsafeOf, "____", "a+b"),
        new RefinedCase<String>("UlidString", "ulid-string", UlidString::of, UlidString::unsafeOf, "01ARZ3NDEKTSV4RRFFQ69G5FAV", "01ARZ3NDEKTSV4RRFFQ69G5FAI"),
        new RefinedCase<String>("JsonString", "json-string", JsonString::of, JsonString::unsafeOf, "{\"key\":\"value\"}", "{invalid"),
        new RefinedCase<String>("CidrV4String", "cidr-v4-string", CidrV4String::of, CidrV4String::unsafeOf, "192.168.1.0/24", "256.1.1.1/24"),
        new RefinedCase<String>("CidrV6String", "cidr-v6-string", CidrV6String::of, CidrV6String::unsafeOf, "2001:db8::/32", "192.168.0.1/24"),
        new RefinedCase<String>("MacAddressString", "mac-address-string", MacAddressString::of, MacAddressString::unsafeOf, "AA:BB:CC:DD:EE:FF", "GG:HH:CC:DD:EE:FF"),
        new RefinedCase<String>("SemVerString", "semver-string", SemVerString::of, SemVerString::unsafeOf, "1.2.3", "v1.2.3"),
        new RefinedCase<String>("CreditCardString", "credit-card-string", CreditCardString::of, CreditCardString::unsafeOf, "4111111111111111", "4111111111111112"),
        new RefinedCase<String>("IsbnString", "isbn-string", IsbnString::of, IsbnString::unsafeOf, "978-3-16-148410-0", "0306406153"),
        new RefinedCase<String>("HostnameString", "hostname-string", HostnameString::of, HostnameString::unsafeOf, "example.com", "-host.com"),
        new RefinedCase<String>("JwtString", "jwt-string", JwtString::of, JwtString::unsafeOf, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIn0.dBjftJeZ4CVP", "a.b"),
        new RefinedCase<String>("Iso8601DateString", "iso8601-date-string", Iso8601DateString::of, Iso8601DateString::unsafeOf, "2024-01-15", "2024-13-01"),
        new RefinedCase<String>("Iso8601TimeString", "iso8601-time-string", Iso8601TimeString::of, Iso8601TimeString::unsafeOf, "14:30:00", "25:00:00"),
        new RefinedCase<String>("Iso8601DateTimeString", "iso8601-datetime-string", Iso8601DateTimeString::of, Iso8601DateTimeString::unsafeOf, "2024-01-15T14:30:00Z", "2024-01-15"),
        new RefinedCase<String>("Iso8601DurationString", "iso8601-duration-string", Iso8601DurationString::of, Iso8601DurationString::unsafeOf, "PT1H30M", "P1Y"),
        new RefinedCase<String>("Iso8601PeriodString", "iso8601-period-string", Iso8601PeriodString::of, Iso8601PeriodString::unsafeOf, "P1Y2M3D", "PT1H"),
        new RefinedCase<String>("TimeZoneIdString", "time-zone-id-string", TimeZoneIdString::of, TimeZoneIdString::unsafeOf, "Asia/Seoul", "Invalid/Zone"),
        new RefinedCase<String>("HexColorString", "hex-color-string", HexColorString::of, HexColorString::unsafeOf, "#A1B2C3", "A1B2C3")
    );

    @TestFactory
    Stream<DynamicTest> formatStringTypesSupportFactoriesAndValidation() {
        return FORMAT_CASES.stream().flatMap(testCase -> Stream.of(
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
    void macAddressStringAcceptsAllThreeFormats() {
        assertEquals("AA:BB:CC:DD:EE:FF", MacAddressString.of("AA:BB:CC:DD:EE:FF").get().value());
        assertEquals("aa:bb:cc:dd:ee:ff", MacAddressString.of("aa:bb:cc:dd:ee:ff").get().value());
        assertEquals("AA-BB-CC-DD-EE-FF", MacAddressString.of("AA-BB-CC-DD-EE-FF").get().value());
        assertEquals("AABB.CCDD.EEFF", MacAddressString.of("AABB.CCDD.EEFF").get().value());
        assertEquals("mac-address-string", MacAddressString.of("AA:BB:CC:DD:EE").getError().code());
        assertEquals("mac-address-string", MacAddressString.of("AA:BB-CC:DD:EE:FF").getError().code());
        assertEquals("mac-address-string", MacAddressString.of("").getError().code());
    }

    @Test
    void semVerStringRejectsLeadingZerosAndMissingParts() {
        assertEquals("1.2.3-alpha.1", SemVerString.of("1.2.3-alpha.1").get().value());
        assertEquals("1.2.3+build.123", SemVerString.of("1.2.3+build.123").get().value());
        assertEquals("1.2.3-alpha+build", SemVerString.of("1.2.3-alpha+build").get().value());
        assertEquals("0.0.0", SemVerString.of("0.0.0").get().value());
        assertEquals("semver-string", SemVerString.of("01.2.3").getError().code());
        assertEquals("semver-string", SemVerString.of("1.2").getError().code());
        assertEquals("semver-string", SemVerString.of("").getError().code());
    }

    @Test
    void creditCardStringAcceptsSpacesAndHyphens() {
        assertEquals("4111 1111 1111 1111", CreditCardString.of("4111 1111 1111 1111").get().value());
        assertEquals("4111-1111-1111-1111", CreditCardString.of("4111-1111-1111-1111").get().value());
        assertEquals("5500000000000004", CreditCardString.of("5500000000000004").get().value());
        assertEquals("credit-card-string", CreditCardString.of("1234").getError().code());
        assertEquals("credit-card-string", CreditCardString.of("abcdefghijklm").getError().code());
        assertEquals("credit-card-string", CreditCardString.of("!111111111111").getError().code());
        assertEquals("credit-card-string", CreditCardString.of("").getError().code());
        assertEquals("credit-card-string", CreditCardString.of("12345678901234567890").getError().code());
    }

    @Test
    void isbnStringAcceptsIsbn10AndIsbn13() {
        assertEquals("0-306-40615-2", IsbnString.of("0-306-40615-2").get().value());
        assertEquals("0306406152", IsbnString.of("0306406152").get().value());
        assertEquals("9783161484100", IsbnString.of("9783161484100").get().value());
        assertEquals("isbn-string", IsbnString.of("123").getError().code());
        assertEquals("isbn-string", IsbnString.of("").getError().code());
    }

    @Test
    void isbnStringAcceptsIsbn10WithXCheckDigit() {
        assertEquals("007462542X", IsbnString.of("007462542X").get().value());
        assertEquals("007462542x", IsbnString.of("007462542x").get().value());
    }

    @Test
    void isbnStringRejectsInvalidChecksum() {
        assertEquals("isbn-string", IsbnString.of("0306406153").getError().code());
        assertEquals("isbn-string", IsbnString.of("9783161484101").getError().code());
    }

    @Test
    void isbnStringRejectsNonDigitCharacters() {
        assertEquals("isbn-string", IsbnString.of("030640615A").getError().code());
        assertEquals("isbn-string", IsbnString.of("978316148410A").getError().code());
        assertEquals("isbn-string", IsbnString.of("!306406152").getError().code());
        assertEquals("isbn-string", IsbnString.of("978!161484100").getError().code());
    }

    @Test
    void isbnStringRejectsIsbn10WithXInMiddle() {
        assertEquals("isbn-string", IsbnString.of("030X406152").getError().code());
    }

    @Test
    void isbnStringRejectsIsbn10WithInvalidLastChar() {
        assertEquals("isbn-string", IsbnString.of("030640615!").getError().code());
    }

    @Test
    void hostnameLabelBoundaries() {
        assertEquals("localhost", HostnameString.of("localhost").get().value());
        assertEquals("my-host", HostnameString.of("my-host").get().value());
        assertEquals("123abc.com", HostnameString.of("123abc.com").get().value());
        assertEquals("sub.domain.example.com", HostnameString.of("sub.domain.example.com").get().value());
        assertEquals("Host.COM", HostnameString.of("Host.COM").get().value());
        assertEquals("A-1.b-2", HostnameString.of("A-1.b-2").get().value());
        assertEquals("hostname-string", HostnameString.of("").getError().code());
        assertEquals("hostname-string", HostnameString.of("host-.com").getError().code());
        assertEquals("hostname-string", HostnameString.of("host..com").getError().code());
        assertEquals("hostname-string", HostnameString.of("host.com.").getError().code());
        assertEquals("hostname-string", HostnameString.of("host_name.com").getError().code());
        assertEquals("hostname-string", HostnameString.of("host{.com").getError().code());

        StringBuilder longLabel = new StringBuilder();
        for (int i = 0; i < 64; i++) {
            longLabel.append('a');
        }
        assertEquals("hostname-string", HostnameString.of(longLabel.toString()).getError().code());

        StringBuilder longHostname = new StringBuilder();
        for (int i = 0; i < 51; i++) {
            if (i > 0) {
                longHostname.append('.');
            }
            longHostname.append("aaaaa");
        }
        assertEquals("hostname-string", HostnameString.of(longHostname.toString()).getError().code());
    }

    @Test
    void jwtStringHandlesEmptySignature() {
        assertEquals("a.b.", JwtString.of("a.b.").get().value());
        assertEquals("jwt-string", JwtString.of("a.b").getError().code());
        assertEquals("jwt-string", JwtString.of("a.b.c.d").getError().code());
        assertEquals("jwt-string", JwtString.of(".b.c").getError().code());
        assertEquals("jwt-string", JwtString.of("a..c").getError().code());
        assertEquals("jwt-string", JwtString.of("a.b.c!").getError().code());
        assertEquals("jwt-string", JwtString.of("a!.b.c").getError().code());
        assertEquals("jwt-string", JwtString.of("a.b!.c").getError().code());
        assertEquals("jwt-string", JwtString.of("nodots").getError().code());
    }

    @Test
    void iso8601DateRejectsInvalidLeapYear() {
        assertEquals("2024-02-29", Iso8601DateString.of("2024-02-29").get().value());
        assertEquals("iso8601-date-string", Iso8601DateString.of("2023-02-29").getError().code());
        assertEquals("iso8601-date-string", Iso8601DateString.of("").getError().code());
    }

    @Test
    void iso8601TimeAcceptsShortForm() {
        assertEquals("14:30", Iso8601TimeString.of("14:30").get().value());
        assertEquals("23:59:59", Iso8601TimeString.of("23:59:59").get().value());
        assertEquals("00:00:00", Iso8601TimeString.of("00:00:00").get().value());
        assertEquals("iso8601-time-string", Iso8601TimeString.of("").getError().code());
    }

    @Test
    void iso8601DateTimeAcceptsTimezones() {
        assertEquals("2024-01-15T14:30:00+09:00", Iso8601DateTimeString.of("2024-01-15T14:30:00+09:00").get().value());
        assertEquals("2024-01-15T14:30:00", Iso8601DateTimeString.of("2024-01-15T14:30:00").get().value());
        assertEquals("iso8601-datetime-string", Iso8601DateTimeString.of("").getError().code());
    }

    @Test
    void cidrV4StringEdgeCases() {
        assertEquals("10.0.0.0/8", CidrV4String.of("10.0.0.0/8").get().value());
        assertEquals("0.0.0.0/0", CidrV4String.of("0.0.0.0/0").get().value());
        assertEquals("255.255.255.255/32", CidrV4String.of("255.255.255.255/32").get().value());
        assertEquals("cidr-v4-string", CidrV4String.of("192.168.1.0/33").getError().code());
        assertEquals("cidr-v4-string", CidrV4String.of("192.168.1.0").getError().code());
        assertEquals("cidr-v4-string", CidrV4String.of("").getError().code());
    }

    @Test
    void cidrV6StringEdgeCases() {
        assertEquals("::1/128", CidrV6String.of("::1/128").get().value());
        assertEquals("::/0", CidrV6String.of("::/0").get().value());
        assertEquals("cidr-v6-string", CidrV6String.of("2001:db8::/129").getError().code());
        assertEquals("cidr-v6-string", CidrV6String.of("").getError().code());
        assertEquals("cidr-v6-string", CidrV6String.of("no-slash").getError().code());
        assertEquals("cidr-v6-string", CidrV6String.of("::1/-1").getError().code());
        assertEquals("cidr-v6-string", CidrV6String.of("not-ipv6/64").getError().code());
        assertEquals("cidr-v6-string", CidrV6String.of("/64").getError().code());
    }

    @Test
    void base64StringEdgeCases() {
        assertEquals("YQ==", Base64String.of("YQ==").get().value());
        assertEquals("", Base64String.of("").get().value());
        assertEquals("SGVsbG8", Base64String.of("SGVsbG8").get().value());
        assertEquals("base64-string", Base64String.of("===").getError().code());
    }

    @Test
    void base64UrlStringEdgeCases() {
        assertEquals("", Base64UrlString.of("").get().value());
        assertEquals("Zg==", Base64UrlString.of("Zg==").get().value());
        assertEquals("____", Base64UrlString.of("____").get().value());
        assertEquals("base64-url-string", Base64UrlString.of("a+b").getError().code());
        assertEquals("base64-url-string", Base64UrlString.of("a/b").getError().code());
    }

    @Test
    void ulidStringAcceptsLowercaseAndRejectsInvalidChars() {
        assertEquals("01ARZ3NDEKTSV4RRFFQ69G5FAV", UlidString.of("01ARZ3NDEKTSV4RRFFQ69G5FAV").get().value());
        assertEquals("01arz3ndektsv4rrffq69g5fav", UlidString.of("01arz3ndektsv4rrffq69g5fav").get().value());
        assertEquals("ulid-string", UlidString.of("01ARZ3NDEKTSV4RRFFQ69G5FAI").getError().code());
        assertEquals("ulid-string", UlidString.of("01ARZ3NDEKTSV4RRFFQ69G5FA").getError().code());
    }

    @Test
    void hexColorStringSupportsRgbAndRgba() {
        assertEquals("#FFF", HexColorString.of("#FFF").get().value());
        assertEquals("#ffcc00", HexColorString.of("#ffcc00").get().value());
        assertEquals("#11223344", HexColorString.of("#11223344").get().value());
        assertEquals("hex-color-string", HexColorString.of("#1234").getError().code());
        assertEquals("hex-color-string", HexColorString.of("##123").getError().code());
        assertEquals("hex-color-string", HexColorString.of("#12").getError().code());
    }

    @Test
    void iso8601DurationStringAcceptsDaysAndRejectsYears() {
        assertEquals("P2D", Iso8601DurationString.of("P2D").get().value());
        assertEquals("iso8601-duration-string", Iso8601DurationString.of("P1Y").getError().code());
        assertEquals("iso8601-duration-string", Iso8601DurationString.of("PT").getError().code());
    }

    @Test
    void iso8601PeriodStringRejectsTimeComponents() {
        assertEquals("P0D", Iso8601PeriodString.of("P0D").get().value());
        assertEquals("iso8601-period-string", Iso8601PeriodString.of("P1Y2M3DT4H").getError().code());
    }

    @Test
    void timeZoneIdStringAcceptsOffsets() {
        assertEquals("UTC", TimeZoneIdString.of("UTC").get().value());
        assertEquals("Z", TimeZoneIdString.of("Z").get().value());
        assertEquals("+09:00", TimeZoneIdString.of("+09:00").get().value());
        assertEquals("time-zone-id-string", TimeZoneIdString.of("UTC+25:00").getError().code());
    }

    @Test
    void zeroToOneDoubleEdgeCases() {
        assertEquals(0.0d, ZeroToOneDouble.of(0.0d).get().value());
        assertEquals(1.0d, ZeroToOneDouble.of(1.0d).get().value());
        assertEquals(0.5d, ZeroToOneDouble.of(0.5d).get().value());
        assertEquals(-0.0d, ZeroToOneDouble.of(-0.0d).get().value());
        assertEquals("zero-to-one-double", ZeroToOneDouble.of(-0.001d).getError().code());
        assertEquals("zero-to-one-double", ZeroToOneDouble.of(1.001d).getError().code());
        assertEquals("zero-to-one-double", ZeroToOneDouble.of(Double.NaN).getError().code());
        assertEquals("zero-to-one-double", ZeroToOneDouble.of(Double.POSITIVE_INFINITY).getError().code());
        assertEquals("zero-to-one-double", ZeroToOneDouble.of(null).getError().code());
        assertThrows(RefinementException.class, () -> ZeroToOneDouble.unsafeOf(2.0d));

        Refined<Double> left = ZeroToOneDouble.unsafeOf(0.5d);
        Refined<Double> right = ZeroToOneDouble.unsafeOf(0.5d);
        assertEquals(left, right);
        assertEquals("ZeroToOneDouble", left.typeName());
        assertTrue(left.toString().contains("ZeroToOneDouble"));
    }

    @Test
    void zeroToOneFloatEdgeCases() {
        assertEquals(0.0f, ZeroToOneFloat.of(0.0f).get().value());
        assertEquals(1.0f, ZeroToOneFloat.of(1.0f).get().value());
        assertEquals(0.5f, ZeroToOneFloat.of(0.5f).get().value());
        assertEquals(-0.0f, ZeroToOneFloat.of(-0.0f).get().value());
        assertEquals("zero-to-one-float", ZeroToOneFloat.of(-0.001f).getError().code());
        assertEquals("zero-to-one-float", ZeroToOneFloat.of(1.001f).getError().code());
        assertEquals("zero-to-one-float", ZeroToOneFloat.of(Float.NaN).getError().code());
        assertEquals("zero-to-one-float", ZeroToOneFloat.of(Float.POSITIVE_INFINITY).getError().code());
        assertEquals("zero-to-one-float", ZeroToOneFloat.of(null).getError().code());
        assertThrows(RefinementException.class, () -> ZeroToOneFloat.unsafeOf(2.0f));

        Refined<Float> left = ZeroToOneFloat.unsafeOf(0.5f);
        Refined<Float> right = ZeroToOneFloat.unsafeOf(0.5f);
        assertEquals(left, right);
        assertEquals("ZeroToOneFloat", left.typeName());
        assertTrue(left.toString().contains("ZeroToOneFloat"));
    }

    @Test
    void specialCharEdgeCases() {
        assertEquals('!', SpecialChar.of('!').get().value());
        assertEquals('@', SpecialChar.of('@').get().value());
        assertEquals('#', SpecialChar.of('#').get().value());
        assertEquals("special-char", SpecialChar.of('a').getError().code());
        assertEquals("special-char", SpecialChar.of('1').getError().code());
        assertEquals("special-char", SpecialChar.of(' ').getError().code());
        assertEquals("special-char", SpecialChar.of('\u00A0').getError().code());
        assertEquals("special-char", SpecialChar.of(null).getError().code());
        assertThrows(RefinementException.class, () -> SpecialChar.unsafeOf('a'));

        Refined<Character> left = SpecialChar.unsafeOf('!');
        Refined<Character> right = SpecialChar.unsafeOf('!');
        assertEquals(left, right);
        assertEquals("SpecialChar", left.typeName());
        assertTrue(left.toString().contains("SpecialChar"));
    }
}
