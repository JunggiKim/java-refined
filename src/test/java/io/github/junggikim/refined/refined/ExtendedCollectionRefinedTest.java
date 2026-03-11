package io.github.junggikim.refined.refined;

import static io.github.junggikim.refined.support.TestCollections.listOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.refined.collection.NonEmptyDeque;
import io.github.junggikim.refined.refined.collection.NonEmptyIterable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import org.junit.jupiter.api.Test;

class ExtendedCollectionRefinedTest {

    @Test
    void nonEmptyDequeSupportsFactoriesAndDequeAccessors() {
        Deque<Integer> source = new ArrayDeque<Integer>(listOf(1, 2, 3));

        NonEmptyDeque<Integer> deque = NonEmptyDeque.unsafeOf(source);
        source.addLast(4);

        assertEquals(listOf(1, 2, 3), deque.value());
        assertEquals(1, deque.first());
        assertEquals(3, deque.last());
        assertEquals(listOf(1, 2, 3), NonEmptyDeque.of(new ArrayDeque<Integer>(listOf(1, 2, 3))).get().value());
        assertEquals("non-empty-deque-empty", NonEmptyDeque.<Integer>of(new ArrayDeque<>()).getError().code());
        assertEquals("non-empty-deque-empty", NonEmptyDeque.<Integer>of(null).getError().code());
        assertThrows(RefinementException.class, () -> NonEmptyDeque.unsafeOf(new ArrayDeque<>()));
        assertThrows(UnsupportedOperationException.class, () -> deque.value().add(99));
    }

    @Test
    void nonEmptyIterableSupportsFactoriesAndIteration() {
        Iterable<Integer> source = listOf(4, 5, 6);

        NonEmptyIterable<Integer> iterable = NonEmptyIterable.unsafeOf(source);
        Iterator<Integer> iterator = iterable.iterator();

        assertEquals(listOf(4, 5, 6), iterable.value());
        assertEquals(4, iterable.head());
        assertEquals(listOf(5, 6), iterable.tail());
        assertEquals(listOf(4, 5, 6), NonEmptyIterable.of(source).get().value());
        assertEquals("non-empty-iterable-empty", NonEmptyIterable.<Integer>of(listOf()).getError().code());
        assertEquals("non-empty-iterable-empty", NonEmptyIterable.<Integer>of(null).getError().code());
        assertThrows(RefinementException.class, () -> NonEmptyIterable.unsafeOf(listOf()));
        assertTrue(iterator.hasNext());
        assertEquals(4, iterator.next());
        assertThrows(UnsupportedOperationException.class, () -> iterable.value().add(99));
        assertTrue(iterable.toString().contains("NonEmptyIterable"));
    }

    @Test
    void nonEmptyIterableReturnsInvalidForNullElement() {
        Iterable<Integer> source = () -> java.util.Arrays.<Integer>asList(1, null).iterator();

        assertEquals("non-empty-iterable-null-element", NonEmptyIterable.of(source).getError().code());
    }
}
