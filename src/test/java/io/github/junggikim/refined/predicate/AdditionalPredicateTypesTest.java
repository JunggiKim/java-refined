package io.github.junggikim.refined.predicate;

import static io.github.junggikim.refined.support.TestCollections.listOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.predicate.booleanvalue.And;
import io.github.junggikim.refined.predicate.booleanvalue.FalseValue;
import io.github.junggikim.refined.predicate.booleanvalue.Nand;
import io.github.junggikim.refined.predicate.booleanvalue.Nor;
import io.github.junggikim.refined.predicate.booleanvalue.OneOf;
import io.github.junggikim.refined.predicate.booleanvalue.Or;
import io.github.junggikim.refined.predicate.booleanvalue.TrueValue;
import io.github.junggikim.refined.predicate.booleanvalue.Xor;
import io.github.junggikim.refined.predicate.character.IsDigitChar;
import io.github.junggikim.refined.predicate.character.IsLetterChar;
import io.github.junggikim.refined.predicate.character.IsLetterOrDigitChar;
import io.github.junggikim.refined.predicate.character.IsLowerCaseChar;
import io.github.junggikim.refined.predicate.character.IsUpperCaseChar;
import io.github.junggikim.refined.predicate.character.IsWhitespaceChar;
import io.github.junggikim.refined.predicate.collection.ContainsElement;
import io.github.junggikim.refined.predicate.collection.CountMatches;
import io.github.junggikim.refined.predicate.collection.EmptyCollection;
import io.github.junggikim.refined.predicate.collection.ExistsElement;
import io.github.junggikim.refined.predicate.collection.ForAllElements;
import io.github.junggikim.refined.predicate.collection.HeadSatisfies;
import io.github.junggikim.refined.predicate.collection.IndexSatisfies;
import io.github.junggikim.refined.predicate.collection.InitSatisfies;
import io.github.junggikim.refined.predicate.collection.LastSatisfies;
import io.github.junggikim.refined.predicate.collection.SizeEqual;
import io.github.junggikim.refined.predicate.collection.TailSatisfies;
import io.github.junggikim.refined.predicate.numeric.ClosedInterval;
import io.github.junggikim.refined.predicate.numeric.ClosedOpenInterval;
import io.github.junggikim.refined.predicate.numeric.DivisibleByBigInteger;
import io.github.junggikim.refined.predicate.numeric.DivisibleByInt;
import io.github.junggikim.refined.predicate.numeric.DivisibleByLong;
import io.github.junggikim.refined.predicate.numeric.EvenBigInteger;
import io.github.junggikim.refined.predicate.numeric.EvenInt;
import io.github.junggikim.refined.predicate.numeric.EvenLong;
import io.github.junggikim.refined.predicate.numeric.FiniteDoublePredicate;
import io.github.junggikim.refined.predicate.numeric.FiniteFloatPredicate;
import io.github.junggikim.refined.predicate.numeric.NonNaNDoublePredicate;
import io.github.junggikim.refined.predicate.numeric.NonNaNFloatPredicate;
import io.github.junggikim.refined.predicate.numeric.OddBigInteger;
import io.github.junggikim.refined.predicate.numeric.OddInt;
import io.github.junggikim.refined.predicate.numeric.OddLong;
import io.github.junggikim.refined.predicate.numeric.OpenClosedInterval;
import io.github.junggikim.refined.predicate.numeric.OpenInterval;
import io.github.junggikim.refined.predicate.logical.AllOf;
import io.github.junggikim.refined.predicate.string.NotBlank;
import io.github.junggikim.refined.predicate.string.NotEmpty;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class AdditionalPredicateTypesTest {

    @TestFactory
    Stream<DynamicTest> booleanPredicatesValidateTruthTables() {
        return Stream.of(
            booleanTest("TrueValue", new TrueValue(), true, false, "true-value"),
            booleanTest("FalseValue", new FalseValue(), false, true, "false-value"),
            booleanListTest("And", new And(), listOf(true, true), listOf(true, false), "and"),
            booleanListTest("Or", new Or(), listOf(false, true), listOf(false, false), "or"),
            booleanListTest("Xor", new Xor(), listOf(true, false, false), listOf(true, true, false), "xor"),
            booleanListTest("Nand", new Nand(), listOf(true, false), listOf(true, true), "nand"),
            booleanListTest("Nor", new Nor(), listOf(false, false), listOf(false, true), "nor"),
            booleanListTest("OneOf", new OneOf(), listOf(false, true, false), listOf(true, true, false), "one-of")
        ).flatMap(it -> it);
    }

    @TestFactory
    Stream<DynamicTest> characterPredicatesValidateCharacters() {
        return Stream.of(
            characterTest("IsDigitChar", new IsDigitChar(), '1', 'a', "digit-char"),
            characterTest("IsLetterChar", new IsLetterChar(), 'a', '1', "letter-char"),
            characterTest("IsLetterOrDigitChar", new IsLetterOrDigitChar(), '1', '-', "letter-or-digit-char"),
            characterTest("IsLowerCaseChar", new IsLowerCaseChar(), 'a', 'A', "lower-case-char"),
            characterTest("IsUpperCaseChar", new IsUpperCaseChar(), 'A', 'a', "upper-case-char"),
            characterTest("IsWhitespaceChar", new IsWhitespaceChar(), ' ', 'a', "whitespace-char")
        ).flatMap(it -> it);
    }

    @Test
    void intervalPredicatesRespectOpenAndClosedBounds() {
        assertEquals(3, new OpenInterval<>(1, 5).validate(3).get());
        assertEquals("open-interval", new OpenInterval<>(1, 5).validate(1).getError().code());
        assertEquals("open-interval", new OpenInterval<>(1, 5).validate(5).getError().code());

        assertEquals(1, new ClosedInterval<>(1, 5).validate(1).get());
        assertEquals(5, new ClosedInterval<>(1, 5).validate(5).get());
        assertEquals("closed-interval", new ClosedInterval<>(1, 5).validate(0).getError().code());
        assertEquals("closed-interval", new ClosedInterval<>(1, 5).validate(6).getError().code());

        assertEquals(5, new OpenClosedInterval<>(1, 5).validate(5).get());
        assertEquals("open-closed-interval", new OpenClosedInterval<>(1, 5).validate(1).getError().code());
        assertEquals("open-closed-interval", new OpenClosedInterval<>(1, 5).validate(6).getError().code());

        assertEquals(1, new ClosedOpenInterval<>(1, 5).validate(1).get());
        assertEquals("closed-open-interval", new ClosedOpenInterval<>(1, 5).validate(0).getError().code());
        assertEquals("closed-open-interval", new ClosedOpenInterval<>(1, 5).validate(5).getError().code());
        assertThrows(IllegalArgumentException.class, () -> new OpenInterval<>(5, 5));
        assertThrows(IllegalArgumentException.class, () -> new ClosedInterval<>(10, 5));
        assertThrows(IllegalArgumentException.class, () -> new OpenClosedInterval<>(5, 5));
        assertThrows(IllegalArgumentException.class, () -> new ClosedOpenInterval<>(5, 5));
    }

    @Test
    void parityAndDivisibilityPredicatesValidateNumbers() {
        assertEquals(4, new EvenInt().validate(4).get());
        assertEquals("even-int", new EvenInt().validate(3).getError().code());
        assertEquals(3, new OddInt().validate(3).get());
        assertEquals("odd-int", new OddInt().validate(4).getError().code());

        assertEquals(4L, new EvenLong().validate(4L).get());
        assertEquals("even-long", new EvenLong().validate(3L).getError().code());
        assertEquals(3L, new OddLong().validate(3L).get());
        assertEquals("odd-long", new OddLong().validate(4L).getError().code());

        assertEquals(BigInteger.valueOf(4), new EvenBigInteger().validate(BigInteger.valueOf(4)).get());
        assertEquals("even-big-integer", new EvenBigInteger().validate(BigInteger.valueOf(3)).getError().code());
        assertEquals(BigInteger.valueOf(3), new OddBigInteger().validate(BigInteger.valueOf(3)).get());
        assertEquals("odd-big-integer", new OddBigInteger().validate(BigInteger.valueOf(4)).getError().code());

        assertEquals(6, new DivisibleByInt(3).validate(6).get());
        assertEquals("divisible-by-int", new DivisibleByInt(3).validate(5).getError().code());
        assertEquals(6L, new DivisibleByLong(3).validate(6L).get());
        assertEquals("divisible-by-long", new DivisibleByLong(3).validate(5L).getError().code());
        assertEquals(BigInteger.valueOf(6), new DivisibleByBigInteger(BigInteger.valueOf(3)).validate(BigInteger.valueOf(6)).get());
        assertEquals("divisible-by-big-integer", new DivisibleByBigInteger(BigInteger.valueOf(3)).validate(BigInteger.valueOf(5)).getError().code());

        assertThrows(IllegalArgumentException.class, () -> new DivisibleByInt(0));
        assertThrows(IllegalArgumentException.class, () -> new DivisibleByLong(0));
        assertThrows(IllegalArgumentException.class, () -> new DivisibleByBigInteger(null));
        assertThrows(IllegalArgumentException.class, () -> new DivisibleByBigInteger(BigInteger.ZERO));
    }

    @Test
    void floatingPointPredicatesHandleNaNAndInfinity() {
        assertEquals(1.0f, new FiniteFloatPredicate().validate(1.0f).get());
        assertEquals("finite-float", new FiniteFloatPredicate().validate(Float.POSITIVE_INFINITY).getError().code());
        assertEquals(Float.POSITIVE_INFINITY, new NonNaNFloatPredicate().validate(Float.POSITIVE_INFINITY).get());
        assertEquals("non-nan-float", new NonNaNFloatPredicate().validate(Float.NaN).getError().code());

        assertEquals(1.0d, new FiniteDoublePredicate().validate(1.0d).get());
        assertEquals("finite-double", new FiniteDoublePredicate().validate(Double.POSITIVE_INFINITY).getError().code());
        assertEquals(Double.POSITIVE_INFINITY, new NonNaNDoublePredicate().validate(Double.POSITIVE_INFINITY).get());
        assertEquals("non-nan-double", new NonNaNDoublePredicate().validate(Double.NaN).getError().code());
    }

    @Test
    void collectionPredicatesValidateShapeAndContent() {
        assertEquals(listOf(1, 2), new ContainsElement<Integer>(2).validate(listOf(1, 2)).get());
        assertEquals("contains-element", new ContainsElement<Integer>(2).validate(listOf(1)).getError().code());

        assertEquals(listOf(), new EmptyCollection<List<Integer>>().validate(listOf()).get());
        assertEquals("empty-collection", new EmptyCollection<List<Integer>>().validate(listOf(1)).getError().code());

        assertEquals(listOf(2, 4), new ForAllElements<Integer>(value -> value % 2 == 0).validate(listOf(2, 4)).get());
        assertEquals("for-all-elements", new ForAllElements<Integer>(value -> value % 2 == 0).validate(listOf(2, 3)).getError().code());

        assertEquals(listOf(1, 3), new ExistsElement<Integer>(value -> value % 2 != 0).validate(listOf(1, 3)).get());
        assertEquals("exists-element", new ExistsElement<Integer>(value -> value % 2 != 0).validate(listOf(2, 4)).getError().code());

        assertEquals(listOf(1, 2, 3), new HeadSatisfies<Integer>(value -> value == 1).validate(listOf(1, 2, 3)).get());
        assertEquals("head-satisfies", new HeadSatisfies<Integer>(value -> value == 1).validate(listOf(2, 3)).getError().code());
        assertEquals("head-satisfies", new HeadSatisfies<Integer>(value -> value == 1).validate(listOf()).getError().code());

        assertEquals(listOf(1, 2, 3), new LastSatisfies<Integer>(value -> value == 3).validate(listOf(1, 2, 3)).get());
        assertEquals("last-satisfies", new LastSatisfies<Integer>(value -> value == 3).validate(listOf(1, 2)).getError().code());
        assertEquals("last-satisfies", new LastSatisfies<Integer>(value -> value == 3).validate(listOf()).getError().code());

        assertEquals(listOf(1, 2, 3), new IndexSatisfies<Integer>(1, value -> value == 2).validate(listOf(1, 2, 3)).get());
        assertEquals("index-satisfies", new IndexSatisfies<Integer>(1, value -> value == 2).validate(listOf(1)).getError().code());
        assertEquals("index-satisfies", new IndexSatisfies<Integer>(-1, value -> value == 2).validate(listOf(1, 2, 3)).getError().code());
        assertEquals("index-satisfies", new IndexSatisfies<Integer>(1, value -> value == 2).validate(listOf(1, 3)).getError().code());

        assertEquals(listOf(1, 2, 3), new InitSatisfies<Integer>(values -> values.equals(listOf(1, 2))).validate(listOf(1, 2, 3)).get());
        assertEquals("init-satisfies", new InitSatisfies<Integer>(values -> values.equals(listOf(1, 2))).validate(listOf(1, 3)).getError().code());
        assertEquals("init-satisfies", new InitSatisfies<Integer>(values -> values.equals(listOf(1, 2))).validate(listOf()).getError().code());

        assertEquals(listOf(1, 2, 3), new TailSatisfies<Integer>(values -> values.equals(listOf(2, 3))).validate(listOf(1, 2, 3)).get());
        assertEquals("tail-satisfies", new TailSatisfies<Integer>(values -> values.equals(listOf(2, 3))).validate(listOf(1, 2)).getError().code());
        assertEquals("tail-satisfies", new TailSatisfies<Integer>(values -> values.equals(listOf(2, 3))).validate(listOf()).getError().code());

        assertEquals(listOf(1, 2), new SizeEqual<List<Integer>>(2).validate(listOf(1, 2)).get());
        assertEquals("size-equal", new SizeEqual<List<Integer>>(2).validate(listOf(1)).getError().code());

        assertEquals(listOf(1, 2, 3), new CountMatches<Integer>(value -> value % 2 != 0, 2).validate(listOf(1, 2, 3)).get());
        assertEquals("count-matches", new CountMatches<Integer>(value -> value % 2 != 0, 2).validate(listOf(1, 2, 4)).getError().code());
    }

    @Test
    void collectionPredicateConstructorsRejectNullPredicates() {
        assertThrows(NullPointerException.class, () -> new ForAllElements<Integer>((java.util.function.Predicate<Integer>) null));
        assertThrows(NullPointerException.class, () -> new ExistsElement<Integer>((java.util.function.Predicate<Integer>) null));
        assertThrows(NullPointerException.class, () -> new CountMatches<Integer>((java.util.function.Predicate<Integer>) null, 1));
        assertThrows(NullPointerException.class, () -> new HeadSatisfies<Integer>((java.util.function.Predicate<Integer>) null));
        assertThrows(NullPointerException.class, () -> new IndexSatisfies<Integer>(0, (java.util.function.Predicate<Integer>) null));
        assertThrows(NullPointerException.class, () -> new InitSatisfies<Integer>((java.util.function.Predicate<List<Integer>>) null));
        assertThrows(NullPointerException.class, () -> new LastSatisfies<Integer>((java.util.function.Predicate<Integer>) null));
        assertThrows(NullPointerException.class, () -> new TailSatisfies<Integer>((java.util.function.Predicate<List<Integer>>) null));
    }

    @Test
    void allOfPreservesFirstFailureViolation() {
        AllOf<String> predicate = new AllOf<String>(listOf(new NotEmpty(), new NotBlank()));

        assertEquals("not-empty", predicate.validate("").getError().code());
        assertEquals("not-blank", predicate.validate(" ").getError().code());
    }

    private Stream<DynamicTest> booleanTest(
        String displayName,
        Constraint<Boolean> predicate,
        Boolean valid,
        Boolean invalid,
        String code
    ) {
        return Stream.of(
            DynamicTest.dynamicTest(displayName + " accepts valid values", () -> assertEquals(valid, predicate.validate(valid).get())),
            DynamicTest.dynamicTest(displayName + " rejects invalid values", () -> assertEquals(code, predicate.validate(invalid).getError().code())),
            DynamicTest.dynamicTest(displayName + " rejects null", () -> assertEquals(code, predicate.validate(null).getError().code()))
        );
    }

    private Stream<DynamicTest> booleanListTest(
        String displayName,
        Constraint<List<Boolean>> predicate,
        List<Boolean> valid,
        List<Boolean> invalid,
        String code
    ) {
        return Stream.of(
            DynamicTest.dynamicTest(displayName + " accepts valid values", () -> assertEquals(valid, predicate.validate(valid).get())),
            DynamicTest.dynamicTest(displayName + " rejects invalid values", () -> assertEquals(code, predicate.validate(invalid).getError().code())),
            DynamicTest.dynamicTest(displayName + " rejects null", () -> assertEquals(code, predicate.validate(null).getError().code()))
        );
    }

    private Stream<DynamicTest> characterTest(
        String displayName,
        Constraint<Character> predicate,
        Character valid,
        Character invalid,
        String code
    ) {
        return Stream.of(
            DynamicTest.dynamicTest(displayName + " accepts valid values", () -> assertEquals(valid, predicate.validate(valid).get())),
            DynamicTest.dynamicTest(displayName + " rejects invalid values", () -> assertEquals(code, predicate.validate(invalid).getError().code())),
            DynamicTest.dynamicTest(displayName + " rejects null", () -> assertEquals(code, predicate.validate(null).getError().code()))
        );
    }

    @Test
    void indexSatisfiesValidatesAtIndexZero() {
        Constraint<List<Integer>> c = new IndexSatisfies<>(0, v -> v == 1);
        assertEquals(listOf(1, 2, 3), c.validate(listOf(1, 2, 3)).get());
        assertEquals("index-satisfies", c.validate(listOf(2, 1, 3)).getError().code());
    }
}
