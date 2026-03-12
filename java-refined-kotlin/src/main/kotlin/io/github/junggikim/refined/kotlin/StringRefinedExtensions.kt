package io.github.junggikim.refined.kotlin

import io.github.junggikim.refined.refined.string.AlphabeticString
import io.github.junggikim.refined.refined.string.AlphanumericString
import io.github.junggikim.refined.refined.string.AsciiString
import io.github.junggikim.refined.refined.string.Base64String
import io.github.junggikim.refined.refined.string.Base64UrlString
import io.github.junggikim.refined.refined.string.CidrV4String
import io.github.junggikim.refined.refined.string.CidrV6String
import io.github.junggikim.refined.refined.string.CreditCardString
import io.github.junggikim.refined.refined.string.EmailString
import io.github.junggikim.refined.refined.string.HexColorString
import io.github.junggikim.refined.refined.string.HexString
import io.github.junggikim.refined.refined.string.HostnameString
import io.github.junggikim.refined.refined.string.Ipv4String
import io.github.junggikim.refined.refined.string.Ipv6String
import io.github.junggikim.refined.refined.string.IsbnString
import io.github.junggikim.refined.refined.string.Iso8601DateString
import io.github.junggikim.refined.refined.string.Iso8601DateTimeString
import io.github.junggikim.refined.refined.string.Iso8601DurationString
import io.github.junggikim.refined.refined.string.Iso8601PeriodString
import io.github.junggikim.refined.refined.string.Iso8601TimeString
import io.github.junggikim.refined.refined.string.JsonString
import io.github.junggikim.refined.refined.string.JwtString
import io.github.junggikim.refined.refined.string.LowerCaseString
import io.github.junggikim.refined.refined.string.MacAddressString
import io.github.junggikim.refined.refined.string.NonBlankString
import io.github.junggikim.refined.refined.string.NonEmptyString
import io.github.junggikim.refined.refined.string.NumericString
import io.github.junggikim.refined.refined.string.RegexString
import io.github.junggikim.refined.refined.string.SemVerString
import io.github.junggikim.refined.refined.string.SlugString
import io.github.junggikim.refined.refined.string.TimeZoneIdString
import io.github.junggikim.refined.refined.string.TrimmedString
import io.github.junggikim.refined.refined.string.UlidString
import io.github.junggikim.refined.refined.string.UpperCaseString
import io.github.junggikim.refined.refined.string.UriString
import io.github.junggikim.refined.refined.string.UrlString
import io.github.junggikim.refined.refined.string.UuidString
import io.github.junggikim.refined.refined.string.ValidBigDecimalString
import io.github.junggikim.refined.refined.string.ValidBigIntegerString
import io.github.junggikim.refined.refined.string.ValidByteString
import io.github.junggikim.refined.refined.string.ValidDoubleString
import io.github.junggikim.refined.refined.string.ValidFloatString
import io.github.junggikim.refined.refined.string.ValidIntString
import io.github.junggikim.refined.refined.string.ValidLongString
import io.github.junggikim.refined.refined.string.ValidShortString
import io.github.junggikim.refined.refined.string.XPathString
import io.github.junggikim.refined.refined.string.XmlString
import io.github.junggikim.refined.validation.Validation
import io.github.junggikim.refined.violation.Violation

fun String?.toAlphabeticString(): Validation<Violation, AlphabeticString> = AlphabeticString.of(this)
fun String?.toAlphabeticStringOrThrow(): AlphabeticString = AlphabeticString.unsafeOf(this)
fun String?.toAlphanumericString(): Validation<Violation, AlphanumericString> = AlphanumericString.of(this)
fun String?.toAlphanumericStringOrThrow(): AlphanumericString = AlphanumericString.unsafeOf(this)
fun String?.toAsciiString(): Validation<Violation, AsciiString> = AsciiString.of(this)
fun String?.toAsciiStringOrThrow(): AsciiString = AsciiString.unsafeOf(this)
fun String?.toBase64String(): Validation<Violation, Base64String> = Base64String.of(this)
fun String?.toBase64StringOrThrow(): Base64String = Base64String.unsafeOf(this)
fun String?.toBase64UrlString(): Validation<Violation, Base64UrlString> = Base64UrlString.of(this)
fun String?.toBase64UrlStringOrThrow(): Base64UrlString = Base64UrlString.unsafeOf(this)
fun String?.toCidrV4String(): Validation<Violation, CidrV4String> = CidrV4String.of(this)
fun String?.toCidrV4StringOrThrow(): CidrV4String = CidrV4String.unsafeOf(this)
fun String?.toCidrV6String(): Validation<Violation, CidrV6String> = CidrV6String.of(this)
fun String?.toCidrV6StringOrThrow(): CidrV6String = CidrV6String.unsafeOf(this)
fun String?.toCreditCardString(): Validation<Violation, CreditCardString> = CreditCardString.of(this)
fun String?.toCreditCardStringOrThrow(): CreditCardString = CreditCardString.unsafeOf(this)
fun String?.toEmailString(): Validation<Violation, EmailString> = EmailString.of(this)
fun String?.toEmailStringOrThrow(): EmailString = EmailString.unsafeOf(this)
fun String?.toHexColorString(): Validation<Violation, HexColorString> = HexColorString.of(this)
fun String?.toHexColorStringOrThrow(): HexColorString = HexColorString.unsafeOf(this)
fun String?.toHexString(): Validation<Violation, HexString> = HexString.of(this)
fun String?.toHexStringOrThrow(): HexString = HexString.unsafeOf(this)
fun String?.toHostnameString(): Validation<Violation, HostnameString> = HostnameString.of(this)
fun String?.toHostnameStringOrThrow(): HostnameString = HostnameString.unsafeOf(this)
fun String?.toIpv4String(): Validation<Violation, Ipv4String> = Ipv4String.of(this)
fun String?.toIpv4StringOrThrow(): Ipv4String = Ipv4String.unsafeOf(this)
fun String?.toIpv6String(): Validation<Violation, Ipv6String> = Ipv6String.of(this)
fun String?.toIpv6StringOrThrow(): Ipv6String = Ipv6String.unsafeOf(this)
fun String?.toIsbnString(): Validation<Violation, IsbnString> = IsbnString.of(this)
fun String?.toIsbnStringOrThrow(): IsbnString = IsbnString.unsafeOf(this)
fun String?.toIso8601DateString(): Validation<Violation, Iso8601DateString> = Iso8601DateString.of(this)
fun String?.toIso8601DateStringOrThrow(): Iso8601DateString = Iso8601DateString.unsafeOf(this)
fun String?.toIso8601DateTimeString(): Validation<Violation, Iso8601DateTimeString> = Iso8601DateTimeString.of(this)
fun String?.toIso8601DateTimeStringOrThrow(): Iso8601DateTimeString = Iso8601DateTimeString.unsafeOf(this)
fun String?.toIso8601DurationString(): Validation<Violation, Iso8601DurationString> = Iso8601DurationString.of(this)
fun String?.toIso8601DurationStringOrThrow(): Iso8601DurationString = Iso8601DurationString.unsafeOf(this)
fun String?.toIso8601PeriodString(): Validation<Violation, Iso8601PeriodString> = Iso8601PeriodString.of(this)
fun String?.toIso8601PeriodStringOrThrow(): Iso8601PeriodString = Iso8601PeriodString.unsafeOf(this)
fun String?.toIso8601TimeString(): Validation<Violation, Iso8601TimeString> = Iso8601TimeString.of(this)
fun String?.toIso8601TimeStringOrThrow(): Iso8601TimeString = Iso8601TimeString.unsafeOf(this)
fun String?.toJsonString(): Validation<Violation, JsonString> = JsonString.of(this)
fun String?.toJsonStringOrThrow(): JsonString = JsonString.unsafeOf(this)
fun String?.toJwtString(): Validation<Violation, JwtString> = JwtString.of(this)
fun String?.toJwtStringOrThrow(): JwtString = JwtString.unsafeOf(this)
fun String?.toLowerCaseString(): Validation<Violation, LowerCaseString> = LowerCaseString.of(this)
fun String?.toLowerCaseStringOrThrow(): LowerCaseString = LowerCaseString.unsafeOf(this)
fun String?.toMacAddressString(): Validation<Violation, MacAddressString> = MacAddressString.of(this)
fun String?.toMacAddressStringOrThrow(): MacAddressString = MacAddressString.unsafeOf(this)
fun String?.toNonBlankString(): Validation<Violation, NonBlankString> = NonBlankString.of(this)
fun String?.toNonBlankStringOrThrow(): NonBlankString = NonBlankString.unsafeOf(this)
fun String?.toNonEmptyString(): Validation<Violation, NonEmptyString> = NonEmptyString.of(this)
fun String?.toNonEmptyStringOrThrow(): NonEmptyString = NonEmptyString.unsafeOf(this)
fun String?.toNumericString(): Validation<Violation, NumericString> = NumericString.of(this)
fun String?.toNumericStringOrThrow(): NumericString = NumericString.unsafeOf(this)
fun String?.toRegexString(): Validation<Violation, RegexString> = RegexString.of(this)
fun String?.toRegexStringOrThrow(): RegexString = RegexString.unsafeOf(this)
fun String?.toSemVerString(): Validation<Violation, SemVerString> = SemVerString.of(this)
fun String?.toSemVerStringOrThrow(): SemVerString = SemVerString.unsafeOf(this)
fun String?.toSlugString(): Validation<Violation, SlugString> = SlugString.of(this)
fun String?.toSlugStringOrThrow(): SlugString = SlugString.unsafeOf(this)
fun String?.toTimeZoneIdString(): Validation<Violation, TimeZoneIdString> = TimeZoneIdString.of(this)
fun String?.toTimeZoneIdStringOrThrow(): TimeZoneIdString = TimeZoneIdString.unsafeOf(this)
fun String?.toTrimmedString(): Validation<Violation, TrimmedString> = TrimmedString.of(this)
fun String?.toTrimmedStringOrThrow(): TrimmedString = TrimmedString.unsafeOf(this)
fun String?.toUlidString(): Validation<Violation, UlidString> = UlidString.of(this)
fun String?.toUlidStringOrThrow(): UlidString = UlidString.unsafeOf(this)
fun String?.toUpperCaseString(): Validation<Violation, UpperCaseString> = UpperCaseString.of(this)
fun String?.toUpperCaseStringOrThrow(): UpperCaseString = UpperCaseString.unsafeOf(this)
fun String?.toUriString(): Validation<Violation, UriString> = UriString.of(this)
fun String?.toUriStringOrThrow(): UriString = UriString.unsafeOf(this)
fun String?.toUrlString(): Validation<Violation, UrlString> = UrlString.of(this)
fun String?.toUrlStringOrThrow(): UrlString = UrlString.unsafeOf(this)
fun String?.toUuidString(): Validation<Violation, UuidString> = UuidString.of(this)
fun String?.toUuidStringOrThrow(): UuidString = UuidString.unsafeOf(this)
fun String?.toValidBigDecimalString(): Validation<Violation, ValidBigDecimalString> = ValidBigDecimalString.of(this)
fun String?.toValidBigDecimalStringOrThrow(): ValidBigDecimalString = ValidBigDecimalString.unsafeOf(this)
fun String?.toValidBigIntegerString(): Validation<Violation, ValidBigIntegerString> = ValidBigIntegerString.of(this)
fun String?.toValidBigIntegerStringOrThrow(): ValidBigIntegerString = ValidBigIntegerString.unsafeOf(this)
fun String?.toValidByteString(): Validation<Violation, ValidByteString> = ValidByteString.of(this)
fun String?.toValidByteStringOrThrow(): ValidByteString = ValidByteString.unsafeOf(this)
fun String?.toValidDoubleString(): Validation<Violation, ValidDoubleString> = ValidDoubleString.of(this)
fun String?.toValidDoubleStringOrThrow(): ValidDoubleString = ValidDoubleString.unsafeOf(this)
fun String?.toValidFloatString(): Validation<Violation, ValidFloatString> = ValidFloatString.of(this)
fun String?.toValidFloatStringOrThrow(): ValidFloatString = ValidFloatString.unsafeOf(this)
fun String?.toValidIntString(): Validation<Violation, ValidIntString> = ValidIntString.of(this)
fun String?.toValidIntStringOrThrow(): ValidIntString = ValidIntString.unsafeOf(this)
fun String?.toValidLongString(): Validation<Violation, ValidLongString> = ValidLongString.of(this)
fun String?.toValidLongStringOrThrow(): ValidLongString = ValidLongString.unsafeOf(this)
fun String?.toValidShortString(): Validation<Violation, ValidShortString> = ValidShortString.of(this)
fun String?.toValidShortStringOrThrow(): ValidShortString = ValidShortString.unsafeOf(this)
fun String?.toXPathString(): Validation<Violation, XPathString> = XPathString.of(this)
fun String?.toXPathStringOrThrow(): XPathString = XPathString.unsafeOf(this)
fun String?.toXmlString(): Validation<Violation, XmlString> = XmlString.of(this)
fun String?.toXmlStringOrThrow(): XmlString = XmlString.unsafeOf(this)
