package io.github.junggikim.refined.predicate;

import static io.github.junggikim.refined.support.TestCollections.listOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.junggikim.refined.predicate.character.IsSpecialChar;
import io.github.junggikim.refined.predicate.collection.AscendingList;
import io.github.junggikim.refined.predicate.collection.DescendingList;
import io.github.junggikim.refined.predicate.numeric.ModuloInt;
import io.github.junggikim.refined.predicate.numeric.ModuloLong;
import io.github.junggikim.refined.predicate.numeric.NonDivisibleByBigInteger;
import io.github.junggikim.refined.predicate.numeric.NonDivisibleByInt;
import io.github.junggikim.refined.predicate.numeric.NonDivisibleByLong;
import java.math.BigInteger;
import java.util.List;
import org.junit.jupiter.api.Test;

class NewPredicateTypesTest {

    @Test
    void moduloIntValidatesRemainder() {
        assertEquals(7, new ModuloInt(3, 1).validate(7).get());
        assertEquals("modulo-int", new ModuloInt(3, 1).validate(8).getError().code());
        assertEquals("modulo-int", new ModuloInt(3, 1).validate(null).getError().code());
        assertThrows(IllegalArgumentException.class, () -> new ModuloInt(0, 0));
    }

    @Test
    void moduloLongValidatesRemainder() {
        assertEquals(7L, new ModuloLong(3, 1).validate(7L).get());
        assertEquals("modulo-long", new ModuloLong(3, 1).validate(8L).getError().code());
        assertEquals("modulo-long", new ModuloLong(3, 1).validate(null).getError().code());
        assertThrows(IllegalArgumentException.class, () -> new ModuloLong(0, 0));
    }

    @Test
    void nonDivisibleByIntRejectsMultiples() {
        assertEquals(5, new NonDivisibleByInt(3).validate(5).get());
        assertEquals("non-divisible-by-int", new NonDivisibleByInt(3).validate(6).getError().code());
        assertEquals("non-divisible-by-int", new NonDivisibleByInt(3).validate(null).getError().code());
        assertThrows(IllegalArgumentException.class, () -> new NonDivisibleByInt(0));
    }

    @Test
    void nonDivisibleByLongRejectsMultiples() {
        assertEquals(5L, new NonDivisibleByLong(3).validate(5L).get());
        assertEquals("non-divisible-by-long", new NonDivisibleByLong(3).validate(6L).getError().code());
        assertEquals("non-divisible-by-long", new NonDivisibleByLong(3).validate(null).getError().code());
        assertThrows(IllegalArgumentException.class, () -> new NonDivisibleByLong(0));
    }

    @Test
    void nonDivisibleByBigIntegerRejectsMultiples() {
        assertEquals(BigInteger.valueOf(5), new NonDivisibleByBigInteger(BigInteger.valueOf(3)).validate(BigInteger.valueOf(5)).get());
        assertEquals("non-divisible-by-big-integer", new NonDivisibleByBigInteger(BigInteger.valueOf(3)).validate(BigInteger.valueOf(6)).getError().code());
        assertEquals("non-divisible-by-big-integer", new NonDivisibleByBigInteger(BigInteger.valueOf(3)).validate(null).getError().code());
        assertThrows(IllegalArgumentException.class, () -> new NonDivisibleByBigInteger(null));
        assertThrows(IllegalArgumentException.class, () -> new NonDivisibleByBigInteger(BigInteger.ZERO));
    }

    @Test
    void nonDivisibleByBigIntegerHandlesNegativeDivisor() {
        assertEquals(BigInteger.valueOf(5), new NonDivisibleByBigInteger(BigInteger.valueOf(-3)).validate(BigInteger.valueOf(5)).get());
        assertEquals("non-divisible-by-big-integer", new NonDivisibleByBigInteger(BigInteger.valueOf(-3)).validate(BigInteger.valueOf(6)).getError().code());
    }

    @Test
    void ascendingListValidatesNonDecreasingOrder() {
        assertEquals(listOf(1, 2, 3), new AscendingList<Integer>().validate(listOf(1, 2, 3)).get());
        assertEquals(listOf(1, 1, 2), new AscendingList<Integer>().validate(listOf(1, 1, 2)).get());
        List<Integer> empty = listOf();
        assertEquals(empty, new AscendingList<Integer>().validate(empty).get());
        assertEquals(listOf(1), new AscendingList<Integer>().validate(listOf(1)).get());
        assertEquals("ascending-list", new AscendingList<Integer>().validate(listOf(3, 2, 1)).getError().code());
        assertEquals("ascending-list", new AscendingList<Integer>().validate(null).getError().code());
    }

    @Test
    void descendingListValidatesNonIncreasingOrder() {
        assertEquals(listOf(3, 2, 1), new DescendingList<Integer>().validate(listOf(3, 2, 1)).get());
        assertEquals(listOf(2, 2, 1), new DescendingList<Integer>().validate(listOf(2, 2, 1)).get());
        List<Integer> empty = listOf();
        assertEquals(empty, new DescendingList<Integer>().validate(empty).get());
        assertEquals(listOf(1), new DescendingList<Integer>().validate(listOf(1)).get());
        assertEquals("descending-list", new DescendingList<Integer>().validate(listOf(1, 2, 3)).getError().code());
        assertEquals("descending-list", new DescendingList<Integer>().validate(null).getError().code());
    }

    @Test
    void isSpecialCharPredicateValidatesCharacters() {
        assertEquals('!', new IsSpecialChar().validate('!').get());
        assertEquals("special-char", new IsSpecialChar().validate('a').getError().code());
        assertEquals("special-char", new IsSpecialChar().validate('1').getError().code());
        assertEquals("special-char", new IsSpecialChar().validate(' ').getError().code());
        assertEquals("special-char", new IsSpecialChar().validate(null).getError().code());
    }
}
