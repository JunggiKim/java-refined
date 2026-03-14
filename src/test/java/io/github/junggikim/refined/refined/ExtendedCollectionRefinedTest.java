package io.github.junggikim.refined.refined;

import static io.github.junggikim.refined.support.TestCollections.listOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.refined.collection.NonEmptyDeque;
import io.github.junggikim.refined.refined.collection.NonEmptyIterable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;

class ExtendedCollectionRefinedTest {

    @Test
    void nonEmptyDequeSupportsFactoriesAndDequeAccessors() {
        Deque<Integer> source = new ArrayDeque<Integer>(listOf(1, 2, 3));

        NonEmptyDeque<Integer> deque = NonEmptyDeque.unsafeOf(source);
        Deque<Integer> dequeView = deque;
        List<Integer> listView = deque;
        source.addLast(4);

        Iterator<Integer> descending = dequeView.descendingIterator();
        List<Integer> reversed = new ArrayList<Integer>();
        while (descending.hasNext()) {
            reversed.add(descending.next());
        }

        assertEquals(listOf(1, 2, 3), listView);
        assertEquals(1, deque.first());
        assertEquals(3, deque.last());
        assertEquals(1, dequeView.getFirst());
        assertEquals(3, dequeView.getLast());
        assertEquals(1, dequeView.peekFirst());
        assertEquals(3, dequeView.peekLast());
        assertEquals(1, dequeView.element());
        assertEquals(1, dequeView.peek());
        assertEquals(listOf(3, 2, 1), reversed);
        assertEquals("non-empty-deque-empty", NonEmptyDeque.<Integer>of(new ArrayDeque<Integer>()).getError().code());
        assertEquals("non-empty-deque-empty", NonEmptyDeque.<Integer>of((Deque<Integer>) null).getError().code());
        assertThrows(RefinementException.class, () -> NonEmptyDeque.unsafeOf(new ArrayDeque<Integer>()));
        assertThrows(UnsupportedOperationException.class, () -> dequeView.addFirst(99));
        assertThrows(UnsupportedOperationException.class, () -> dequeView.addLast(99));
        assertThrows(UnsupportedOperationException.class, () -> dequeView.offerFirst(99));
        assertThrows(UnsupportedOperationException.class, () -> dequeView.offerLast(99));
        assertThrows(UnsupportedOperationException.class, () -> dequeView.removeFirst());
        assertThrows(UnsupportedOperationException.class, () -> dequeView.removeLast());
        assertThrows(UnsupportedOperationException.class, () -> dequeView.pollFirst());
        assertThrows(UnsupportedOperationException.class, () -> dequeView.pollLast());
        assertThrows(UnsupportedOperationException.class, () -> dequeView.removeFirstOccurrence(1));
        assertThrows(UnsupportedOperationException.class, () -> dequeView.removeLastOccurrence(3));
        assertThrows(UnsupportedOperationException.class, () -> dequeView.offer(99));
        assertThrows(UnsupportedOperationException.class, () -> dequeView.remove());
        assertThrows(UnsupportedOperationException.class, () -> dequeView.poll());
        assertThrows(UnsupportedOperationException.class, () -> dequeView.push(99));
        assertThrows(UnsupportedOperationException.class, () -> dequeView.pop());
        assertThrows(UnsupportedOperationException.class, () -> listView.add(99));
        assertThrows(UnsupportedOperationException.class, () -> dequeView.descendingIterator().remove());
    }

    @Test
    void nonEmptyIterableSupportsFactoriesAndIteration() {
        Iterable<Integer> source = listOf(4, 5, 6);

        NonEmptyIterable<Integer> iterable = NonEmptyIterable.unsafeOf(source);
        Iterable<Integer> iterableView = iterable;
        List<Integer> listView = iterable;
        Iterator<Integer> iterator = iterableView.iterator();

        assertEquals(listOf(4, 5, 6), listView);
        assertEquals(listOf(4, 5, 6), iterable);
        assertEquals(4, iterable.head());
        assertEquals(listOf(5, 6), iterable.tail());
        assertEquals(5, iterable.get(1));
        assertEquals(listOf(4, 5, 6), NonEmptyIterable.of(source).get());
        assertEquals("non-empty-iterable-empty", NonEmptyIterable.<Integer>of(listOf()).getError().code());
        assertEquals("non-empty-iterable-empty", NonEmptyIterable.<Integer>of((Iterable<Integer>) null).getError().code());
        assertThrows(RefinementException.class, () -> NonEmptyIterable.unsafeOf(listOf()));
        assertEquals(4, iterator.next());
        assertThrows(UnsupportedOperationException.class, () -> iterator.remove());
        assertThrows(UnsupportedOperationException.class, () -> listView.add(99));
        assertEquals(listOf(4, 5, 6).toString(), iterable.toString());
    }

    @Test
    void nonEmptyIterableReturnsInvalidForNullElement() {
        Iterable<Integer> source = () -> java.util.Arrays.<Integer>asList(1, null).iterator();

        assertEquals("non-empty-iterable-null-element", NonEmptyIterable.of(source).getError().code());
    }
}
