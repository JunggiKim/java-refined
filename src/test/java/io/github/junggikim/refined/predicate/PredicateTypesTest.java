package io.github.junggikim.refined.predicate;

import static io.github.junggikim.refined.support.TestCollections.listOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.predicate.collection.MaxSize;
import io.github.junggikim.refined.predicate.collection.MinSize;
import io.github.junggikim.refined.predicate.collection.SizeBetween;
import io.github.junggikim.refined.predicate.logical.AllOf;
import io.github.junggikim.refined.predicate.logical.AnyOf;
import io.github.junggikim.refined.predicate.logical.Not;
import io.github.junggikim.refined.predicate.numeric.EqualTo;
import io.github.junggikim.refined.predicate.numeric.GreaterOrEqual;
import io.github.junggikim.refined.predicate.numeric.GreaterThan;
import io.github.junggikim.refined.predicate.numeric.LessOrEqual;
import io.github.junggikim.refined.predicate.numeric.LessThan;
import io.github.junggikim.refined.predicate.numeric.NotEqualTo;
import io.github.junggikim.refined.predicate.string.Contains;
import io.github.junggikim.refined.predicate.string.EndsWith;
import io.github.junggikim.refined.predicate.string.LengthAtLeast;
import io.github.junggikim.refined.predicate.string.LengthAtMost;
import io.github.junggikim.refined.predicate.string.LengthBetween;
import io.github.junggikim.refined.predicate.string.MatchesRegex;
import io.github.junggikim.refined.predicate.string.NotBlank;
import io.github.junggikim.refined.predicate.string.NotEmpty;
import io.github.junggikim.refined.predicate.string.StartsWith;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class PredicateTypesTest {

    @TestFactory
    Stream<DynamicTest> numericPredicatesValidateBoundaries() {
        return Stream.of(
            numericTest("GreaterThan", new GreaterThan<>(1), 2, 1, "greater-than"),
            numericTest("GreaterOrEqual", new GreaterOrEqual<>(1), 1, 0, "greater-or-equal"),
            numericTest("LessThan", new LessThan<>(1), 0, 1, "less-than"),
            numericTest("LessOrEqual", new LessOrEqual<>(1), 1, 2, "less-or-equal"),
            numericTest("EqualTo", new EqualTo<>(1), 1, 2, "equal-to"),
            numericTest("NotEqualTo", new NotEqualTo<>(1), 2, 1, "not-equal-to")
        ).flatMap(it -> it);
    }

    @TestFactory
    Stream<DynamicTest> stringPredicatesValidateContent() {
        return Stream.of(
            stringTest("NotEmpty", new NotEmpty(), "a", "", "not-empty"),
            stringTest("NotBlank", new NotBlank(), "a", " ", "not-blank"),
            stringTest("LengthAtLeast", new LengthAtLeast(2), "ab", "a", "length-at-least"),
            stringTest("LengthAtMost", new LengthAtMost(2), "ab", "abc", "length-at-most"),
            stringTest("MatchesRegex", new MatchesRegex("\\d+"), "12", "ab", "matches-regex"),
            stringTest("StartsWith", new StartsWith("pre"), "prefix", "suffix", "starts-with"),
            stringTest("EndsWith", new EndsWith("fix"), "suffix", "prefixed", "ends-with"),
            stringTest("Contains", new Contains("mid"), "in-the-middle", "outside", "contains")
        ).flatMap(it -> it);
    }

    @Test
    void lengthBetweenCoversLowerAndUpperBounds() {
        Constraint<String> predicate = new LengthBetween(2, 4);

        assertEquals("abc", predicate.validate("abc").get());
        assertEquals("length-between", predicate.validate("a").getError().code());
        assertEquals("length-between", predicate.validate("abcde").getError().code());
    }

    @Test
    void lengthBetweenRejectsInvalidBounds() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> new LengthBetween(4, 2));
        assertEquals("length-between minimum must be less than or equal to maximum", thrown.getMessage());
    }

    @Test
    void collectionPredicatesValidateSizes() {
        assertEquals(listOf(1, 2), new MinSize<List<Integer>>(2).validate(listOf(1, 2)).get());
        assertEquals("min-size", new MinSize<List<Integer>>(2).validate(listOf(1)).getError().code());

        assertEquals(listOf(1, 2), new MaxSize<List<Integer>>(2).validate(listOf(1, 2)).get());
        assertEquals("max-size", new MaxSize<List<Integer>>(2).validate(listOf(1, 2, 3)).getError().code());

        assertEquals(listOf(1, 2), new SizeBetween<List<Integer>>(1, 3).validate(listOf(1, 2)).get());
        assertEquals("size-between", new SizeBetween<List<Integer>>(1, 3).validate(listOf()).getError().code());
        assertEquals("size-between", new SizeBetween<List<Integer>>(1, 3).validate(listOf(1, 2, 3, 4)).getError().code());
    }

    @Test
    void sizeBetweenRejectsInvalidBounds() {
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> new SizeBetween<List<Integer>>(3, 1)
        );
        assertEquals("size-between minimum must be less than or equal to maximum", thrown.getMessage());
    }

    @Test
    void logicalPredicatesComposeConstraints() {
        Constraint<String> allOf = new AllOf<String>(listOf(new NotBlank(), new StartsWith("ab")));
        Constraint<String> anyOf = new AnyOf<String>(listOf(new StartsWith("pre"), new EndsWith("post")));
        Constraint<String> not = new Not<>(new NotBlank());

        assertEquals("abc", allOf.validate("abc").get());
        assertEquals("starts-with", allOf.validate("xbc").getError().code());

        assertEquals("the-post", anyOf.validate("the-post").get());
        assertEquals("any-of", anyOf.validate("middle").getError().code());

        assertEquals(" ", not.validate(" ").get());
        assertEquals("not", not.validate("text").getError().code());
    }

    @Test
    void logicalPredicatesRejectNullInput() {
        assertEquals("all-of", new AllOf<String>(listOf(new NotBlank())).validate(null).getError().code());
        assertEquals("any-of", new AnyOf<String>(listOf(new NotBlank())).validate(null).getError().code());
        assertEquals("not", new Not<>(new NotBlank()).validate(null).getError().code());
    }

    private Stream<DynamicTest> numericTest(
        String displayName,
        Constraint<Integer> predicate,
        Integer valid,
        Integer invalid,
        String code
    ) {
        return Stream.of(
            DynamicTest.dynamicTest(displayName + " accepts valid values", () -> assertEquals(valid, predicate.validate(valid).get())),
            DynamicTest.dynamicTest(displayName + " rejects invalid values", () -> assertEquals(code, predicate.validate(invalid).getError().code())),
            DynamicTest.dynamicTest(displayName + " rejects null", () -> assertEquals(code, predicate.validate(null).getError().code()))
        );
    }

    private Stream<DynamicTest> stringTest(
        String displayName,
        Constraint<String> predicate,
        String valid,
        String invalid,
        String code
    ) {
        return Stream.of(
            DynamicTest.dynamicTest(displayName + " accepts valid values", () -> assertEquals(valid, predicate.validate(valid).get())),
            DynamicTest.dynamicTest(displayName + " rejects invalid values", () -> assertEquals(code, predicate.validate(invalid).getError().code())),
            DynamicTest.dynamicTest(displayName + " rejects null", () -> assertEquals(code, predicate.validate(null).getError().code()))
        );
    }
}
