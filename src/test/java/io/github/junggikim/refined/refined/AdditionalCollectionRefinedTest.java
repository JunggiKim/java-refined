package io.github.junggikim.refined.refined;

import static io.github.junggikim.refined.support.TestCollections.listOf;
import static io.github.junggikim.refined.support.TestCollections.mapOf;
import static io.github.junggikim.refined.support.TestCollections.snapshot;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.refined.collection.NonEmptyDeque;
import io.github.junggikim.refined.refined.collection.NonEmptyNavigableMap;
import io.github.junggikim.refined.refined.collection.NonEmptyNavigableSet;
import io.github.junggikim.refined.refined.collection.NonEmptyQueue;
import io.github.junggikim.refined.refined.collection.NonEmptySortedMap;
import io.github.junggikim.refined.refined.collection.NonEmptySortedSet;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.LinkedList;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class AdditionalCollectionRefinedTest {

    @Test
    void nonEmptyQueueSupportsFactoriesAndQueueAccessors() {
        Queue<Integer> source = new ArrayDeque<Integer>(listOf(1, 2, 3));

        NonEmptyQueue<Integer> queue = NonEmptyQueue.unsafeOf(source);
        source.add(4);

        assertEquals(listOf(1, 2, 3), queue.value());
        assertEquals(1, queue.peek());
        assertEquals(3, queue.last());
        assertEquals(listOf(1, 2, 3), NonEmptyQueue.of(new ArrayDeque<Integer>(listOf(1, 2, 3))).get().value());
        assertEquals("non-empty-queue-empty", NonEmptyQueue.<Integer>of(new ArrayDeque<>()).getError().code());
        assertEquals("non-empty-queue-empty", NonEmptyQueue.<Integer>of(null).getError().code());
        assertThrows(RefinementException.class, () -> NonEmptyQueue.unsafeOf(new ArrayDeque<>()));
        assertThrows(UnsupportedOperationException.class, () -> queue.value().add(99));
    }

    @Test
    void nonEmptySortedSetPreservesComparatorAndSnapshot() {
        SortedSet<Integer> source = new TreeSet<>(Collections.reverseOrder());
        source.add(1);
        source.add(3);
        source.add(2);

        NonEmptySortedSet<Integer> sortedSet = NonEmptySortedSet.unsafeOf(source);
        source.add(4);

        assertEquals(listOf(3, 2, 1), snapshot(sortedSet.value()));
        assertEquals(Collections.reverseOrder(), sortedSet.value().comparator());
        assertEquals(3, sortedSet.first());
        assertEquals(1, sortedSet.last());
        assertEquals(listOf(1, 2), snapshot(NonEmptySortedSet.of(new TreeSet<Integer>(listOf(1, 2))).get().value()));
        assertEquals("non-empty-sorted-set-empty", NonEmptySortedSet.<Integer>of(new TreeSet<>()).getError().code());
        assertEquals("non-empty-sorted-set-empty", NonEmptySortedSet.<Integer>of(null).getError().code());
        assertThrows(RefinementException.class, () -> NonEmptySortedSet.unsafeOf(new TreeSet<>()));
        assertThrows(UnsupportedOperationException.class, () -> sortedSet.value().add(99));
    }

    @Test
    void nonEmptySortedMapPreservesComparatorAndSnapshot() {
        SortedMap<Integer, String> source = new TreeMap<>(Collections.reverseOrder());
        source.put(1, "one");
        source.put(3, "three");
        source.put(2, "two");

        NonEmptySortedMap<Integer, String> sortedMap = NonEmptySortedMap.unsafeOf(source);
        source.put(4, "four");

        assertEquals(listOf(3, 2, 1), snapshot(sortedMap.value().keySet()));
        assertEquals(Collections.reverseOrder(), sortedMap.value().comparator());
        assertEquals(3, sortedMap.firstKey());
        assertEquals(1, sortedMap.lastKey());
        assertEquals(listOf(1, 2), snapshot(NonEmptySortedMap.of(new TreeMap<Integer, String>(mapOf(1, "one", 2, "two"))).get().value().keySet()));
        assertEquals("non-empty-sorted-map-empty", NonEmptySortedMap.<Integer, String>of(new TreeMap<>()).getError().code());
        assertEquals("non-empty-sorted-map-empty", NonEmptySortedMap.<Integer, String>of(null).getError().code());
        assertThrows(RefinementException.class, () -> NonEmptySortedMap.unsafeOf(new TreeMap<>()));
        assertThrows(UnsupportedOperationException.class, () -> sortedMap.value().put(99, "boom"));
    }

    @Test
    void nonEmptyNavigableSetSupportsBoundAccessors() {
        NavigableSet<Integer> source = new TreeSet<>();
        source.add(1);
        source.add(2);
        source.add(3);

        NonEmptyNavigableSet<Integer> navigableSet = NonEmptyNavigableSet.unsafeOf(source);
        source.add(4);

        TreeSet<Integer> reverse = new TreeSet<>(Collections.reverseOrder());
        reverse.addAll(listOf(1, 2, 3));

        assertEquals(listOf(1, 2, 3), snapshot(navigableSet.value()));
        assertEquals(1, navigableSet.first());
        assertEquals(3, navigableSet.last());
        assertEquals(listOf(3, 2, 1), snapshot(NonEmptyNavigableSet.of(reverse).get().value()));
        assertEquals("non-empty-navigable-set-empty", NonEmptyNavigableSet.<Integer>of(new TreeSet<>()).getError().code());
        assertEquals("non-empty-navigable-set-empty", NonEmptyNavigableSet.<Integer>of(null).getError().code());
        assertThrows(RefinementException.class, () -> NonEmptyNavigableSet.unsafeOf(new TreeSet<>()));
        assertThrows(UnsupportedOperationException.class, () -> navigableSet.value().add(99));
    }

    @Test
    void nonEmptyNavigableMapSupportsBoundAccessors() {
        NavigableMap<Integer, String> source = new TreeMap<>();
        source.put(1, "one");
        source.put(2, "two");
        source.put(3, "three");

        NonEmptyNavigableMap<Integer, String> navigableMap = NonEmptyNavigableMap.unsafeOf(source);
        source.put(4, "four");

        TreeMap<Integer, String> reverse = new TreeMap<>(Collections.reverseOrder());
        reverse.put(1, "one");
        reverse.put(2, "two");
        reverse.put(3, "three");

        assertEquals(listOf(1, 2, 3), snapshot(navigableMap.value().keySet()));
        assertEquals(1, navigableMap.firstKey());
        assertEquals(3, navigableMap.lastKey());
        assertEquals(listOf(3, 2, 1), snapshot(NonEmptyNavigableMap.of(reverse).get().value().keySet()));
        assertEquals("non-empty-navigable-map-empty", NonEmptyNavigableMap.<Integer, String>of(new TreeMap<>()).getError().code());
        assertEquals("non-empty-navigable-map-empty", NonEmptyNavigableMap.<Integer, String>of(null).getError().code());
        assertThrows(RefinementException.class, () -> NonEmptyNavigableMap.unsafeOf(new TreeMap<>()));
        assertThrows(UnsupportedOperationException.class, () -> navigableMap.value().put(99, "boom"));
    }

    @RepeatedTest(3)
    void additionalCollectionWrappersRemainDeterministicAcrossRuns() {
        assertEquals(listOf(1, 2), NonEmptyQueue.unsafeOf(new ArrayDeque<Integer>(listOf(1, 2))).value());
        TreeSet<Integer> reverseSorted = new TreeSet<>(Collections.reverseOrder());
        reverseSorted.addAll(listOf(2, 1));
        assertEquals(listOf(2, 1), snapshot(NonEmptySortedSet.unsafeOf(reverseSorted).value()));
        assertTrue(NonEmptyNavigableMap.unsafeOf(new TreeMap<Integer, String>(mapOf(1, "one"))).toString().contains("NonEmptyNavigableMap"));
    }

    @Test
    void queueLikeWrappersReturnInvalidForNullElements() {
        LinkedList<Integer> dequeWithNull = new LinkedList<Integer>();
        dequeWithNull.add(1);
        dequeWithNull.add(null);

        assertEquals("non-empty-queue-null-element", NonEmptyQueue.of(dequeWithNull).getError().code());
        assertEquals("non-empty-deque-null-element", NonEmptyDeque.of(dequeWithNull).getError().code());
    }
}
