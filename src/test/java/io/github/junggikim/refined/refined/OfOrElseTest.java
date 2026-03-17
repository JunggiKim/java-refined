package io.github.junggikim.refined.refined;

import static io.github.junggikim.refined.support.TestCollections.listOf;
import static io.github.junggikim.refined.support.TestCollections.mapOf;
import static io.github.junggikim.refined.support.TestCollections.setOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.refined.character.DigitChar;
import io.github.junggikim.refined.refined.collection.NonEmptyDeque;
import io.github.junggikim.refined.refined.collection.NonEmptyIterable;
import io.github.junggikim.refined.refined.collection.NonEmptyList;
import io.github.junggikim.refined.refined.collection.NonEmptyMap;
import io.github.junggikim.refined.refined.collection.NonEmptyNavigableMap;
import io.github.junggikim.refined.refined.collection.NonEmptyNavigableSet;
import io.github.junggikim.refined.refined.collection.NonEmptyQueue;
import io.github.junggikim.refined.refined.collection.NonEmptySet;
import io.github.junggikim.refined.refined.collection.NonEmptySortedMap;
import io.github.junggikim.refined.refined.collection.NonEmptySortedSet;
import io.github.junggikim.refined.refined.numeric.NaturalBigInteger;
import io.github.junggikim.refined.refined.numeric.NegativeBigDecimal;
import io.github.junggikim.refined.refined.numeric.NegativeInt;
import io.github.junggikim.refined.refined.numeric.NonNegativeInt;
import io.github.junggikim.refined.refined.numeric.PositiveInt;
import io.github.junggikim.refined.refined.numeric.PositiveLong;
import io.github.junggikim.refined.refined.string.NonBlankString;
import io.github.junggikim.refined.refined.string.EmailString;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;

class OfOrElseTest {

    // --- Numeric types ---

    @Test
    void positiveIntOfOrElseReturnsValueWhenValid() {
        assertEquals(5, PositiveInt.ofOrElse(5, 1).value());
    }

    @Test
    void positiveIntOfOrElseReturnsFallbackWhenInvalid() {
        assertEquals(1, PositiveInt.ofOrElse(0, 1).value());
    }

    @Test
    void positiveIntOfOrElseReturnsFallbackWhenNull() {
        assertEquals(1, PositiveInt.ofOrElse(null, 1).value());
    }

    @Test
    void positiveIntOfOrElseThrowsWhenDefaultAlsoInvalid() {
        assertThrows(RefinementException.class, () -> PositiveInt.ofOrElse(0, 0));
    }

    @Test
    void positiveLongOfOrElseReturnsValueWhenValid() {
        assertEquals(5L, PositiveLong.ofOrElse(5L, 1L).value());
    }

    @Test
    void positiveLongOfOrElseReturnsFallbackWhenInvalid() {
        assertEquals(1L, PositiveLong.ofOrElse(0L, 1L).value());
    }

    @Test
    void nonNegativeIntOfOrElseReturnsValueWhenValid() {
        assertEquals(0, NonNegativeInt.ofOrElse(0, 1).value());
    }

    @Test
    void nonNegativeIntOfOrElseReturnsFallbackWhenInvalid() {
        assertEquals(1, NonNegativeInt.ofOrElse(-1, 1).value());
    }

    @Test
    void negativeIntOfOrElseReturnsValueWhenValid() {
        assertEquals(-5, NegativeInt.ofOrElse(-5, -1).value());
    }

    @Test
    void negativeIntOfOrElseReturnsFallbackWhenInvalid() {
        assertEquals(-1, NegativeInt.ofOrElse(0, -1).value());
    }

    @Test
    void naturalBigIntegerOfOrElseReturnsValueWhenValid() {
        assertEquals(BigInteger.TEN, NaturalBigInteger.ofOrElse(BigInteger.TEN, BigInteger.ZERO).value());
    }

    @Test
    void naturalBigIntegerOfOrElseReturnsFallbackWhenNull() {
        assertEquals(BigInteger.ZERO, NaturalBigInteger.ofOrElse(null, BigInteger.ZERO).value());
    }

    @Test
    void negativeBigDecimalOfOrElseReturnsValueWhenValid() {
        BigDecimal neg = new BigDecimal("-5.0");
        BigDecimal def = new BigDecimal("-1.0");
        assertEquals(neg, NegativeBigDecimal.ofOrElse(neg, def).value());
    }

    @Test
    void negativeBigDecimalOfOrElseReturnsFallbackWhenInvalid() {
        BigDecimal def = new BigDecimal("-1.0");
        assertEquals(def, NegativeBigDecimal.ofOrElse(BigDecimal.ZERO, def).value());
    }

    // --- Character types ---

    @Test
    void digitCharOfOrElseReturnsValueWhenValid() {
        assertEquals('5', DigitChar.ofOrElse('5', '0').value());
    }

    @Test
    void digitCharOfOrElseReturnsFallbackWhenInvalid() {
        assertEquals('0', DigitChar.ofOrElse('a', '0').value());
    }

    @Test
    void digitCharOfOrElseReturnsFallbackWhenNull() {
        assertEquals('0', DigitChar.ofOrElse(null, '0').value());
    }

    @Test
    void digitCharOfOrElseThrowsWhenDefaultAlsoInvalid() {
        assertThrows(RefinementException.class, () -> DigitChar.ofOrElse('a', 'a'));
    }

    // --- String types ---

    @Test
    void nonBlankStringOfOrElseReturnsValueWhenValid() {
        assertEquals("hello", NonBlankString.ofOrElse("hello", "default").value());
    }

    @Test
    void nonBlankStringOfOrElseReturnsFallbackWhenInvalid() {
        assertEquals("default", NonBlankString.ofOrElse("   ", "default").value());
    }

    @Test
    void nonBlankStringOfOrElseReturnsFallbackWhenNull() {
        assertEquals("default", NonBlankString.ofOrElse(null, "default").value());
    }

    @Test
    void nonBlankStringOfOrElseThrowsWhenDefaultAlsoInvalid() {
        assertThrows(RefinementException.class, () -> NonBlankString.ofOrElse("   ", "   "));
    }

    @Test
    void emailStringOfOrElseReturnsValueWhenValid() {
        assertEquals("a@b.com", EmailString.ofOrElse("a@b.com", "x@y.com").value());
    }

    @Test
    void emailStringOfOrElseReturnsFallbackWhenInvalid() {
        assertEquals("x@y.com", EmailString.ofOrElse("invalid", "x@y.com").value());
    }

    // --- Collection types ---

    @Test
    void nonEmptyListOfOrElseReturnsValueWhenValid() {
        assertEquals(listOf(1, 2), NonEmptyList.ofOrElse(listOf(1, 2), listOf(9)));
    }

    @Test
    void nonEmptyListOfOrElseReturnsFallbackWhenInvalid() {
        List<Integer> empty = listOf();
        assertEquals(listOf(9), NonEmptyList.ofOrElse(empty, listOf(9)));
    }

    @Test
    void nonEmptyListOfOrElseReturnsFallbackWhenNull() {
        assertEquals(listOf(9), NonEmptyList.ofOrElse(null, listOf(9)));
    }

    @Test
    void nonEmptyListOfOrElseThrowsWhenDefaultAlsoInvalid() {
        List<Integer> empty = listOf();
        assertThrows(RefinementException.class, () -> NonEmptyList.ofOrElse(empty, listOf()));
    }

    @Test
    void nonEmptySetOfOrElseReturnsValueWhenValid() {
        Set<Integer> input = new LinkedHashSet<Integer>(listOf(1, 2));
        Set<Integer> fallback = new LinkedHashSet<Integer>(listOf(9));

        assertEquals(setOf(1, 2), NonEmptySet.ofOrElse(input, fallback));
    }

    @Test
    void nonEmptySetOfOrElseReturnsFallbackWhenNull() {
        Set<Integer> fallback = new LinkedHashSet<Integer>(listOf(9));

        assertEquals(setOf(9), NonEmptySet.ofOrElse(null, fallback));
    }

    @Test
    void nonEmptyMapOfOrElseReturnsValueWhenValid() {
        Map<String, Integer> input = new LinkedHashMap<String, Integer>(mapOf("a", 1));
        Map<String, Integer> fallback = new LinkedHashMap<String, Integer>(mapOf("b", 2));

        assertEquals(mapOf("a", 1), NonEmptyMap.ofOrElse(input, fallback));
    }

    @Test
    void nonEmptyMapOfOrElseReturnsFallbackWhenNull() {
        Map<String, Integer> fallback = new LinkedHashMap<String, Integer>(mapOf("b", 2));

        assertEquals(mapOf("b", 2), NonEmptyMap.ofOrElse(null, fallback));
    }

    @Test
    void nonEmptyQueueOfOrElseReturnsValueWhenValid() {
        Queue<Integer> input = new LinkedList<Integer>(listOf(1, 2));
        Queue<Integer> fallback = new LinkedList<Integer>(listOf(9));

        assertEquals(listOf(1, 2), NonEmptyQueue.ofOrElse(input, fallback));
    }

    @Test
    void nonEmptyQueueOfOrElseReturnsFallbackWhenEmpty() {
        Queue<Integer> empty = new LinkedList<Integer>();
        Queue<Integer> fallback = new LinkedList<Integer>(listOf(9));

        assertEquals(listOf(9), NonEmptyQueue.ofOrElse(empty, fallback));
    }

    @Test
    void nonEmptyDequeOfOrElseReturnsValueWhenValid() {
        Deque<Integer> input = new ArrayDeque<Integer>(listOf(1, 2));
        Deque<Integer> fallback = new ArrayDeque<Integer>(listOf(9));

        assertEquals(listOf(1, 2), NonEmptyDeque.ofOrElse(input, fallback));
    }

    @Test
    void nonEmptyDequeOfOrElseReturnsFallbackWhenNull() {
        Deque<Integer> fallback = new ArrayDeque<Integer>(listOf(9));

        assertEquals(listOf(9), NonEmptyDeque.ofOrElse(null, fallback));
    }

    @Test
    void nonEmptyIterableOfOrElseReturnsValueWhenValid() {
        assertEquals(listOf(1), NonEmptyIterable.ofOrElse(listOf(1), listOf(9)));
    }

    @Test
    void nonEmptyIterableOfOrElseReturnsFallbackWhenEmpty() {
        List<Integer> empty = listOf();
        assertEquals(listOf(9), NonEmptyIterable.ofOrElse(empty, listOf(9)));
    }

    @Test
    void nonEmptySortedSetOfOrElseReturnsValueWhenValid() {
        SortedSet<Integer> input = new TreeSet<Integer>(listOf(1, 2));
        SortedSet<Integer> fallback = new TreeSet<Integer>(listOf(9));

        assertEquals(setOf(1, 2), NonEmptySortedSet.ofOrElse(input, fallback));
    }

    @Test
    void nonEmptySortedSetOfOrElseReturnsFallbackWhenNull() {
        SortedSet<Integer> fallback = new TreeSet<Integer>(listOf(9));

        assertEquals(setOf(9), NonEmptySortedSet.ofOrElse(null, fallback));
    }

    @Test
    void nonEmptySortedMapOfOrElseReturnsValueWhenValid() {
        SortedMap<String, Integer> input = new TreeMap<String, Integer>(mapOf("a", 1));
        SortedMap<String, Integer> fallback = new TreeMap<String, Integer>(mapOf("b", 2));

        assertEquals(mapOf("a", 1), NonEmptySortedMap.ofOrElse(input, fallback));
    }

    @Test
    void nonEmptySortedMapOfOrElseReturnsFallbackWhenNull() {
        SortedMap<String, Integer> fallback = new TreeMap<String, Integer>(mapOf("b", 2));

        assertEquals(mapOf("b", 2), NonEmptySortedMap.ofOrElse(null, fallback));
    }

    @Test
    void nonEmptyNavigableSetOfOrElseReturnsValueWhenValid() {
        NavigableSet<Integer> input = new TreeSet<Integer>(listOf(1, 2));
        NavigableSet<Integer> fallback = new TreeSet<Integer>(listOf(9));

        assertEquals(setOf(1, 2), NonEmptyNavigableSet.ofOrElse(input, fallback));
    }

    @Test
    void nonEmptyNavigableSetOfOrElseReturnsFallbackWhenNull() {
        NavigableSet<Integer> fallback = new TreeSet<Integer>(listOf(9));

        assertEquals(setOf(9), NonEmptyNavigableSet.ofOrElse(null, fallback));
    }

    @Test
    void nonEmptyNavigableMapOfOrElseReturnsValueWhenValid() {
        NavigableMap<String, Integer> input = new TreeMap<String, Integer>(mapOf("a", 1));
        NavigableMap<String, Integer> fallback = new TreeMap<String, Integer>(mapOf("b", 2));

        assertEquals(mapOf("a", 1), NonEmptyNavigableMap.ofOrElse(input, fallback));
    }

    @Test
    void nonEmptyNavigableMapOfOrElseReturnsFallbackWhenNull() {
        NavigableMap<String, Integer> fallback = new TreeMap<String, Integer>(mapOf("b", 2));

        assertEquals(mapOf("b", 2), NonEmptyNavigableMap.ofOrElse(null, fallback));
    }
}
