package io.github.junggikim.refined.refined;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.junggikim.refined.refined.string.JsonString;
import org.junit.jupiter.api.Test;

class JsonStringEdgeCaseTest {

    @Test
    void jsonStringAcceptsEmptyObjectAndArray() {
        assertEquals("{}", JsonString.of("{}").get().value());
        assertEquals("[]", JsonString.of("[]").get().value());
    }

    @Test
    void jsonStringAcceptsPrimitiveValues() {
        assertEquals("null", JsonString.of("null").get().value());
        assertEquals("true", JsonString.of("true").get().value());
        assertEquals("false", JsonString.of("false").get().value());
        assertEquals("42", JsonString.of("42").get().value());
        assertEquals("\"hello\"", JsonString.of("\"hello\"").get().value());
    }

    @Test
    void jsonStringAcceptsNumbers() {
        assertEquals("0", JsonString.of("0").get().value());
        assertEquals("-1", JsonString.of("-1").get().value());
        assertEquals("0.5", JsonString.of("0.5").get().value());
        assertEquals("-0.5", JsonString.of("-0.5").get().value());
        assertTrue(JsonString.of("1e10").isValid());
        assertTrue(JsonString.of("1E10").isValid());
        assertTrue(JsonString.of("1e+10").isValid());
        assertTrue(JsonString.of("1e-10").isValid());
        assertTrue(JsonString.of("-0.5e+10").isValid());
    }

    @Test
    void jsonStringRejectsLeadingZeros() {
        assertEquals("json-string", JsonString.of("01").getError().code());
    }

    @Test
    void jsonStringAcceptsNestedStructures() {
        assertTrue(JsonString.of("{\"a\":{\"b\":[1,2,3]}}").isValid());
        assertTrue(JsonString.of("[{\"key\":\"value\"},{\"key\":\"value\"}]").isValid());
        assertTrue(JsonString.of("[1,[2,[3]]]").isValid());
    }

    @Test
    void jsonStringAcceptsUnicodeEscapes() {
        assertTrue(JsonString.of("\"\\u0041\"").isValid());
        assertTrue(JsonString.of("\"\\u00E9\"").isValid());
    }

    @Test
    void jsonStringAcceptsAllEscapeSequences() {
        assertTrue(JsonString.of("\"\\\"\"").isValid());
        assertTrue(JsonString.of("\"\\\\\"").isValid());
        assertTrue(JsonString.of("\"\\/\"").isValid());
        assertTrue(JsonString.of("\"\\b\"").isValid());
        assertTrue(JsonString.of("\"\\f\"").isValid());
        assertTrue(JsonString.of("\"\\n\"").isValid());
        assertTrue(JsonString.of("\"\\r\"").isValid());
        assertTrue(JsonString.of("\"\\t\"").isValid());
    }

    @Test
    void jsonStringAcceptsWhitespace() {
        assertTrue(JsonString.of(" { \"key\" : \"value\" } ").isValid());
        assertTrue(JsonString.of(" [ 1 , 2 , 3 ] ").isValid());
        assertTrue(JsonString.of("\t\n\r {}").isValid());
    }

    @Test
    void jsonStringRejectsEmptyString() {
        assertEquals("json-string", JsonString.of("").getError().code());
    }

    @Test
    void jsonStringRejectsUnclosedStructures() {
        assertEquals("json-string", JsonString.of("{").getError().code());
        assertEquals("json-string", JsonString.of("[").getError().code());
        assertEquals("json-string", JsonString.of("{\"key\"").getError().code());
        assertEquals("json-string", JsonString.of("{\"key\":").getError().code());
        assertEquals("json-string", JsonString.of("[1,").getError().code());
    }

    @Test
    void jsonStringRejectsTrailingComma() {
        assertEquals("json-string", JsonString.of("[1,]").getError().code());
        assertEquals("json-string", JsonString.of("{\"a\":1,}").getError().code());
    }

    @Test
    void jsonStringRejectsSingleQuotes() {
        assertEquals("json-string", JsonString.of("{'key': 1}").getError().code());
    }

    @Test
    void jsonStringRejectsUndefined() {
        assertEquals("json-string", JsonString.of("undefined").getError().code());
    }

    @Test
    void jsonStringRejectsInvalidEscapes() {
        assertEquals("json-string", JsonString.of("\"\\x41\"").getError().code());
    }

    @Test
    void jsonStringRejectsUnclosedString() {
        assertEquals("json-string", JsonString.of("\"unclosed").getError().code());
        assertEquals("json-string", JsonString.of("\"\\").getError().code());
    }

    @Test
    void jsonStringRejectsTrailingGarbage() {
        assertEquals("json-string", JsonString.of("{}garbage").getError().code());
        assertEquals("json-string", JsonString.of("42 42").getError().code());
    }

    @Test
    void jsonStringRejectsOnlyWhitespace() {
        assertEquals("json-string", JsonString.of("   ").getError().code());
    }

    @Test
    void jsonStringRejectsNull() {
        assertEquals("json-string", JsonString.of(null).getError().code());
    }

    @Test
    void jsonStringRejectsBadNumbers() {
        assertEquals("json-string", JsonString.of("-").getError().code());
        assertEquals("json-string", JsonString.of("1.").getError().code());
        assertEquals("json-string", JsonString.of("1e").getError().code());
        assertEquals("json-string", JsonString.of("1e+").getError().code());
    }

    @Test
    void jsonStringRejectsControlCharacters() {
        assertEquals("json-string", JsonString.of("\"\t\"").getError().code());
    }

    @Test
    void jsonStringRejectsBadUnicodeEscape() {
        assertEquals("json-string", JsonString.of("\"\\u00G0\"").getError().code());
        assertEquals("json-string", JsonString.of("\"\\u00\"").getError().code());
    }

    @Test
    void jsonStringRejectsObjectWithNonStringKey() {
        assertEquals("json-string", JsonString.of("{1:2}").getError().code());
    }

    @Test
    void jsonStringRejectsObjectMissingColon() {
        assertEquals("json-string", JsonString.of("{\"key\" \"value\"}").getError().code());
    }

    @Test
    void jsonStringRejectsTruncatedLiterals() {
        assertEquals("json-string", JsonString.of("tru").getError().code());
        assertEquals("json-string", JsonString.of("fals").getError().code());
        assertEquals("json-string", JsonString.of("nul").getError().code());
    }

    @Test
    void jsonStringNumberInContext() {
        assertTrue(JsonString.of("[42]").isValid());
        assertTrue(JsonString.of("{\"a\":42}").isValid());
        assertTrue(JsonString.of("[0,1]").isValid());
        assertTrue(JsonString.of("[-0]").isValid());
        assertTrue(JsonString.of("[1.5,2]").isValid());
        assertTrue(JsonString.of("[1e2,2]").isValid());
        assertTrue(JsonString.of("[1E2]").isValid());
        assertTrue(JsonString.of("[1e-2]").isValid());
        assertTrue(JsonString.of("[12345]").isValid());
    }

    @Test
    void jsonStringNumberEdgeCasesInArrays() {
        assertEquals("json-string", JsonString.of("[-]").getError().code());
        assertEquals("json-string", JsonString.of("[1.]").getError().code());
        assertEquals("json-string", JsonString.of("[1e]").getError().code());
        assertEquals("json-string", JsonString.of("[1e+]").getError().code());
        assertEquals("json-string", JsonString.of("[01]").getError().code());
    }

    @Test
    void jsonStringObjectEdgeCases() {
        assertTrue(JsonString.of("{\"a\":1,\"b\":2}").isValid());
        assertEquals("json-string", JsonString.of("{\"a\":1,}").getError().code());
        assertEquals("json-string", JsonString.of("{\"a\":}").getError().code());
        assertEquals("json-string", JsonString.of("{\"a\"}").getError().code());
        assertEquals("json-string", JsonString.of("{\"a\":1 \"b\":2}").getError().code());
    }

    @Test
    void jsonStringArrayEdgeCases() {
        assertTrue(JsonString.of("[1,2,3]").isValid());
        assertEquals("json-string", JsonString.of("[1 2]").getError().code());
        assertEquals("json-string", JsonString.of("[").getError().code());
        assertEquals("json-string", JsonString.of("[1").getError().code());
    }

    @Test
    void jsonStringStringEdgeCases() {
        assertTrue(JsonString.of("\"\"").isValid());
        assertTrue(JsonString.of("\"abc\"").isValid());
        assertEquals("json-string", JsonString.of("\"").getError().code());
        assertEquals("json-string", JsonString.of("\"\\u\"").getError().code());
    }

    @Test
    void jsonStringMismatchedLiterals() {
        assertEquals("json-string", JsonString.of("trUe").getError().code());
        assertEquals("json-string", JsonString.of("falSe").getError().code());
        assertEquals("json-string", JsonString.of("nuLl").getError().code());
    }

    @Test
    void jsonStringNestedObjectValueMissing() {
        assertEquals("json-string", JsonString.of("{\"a\":{\"b\":}}").getError().code());
    }

    @Test
    void jsonStringEmptyValuePositions() {
        assertEquals("json-string", JsonString.of("[,]").getError().code());
        assertEquals("json-string", JsonString.of("{,}").getError().code());
    }

    @Test
    void jsonStringExponentWithMinusSign() {
        assertTrue(JsonString.of("1e-1").isValid());
        assertTrue(JsonString.of("1E+1").isValid());
        assertTrue(JsonString.of("1E-1").isValid());
    }

    @Test
    void jsonStringObjectSecondEntryFailures() {
        assertEquals("json-string", JsonString.of("{\"a\":1,\"unclosed}").getError().code());
        assertEquals("json-string", JsonString.of("{\"a\":1,").getError().code());
        assertEquals("json-string", JsonString.of("{\"a\":1").getError().code());
        assertEquals("json-string", JsonString.of("{\"a\":1,2}").getError().code());
    }

    @Test
    void jsonStringUnicodeEscapeUpperCase() {
        assertTrue(JsonString.of("\"\\u0041\"").isValid());
        assertTrue(JsonString.of("\"\\u004F\"").isValid());
        assertTrue(JsonString.of("\"\\u004f\"").isValid());
    }

    @Test
    void jsonStringNumberInnerBranches() {
        assertTrue(JsonString.of("[-0]").isValid());
        assertTrue(JsonString.of("[-123]").isValid());
        assertTrue(JsonString.of("[0.123]").isValid());
        assertTrue(JsonString.of("[123.456]").isValid());
        assertTrue(JsonString.of("[1e10]").isValid());
        assertTrue(JsonString.of("[1E10]").isValid());
        assertTrue(JsonString.of("[1e+10]").isValid());
        assertTrue(JsonString.of("[1e-10]").isValid());
        assertEquals("json-string", JsonString.of("[1.e]").getError().code());
        assertEquals("json-string", JsonString.of("[1e]").getError().code());
        assertEquals("json-string", JsonString.of("[1e+]").getError().code());
    }

    @Test
    void jsonStringNumberAtEndOfString() {
        assertTrue(JsonString.of("0").isValid());
        assertTrue(JsonString.of("-0").isValid());
        assertTrue(JsonString.of("123").isValid());
        assertTrue(JsonString.of("1.5").isValid());
        assertTrue(JsonString.of("1e5").isValid());
    }

    @Test
    void jsonStringNumberFollowedByNonNumeric() {
        assertTrue(JsonString.of("[0,1]").isValid());
        assertTrue(JsonString.of("{\"x\":0}").isValid());
        assertTrue(JsonString.of("[0.5,1]").isValid());
        assertTrue(JsonString.of("[1e2,2]").isValid());
    }

    @Test
    void jsonStringArraySecondEntryFailures() {
        assertEquals("json-string", JsonString.of("[1,").getError().code());
        assertEquals("json-string", JsonString.of("[1,}").getError().code());
    }

    @Test
    void jsonStringStringWithControlChar() {
        assertEquals("json-string", JsonString.of("\"\n\"").getError().code());
        assertEquals("json-string", JsonString.of("\"\u0001\"").getError().code());
    }

    @Test
    void jsonStringUnicodeHexBranches() {
        assertEquals("json-string", JsonString.of("\"\\u 000\"").getError().code());
        assertEquals("json-string", JsonString.of("\"\\u00g0\"").getError().code());
        assertEquals("json-string", JsonString.of("\"\\u00:0\"").getError().code());
    }

    @Test
    void jsonStringNumberNonDigitAfterMinus() {
        assertEquals("json-string", JsonString.of("[-a]").getError().code());
        assertEquals("json-string", JsonString.of("[-.]").getError().code());
    }

    @Test
    void jsonStringNumberNonDigitAfterDecimalPoint() {
        assertEquals("json-string", JsonString.of("[1.a]").getError().code());
        assertEquals("json-string", JsonString.of("[1. ]").getError().code());
    }

    @Test
    void jsonStringNumberNonDigitAfterExponent() {
        assertEquals("json-string", JsonString.of("[1ea]").getError().code());
        assertEquals("json-string", JsonString.of("[1e+a]").getError().code());
        assertEquals("json-string", JsonString.of("[1e ]").getError().code());
        assertEquals("json-string", JsonString.of("[1e+ ]").getError().code());
    }

    @Test
    void jsonStringRejectsDeeplyNestedArraysBeyondLimit() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 513; i++) {
            sb.append('[');
        }
        sb.append("1");
        for (int i = 0; i < 513; i++) {
            sb.append(']');
        }
        assertEquals("json-string", JsonString.of(sb.toString()).getError().code());
    }

    @Test
    void jsonStringRejectsDeeplyNestedObjectsBeyondLimit() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 513; i++) {
            sb.append("{\"a\":");
        }
        sb.append("1");
        for (int i = 0; i < 513; i++) {
            sb.append('}');
        }
        assertEquals("json-string", JsonString.of(sb.toString()).getError().code());
    }

    @Test
    void jsonStringAcceptsDeeplyNestedArraysWithinLimit() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 512; i++) {
            sb.append('[');
        }
        sb.append("1");
        for (int i = 0; i < 512; i++) {
            sb.append(']');
        }
        assertTrue(JsonString.of(sb.toString()).isValid());
    }

    @Test
    void jsonStringAcceptsDeeplyNestedObjectsWithinLimit() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 512; i++) {
            sb.append("{\"a\":");
        }
        sb.append("1");
        for (int i = 0; i < 512; i++) {
            sb.append('}');
        }
        assertTrue(JsonString.of(sb.toString()).isValid());
    }

    @Test
    void jsonStringRejectsMixedDeepNestingBeyondLimit() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 257; i++) {
            sb.append("[{\"a\":");
        }
        sb.append("1");
        for (int i = 0; i < 257; i++) {
            sb.append("}]");
        }
        assertEquals("json-string", JsonString.of(sb.toString()).getError().code());
    }
}
