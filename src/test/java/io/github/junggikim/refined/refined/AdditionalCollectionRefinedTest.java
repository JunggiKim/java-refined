package io.github.junggikim.refined.refined;

import static io.github.junggikim.refined.support.TestCollections.listOf;
import static io.github.junggikim.refined.support.TestCollections.mapOf;
import static io.github.junggikim.refined.support.TestCollections.snapshot;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.refined.collection.NonEmptyNavigableMap;
import io.github.junggikim.refined.refined.collection.NonEmptyNavigableSet;
import io.github.junggikim.refined.refined.collection.NonEmptyQueue;
import io.github.junggikim.refined.refined.collection.NonEmptySortedMap;
import io.github.junggikim.refined.refined.collection.NonEmptySortedSet;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
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
        Queue<Integer> queueView = queue;
        List<Integer> listView = queue;
        source.add(4);

        assertEquals(listOf(1, 2, 3), listView);
        assertEquals(1, queueView.peek());
        assertEquals(1, queueView.element());
        assertEquals(3, queue.last());
        assertEquals(2, listView.get(1));
        assertEquals(listOf(1, 2, 3), queue);
        assertEquals(listOf(1, 2, 3).hashCode(), queue.hashCode());
        assertEquals("non-empty-queue-empty", NonEmptyQueue.<Integer>of(new ArrayDeque<>()).getError().code());
        assertEquals("non-empty-queue-empty", NonEmptyQueue.<Integer>of(null).getError().code());
        assertThrows(RefinementException.class, () -> NonEmptyQueue.unsafeOf(new ArrayDeque<Integer>()));
        assertThrows(UnsupportedOperationException.class, () -> queueView.offer(99));
        assertThrows(UnsupportedOperationException.class, () -> queueView.remove());
        assertThrows(UnsupportedOperationException.class, () -> queueView.poll());
        assertThrows(UnsupportedOperationException.class, () -> listView.add(99));
        assertThrows(UnsupportedOperationException.class, () -> listView.listIterator().add(99));
        assertThrows(UnsupportedOperationException.class, () -> {
            java.util.ListIterator<Integer> iterator = listView.listIterator();
            iterator.next();
            iterator.set(99);
        });
    }

    @Test
    void nonEmptySortedSetPreservesComparatorAndSnapshot() {
        SortedSet<Integer> source = new TreeSet<Integer>(Collections.reverseOrder());
        source.add(1);
        source.add(3);
        source.add(2);

        SortedSet<Integer> sortedSet = NonEmptySortedSet.unsafeOf(source);
        source.add(4);

        SortedSet<Integer> naturalSet = NonEmptySortedSet.unsafeOf(new TreeSet<Integer>(listOf(1, 2, 3)));

        assertEquals(listOf(3, 2, 1), snapshot(sortedSet));
        assertTrue(sortedSet.contains(2));
        assertEquals(3, sortedSet.size());
        assertEquals(Collections.reverseOrder(), sortedSet.comparator());
        assertEquals(3, sortedSet.first());
        assertEquals(1, sortedSet.last());
        assertEquals(listOf(1, 2), snapshot(naturalSet.headSet(3)));
        assertEquals(listOf(2, 3), snapshot(naturalSet.tailSet(2)));
        assertEquals(listOf(1, 2), snapshot(naturalSet.subSet(1, 3)));
        assertEquals("non-empty-sorted-set-empty", NonEmptySortedSet.<Integer>of(new TreeSet<Integer>()).getError().code());
        assertEquals("non-empty-sorted-set-empty", NonEmptySortedSet.<Integer>of(null).getError().code());
        assertThrows(RefinementException.class, () -> NonEmptySortedSet.unsafeOf(new TreeSet<Integer>()));
        assertThrows(UnsupportedOperationException.class, () -> sortedSet.add(99));
        assertThrows(UnsupportedOperationException.class, () -> naturalSet.headSet(3).add(0));
    }

    @Test
    void nonEmptySortedMapPreservesComparatorAndSnapshot() {
        SortedMap<Integer, String> source = new TreeMap<Integer, String>(Collections.reverseOrder());
        source.put(1, "one");
        source.put(3, "three");
        source.put(2, "two");

        SortedMap<Integer, String> sortedMap = NonEmptySortedMap.unsafeOf(source);
        source.put(4, "four");

        SortedMap<Integer, String> naturalMap = NonEmptySortedMap.unsafeOf(new TreeMap<Integer, String>(mapOf(1, "one", 2, "two", 3, "three")));

        assertEquals(listOf(3, 2, 1), snapshot(sortedMap.keySet()));
        assertEquals("one", sortedMap.get(1));
        assertTrue(sortedMap.containsKey(1));
        assertTrue(sortedMap.containsValue("two"));
        assertEquals(3, sortedMap.size());
        assertEquals(Collections.reverseOrder(), sortedMap.comparator());
        assertEquals(3, sortedMap.firstKey());
        assertEquals(1, sortedMap.lastKey());
        assertEquals(listOf(1, 2), snapshot(naturalMap.headMap(3).keySet()));
        assertEquals(listOf(2, 3), snapshot(naturalMap.tailMap(2).keySet()));
        assertEquals(listOf(1, 2), snapshot(naturalMap.subMap(1, 3).keySet()));
        assertEquals("non-empty-sorted-map-empty", NonEmptySortedMap.<Integer, String>of(new TreeMap<Integer, String>()).getError().code());
        assertEquals("non-empty-sorted-map-empty", NonEmptySortedMap.<Integer, String>of(null).getError().code());
        assertThrows(RefinementException.class, () -> NonEmptySortedMap.unsafeOf(new TreeMap<Integer, String>()));
        assertThrows(UnsupportedOperationException.class, () -> sortedMap.put(99, "boom"));
        assertThrows(UnsupportedOperationException.class, () -> naturalMap.headMap(3).put(0, "zero"));
    }

    @Test
    void nonEmptyNavigableSetSupportsBoundAccessors() {
        NavigableSet<Integer> source = new TreeSet<Integer>();
        source.add(1);
        source.add(2);
        source.add(3);

        NavigableSet<Integer> navigableSet = NonEmptyNavigableSet.unsafeOf(source);
        source.add(4);

        TreeSet<Integer> reverse = new TreeSet<Integer>(Collections.reverseOrder());
        reverse.addAll(listOf(1, 2, 3));

        java.util.Iterator<Integer> descendingIterator = navigableSet.descendingIterator();
        List<Integer> descendingValues = new java.util.ArrayList<Integer>();
        while (descendingIterator.hasNext()) {
            descendingValues.add(descendingIterator.next());
        }

        assertEquals(listOf(1, 2, 3), snapshot(navigableSet));
        assertTrue(navigableSet.contains(2));
        assertEquals(3, navigableSet.size());
        assertEquals(null, navigableSet.comparator());
        assertEquals(1, navigableSet.first());
        assertEquals(3, navigableSet.last());
        assertEquals(1, navigableSet.lower(2));
        assertEquals(2, navigableSet.floor(2));
        assertEquals(2, navigableSet.ceiling(2));
        assertEquals(3, navigableSet.higher(2));
        assertEquals(listOf(3, 2, 1), descendingValues);
        assertEquals(listOf(3, 2, 1), snapshot(navigableSet.descendingSet()));
        assertEquals(listOf(1, 2), snapshot(navigableSet.headSet(3, false)));
        assertEquals(listOf(2, 3), snapshot(navigableSet.tailSet(2, true)));
        assertEquals(listOf(1, 2), snapshot(navigableSet.subSet(1, true, 3, false)));
        assertEquals(listOf(1, 2), snapshot(navigableSet.headSet(3)));
        assertEquals(listOf(2, 3), snapshot(navigableSet.tailSet(2)));
        assertEquals(listOf(1, 2), snapshot(navigableSet.subSet(1, 3)));
        assertEquals(listOf(3, 2, 1), snapshot(NonEmptyNavigableSet.of(reverse).get()));
        assertEquals(Collections.reverseOrder(), NonEmptyNavigableSet.of(reverse).get().comparator());
        assertEquals("non-empty-navigable-set-empty", NonEmptyNavigableSet.<Integer>of(new TreeSet<Integer>()).getError().code());
        assertEquals("non-empty-navigable-set-empty", NonEmptyNavigableSet.<Integer>of(null).getError().code());
        assertThrows(RefinementException.class, () -> NonEmptyNavigableSet.unsafeOf(new TreeSet<Integer>()));
        assertThrows(UnsupportedOperationException.class, () -> navigableSet.add(99));
        assertThrows(UnsupportedOperationException.class, () -> navigableSet.pollFirst());
        assertThrows(UnsupportedOperationException.class, () -> navigableSet.pollLast());
        assertThrows(UnsupportedOperationException.class, () -> navigableSet.descendingSet().pollFirst());
    }

    @Test
    void nonEmptyNavigableMapSupportsBoundAccessors() {
        NavigableMap<Integer, String> source = new TreeMap<Integer, String>();
        source.put(1, "one");
        source.put(2, "two");
        source.put(3, "three");

        NavigableMap<Integer, String> navigableMap = NonEmptyNavigableMap.unsafeOf(source);
        source.put(4, "four");

        TreeMap<Integer, String> reverse = new TreeMap<Integer, String>(Collections.reverseOrder());
        reverse.put(1, "one");
        reverse.put(2, "two");
        reverse.put(3, "three");

        assertEquals(listOf(1, 2, 3), snapshot(navigableMap.keySet()));
        assertEquals("two", navigableMap.get(2));
        assertTrue(navigableMap.containsKey(3));
        assertTrue(navigableMap.containsValue("two"));
        assertEquals(3, navigableMap.size());
        assertEquals(null, navigableMap.comparator());
        assertEquals(1, navigableMap.firstKey());
        assertEquals(3, navigableMap.lastKey());
        assertEquals(mapOf(1, "one"), java.util.Collections.singletonMap(navigableMap.lowerEntry(2).getKey(), navigableMap.lowerEntry(2).getValue()));
        assertEquals(mapOf(2, "two"), java.util.Collections.singletonMap(navigableMap.floorEntry(2).getKey(), navigableMap.floorEntry(2).getValue()));
        assertEquals(mapOf(2, "two"), java.util.Collections.singletonMap(navigableMap.ceilingEntry(2).getKey(), navigableMap.ceilingEntry(2).getValue()));
        assertEquals(mapOf(3, "three"), java.util.Collections.singletonMap(navigableMap.higherEntry(2).getKey(), navigableMap.higherEntry(2).getValue()));
        assertEquals(mapOf(1, "one"), java.util.Collections.singletonMap(navigableMap.firstEntry().getKey(), navigableMap.firstEntry().getValue()));
        assertEquals(mapOf(3, "three"), java.util.Collections.singletonMap(navigableMap.lastEntry().getKey(), navigableMap.lastEntry().getValue()));
        assertEquals(1, navigableMap.lowerKey(2));
        assertEquals(2, navigableMap.floorKey(2));
        assertEquals(2, navigableMap.ceilingKey(2));
        assertEquals(3, navigableMap.higherKey(2));
        assertEquals(mapOf(1, "one", 2, "two"), navigableMap.headMap(2, true));
        assertEquals(mapOf(2, "two", 3, "three"), navigableMap.tailMap(2, true));
        assertEquals(mapOf(1, "one", 2, "two"), navigableMap.subMap(1, true, 3, false));
        assertEquals(mapOf(1, "one", 2, "two"), navigableMap.headMap(3));
        assertEquals(mapOf(2, "two", 3, "three"), navigableMap.tailMap(2));
        assertEquals(mapOf(1, "one", 2, "two"), navigableMap.subMap(1, 3));
        assertEquals(listOf(3, 2, 1), snapshot(navigableMap.descendingMap().keySet()));
        assertEquals(listOf(1, 2, 3), snapshot(navigableMap.navigableKeySet()));
        assertEquals(listOf(3, 2, 1), snapshot(navigableMap.descendingKeySet()));
        assertEquals(listOf(3, 2, 1), snapshot(NonEmptyNavigableMap.of(reverse).get().keySet()));
        assertEquals(Collections.reverseOrder(), NonEmptyNavigableMap.of(reverse).get().comparator());
        assertEquals("non-empty-navigable-map-empty", NonEmptyNavigableMap.<Integer, String>of(new TreeMap<Integer, String>()).getError().code());
        assertEquals("non-empty-navigable-map-empty", NonEmptyNavigableMap.<Integer, String>of(null).getError().code());
        assertThrows(RefinementException.class, () -> NonEmptyNavigableMap.unsafeOf(new TreeMap<Integer, String>()));
        assertThrows(UnsupportedOperationException.class, () -> navigableMap.put(99, "boom"));
        assertThrows(UnsupportedOperationException.class, () -> navigableMap.pollFirstEntry());
        assertThrows(UnsupportedOperationException.class, () -> navigableMap.pollLastEntry());
        assertThrows(UnsupportedOperationException.class, () -> navigableMap.descendingMap().put(0, "zero"));
        assertThrows(UnsupportedOperationException.class, () -> navigableMap.navigableKeySet().remove(1));
    }

    @RepeatedTest(3)
    void additionalCollectionWrappersRemainDeterministicAcrossRuns() {
        assertEquals(listOf(1, 2), NonEmptyQueue.unsafeOf(new ArrayDeque<Integer>(listOf(1, 2))));
        TreeSet<Integer> reverseSorted = new TreeSet<Integer>(Collections.reverseOrder());
        reverseSorted.addAll(listOf(2, 1));
        assertEquals(listOf(2, 1), snapshot(NonEmptySortedSet.unsafeOf(reverseSorted)));
        assertEquals(
            new TreeMap<Integer, String>(mapOf(1, "one")).toString(),
            NonEmptyNavigableMap.unsafeOf(new TreeMap<Integer, String>(mapOf(1, "one"))).toString()
        );
    }

    @Test
    void queueLikeWrappersReturnInvalidForNullElements() {
        LinkedList<Integer> dequeWithNull = new LinkedList<Integer>();
        dequeWithNull.add(1);
        dequeWithNull.add(null);

        assertEquals("non-empty-queue-null-element", NonEmptyQueue.of(dequeWithNull).getError().code());
        assertEquals("non-empty-deque-null-element", io.github.junggikim.refined.refined.collection.NonEmptyDeque.of(dequeWithNull).getError().code());
    }
}
