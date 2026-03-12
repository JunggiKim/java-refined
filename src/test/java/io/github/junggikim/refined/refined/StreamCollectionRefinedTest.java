package io.github.junggikim.refined.refined;

import static io.github.junggikim.refined.support.TestCollections.listOf;
import static io.github.junggikim.refined.support.TestCollections.mapOf;
import static io.github.junggikim.refined.support.TestCollections.snapshot;
import static io.github.junggikim.refined.support.TestCollections.setOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.junggikim.refined.core.RefinementException;
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
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.Test;

class StreamCollectionRefinedTest {

    @Test
    void linearCollectionTypesSupportSafeAndUnsafeStreamFactories() {
        NonEmptyList<String> list = NonEmptyList.unsafeOfStream(Stream.of("a", "b"));
        NonEmptySet<String> set = NonEmptySet.unsafeOfStream(Stream.of("a", "b"));
        NonEmptyQueue<String> queue = NonEmptyQueue.unsafeOfStream(Stream.of("a", "b"));
        NonEmptyDeque<String> deque = NonEmptyDeque.unsafeOfStream(Stream.of("a", "b"));
        NonEmptyIterable<String> iterable = NonEmptyIterable.unsafeOfStream(Stream.of("a", "b"));

        assertEquals(listOf("a", "b"), list);
        assertEquals(setOf("a", "b"), set);
        assertEquals(listOf("a", "b"), queue);
        assertEquals(listOf("a", "b"), deque);
        assertEquals(listOf("a", "b"), iterable);

        assertEquals(listOf("x", "y"), NonEmptyList.ofStream(Stream.of("x", "y")).get());
        assertEquals(setOf("x", "y"), NonEmptySet.ofStream(Stream.of("x", "y")).get());
        assertEquals(listOf("x", "y"), NonEmptyQueue.ofStream(Stream.of("x", "y")).get());
        assertEquals(listOf("x", "y"), NonEmptyDeque.ofStream(Stream.of("x", "y")).get());
        assertEquals(listOf("x", "y"), NonEmptyIterable.ofStream(Stream.of("x", "y")).get());
    }

    @Test
    void linearCollectionStreamFactoriesReturnInvalidForNullEmptyAndNullElements() {
        assertEquals("non-empty-list-empty", NonEmptyList.<Integer>ofStream(null).getError().code());
        assertEquals("non-empty-set-empty", NonEmptySet.<Integer>ofStream(null).getError().code());
        assertEquals("non-empty-queue-empty", NonEmptyQueue.<Integer>ofStream(null).getError().code());
        assertEquals("non-empty-deque-empty", NonEmptyDeque.<Integer>ofStream(null).getError().code());
        assertEquals("non-empty-iterable-empty", NonEmptyIterable.<Integer>ofStream(null).getError().code());

        assertEquals("non-empty-list-empty", NonEmptyList.ofStream(Stream.<Integer>empty()).getError().code());
        assertEquals("non-empty-set-empty", NonEmptySet.ofStream(Stream.<Integer>empty()).getError().code());
        assertEquals("non-empty-queue-empty", NonEmptyQueue.ofStream(Stream.<Integer>empty()).getError().code());
        assertEquals("non-empty-deque-empty", NonEmptyDeque.ofStream(Stream.<Integer>empty()).getError().code());
        assertEquals("non-empty-iterable-empty", NonEmptyIterable.ofStream(Stream.<Integer>empty()).getError().code());

        assertEquals("non-empty-list-null-element", NonEmptyList.ofStream(Stream.of(1, null)).getError().code());
        assertEquals("non-empty-set-null-element", NonEmptySet.ofStream(Stream.of(1, null)).getError().code());
        assertEquals("non-empty-queue-null-element", NonEmptyQueue.ofStream(Stream.of(1, null)).getError().code());
        assertEquals("non-empty-deque-null-element", NonEmptyDeque.ofStream(Stream.of(1, null)).getError().code());
        assertEquals("non-empty-iterable-null-element", NonEmptyIterable.ofStream(Stream.of(1, null)).getError().code());

        assertThrows(RefinementException.class, () -> NonEmptyList.unsafeOfStream(Stream.<Integer>empty()));
        assertThrows(RefinementException.class, () -> NonEmptySet.unsafeOfStream(Stream.<Integer>empty()));
        assertThrows(RefinementException.class, () -> NonEmptyQueue.unsafeOfStream(Stream.<Integer>empty()));
        assertThrows(RefinementException.class, () -> NonEmptyDeque.unsafeOfStream(Stream.<Integer>empty()));
        assertThrows(RefinementException.class, () -> NonEmptyIterable.unsafeOfStream(Stream.<Integer>empty()));
    }

    @Test
    void linearCollectionStreamFactoriesReturnInvalidForBrokenStreams() {
        assertEquals("non-empty-list-invalid-element", NonEmptyList.ofStream(failingElementStream()).getError().code());
        assertEquals("non-empty-set-invalid-element", NonEmptySet.ofStream(failingElementStream()).getError().code());
        assertEquals("non-empty-queue-invalid-element", NonEmptyQueue.ofStream(failingElementStream()).getError().code());
        assertEquals("non-empty-deque-invalid-element", NonEmptyDeque.ofStream(failingElementStream()).getError().code());
        assertEquals("non-empty-iterable-invalid-element", NonEmptyIterable.ofStream(failingElementStream()).getError().code());
    }

    @Test
    void mapTypesSupportEntryStreamFactories() {
        NonEmptyMap<String, Integer> map = NonEmptyMap.unsafeOfEntryStream(entryStream("a", 1, "b", 2));
        SortedMap<Integer, String> sortedMap = NonEmptySortedMap.unsafeOfEntryStream(
            StreamCollectionRefinedTest.<Integer, String>entryStream(2, "two", 1, "one")
        );
        SortedMap<Integer, String> reverseSortedMap = NonEmptySortedMap.unsafeOfEntryStream(
            entryStream(2, "two", 1, "one"),
            Collections.reverseOrder()
        );
        NavigableMap<Integer, String> navigableMap = NonEmptyNavigableMap.unsafeOfEntryStream(
            StreamCollectionRefinedTest.<Integer, String>entryStream(2, "two", 1, "one")
        );
        NavigableMap<Integer, String> reverseNavigableMap = NonEmptyNavigableMap.unsafeOfEntryStream(
            entryStream(2, "two", 1, "one"),
            Collections.reverseOrder()
        );

        assertEquals(mapOf("a", 1, "b", 2), map);
        assertEquals(listOf(1, 2), snapshot(sortedMap.keySet()));
        assertEquals(listOf(2, 1), snapshot(reverseSortedMap.keySet()));
        assertEquals(listOf(1, 2), snapshot(navigableMap.keySet()));
        assertEquals(listOf(2, 1), snapshot(reverseNavigableMap.keySet()));

        assertEquals(mapOf("x", 1), NonEmptyMap.ofEntryStream(entryStream("x", 1)).get());
        assertEquals(
            listOf(1),
            snapshot(NonEmptySortedMap.ofEntryStream(StreamCollectionRefinedTest.<Integer, String>entryStream(1, "one")).get().keySet())
        );
        assertEquals(
            listOf(1),
            snapshot(NonEmptySortedMap.ofEntryStream(entryStream(1, "one"), Collections.<Integer>reverseOrder()).get().keySet())
        );
        assertEquals(
            listOf(1),
            snapshot(NonEmptyNavigableMap.ofEntryStream(StreamCollectionRefinedTest.<Integer, String>entryStream(1, "one")).get().keySet())
        );
        assertEquals(
            listOf(1),
            snapshot(NonEmptyNavigableMap.ofEntryStream(entryStream(1, "one"), Collections.<Integer>reverseOrder()).get().keySet())
        );
    }

    @Test
    void mapEntryStreamFactoriesReturnInvalidForNullEmptyAndInvalidEntries() {
        assertEquals("non-empty-map-empty", NonEmptyMap.<String, Integer>ofEntryStream(null).getError().code());
        assertEquals("non-empty-sorted-map-empty", NonEmptySortedMap.<Integer, String>ofEntryStream(null).getError().code());
        assertEquals(
            "non-empty-sorted-map-empty",
            NonEmptySortedMap.<Integer, String>ofEntryStream(null, Comparator.<Integer>naturalOrder()).getError().code()
        );
        assertEquals("non-empty-navigable-map-empty", NonEmptyNavigableMap.<Integer, String>ofEntryStream(null).getError().code());
        assertEquals(
            "non-empty-navigable-map-empty",
            NonEmptyNavigableMap.<Integer, String>ofEntryStream(null, Comparator.<Integer>naturalOrder()).getError().code()
        );

        assertEquals("non-empty-map-empty", NonEmptyMap.ofEntryStream(Stream.<Map.Entry<String, Integer>>empty()).getError().code());
        assertEquals("non-empty-sorted-map-empty", NonEmptySortedMap.ofEntryStream(Stream.<Map.Entry<Integer, String>>empty()).getError().code());
        assertEquals(
            "non-empty-navigable-map-empty",
            NonEmptyNavigableMap.ofEntryStream(Stream.<Map.Entry<Integer, String>>empty()).getError().code()
        );

        assertEquals("non-empty-map-null-key", NonEmptyMap.ofEntryStream(Stream.of(entry(null, 1))).getError().code());
        assertEquals("non-empty-map-null-value", NonEmptyMap.ofEntryStream(Stream.of(entry("a", null))).getError().code());
        assertEquals("non-empty-map-invalid-entry", NonEmptyMap.ofEntryStream(Stream.<Map.Entry<String, Integer>>of((Map.Entry<String, Integer>) null)).getError().code());
        assertEquals(
            "non-empty-sorted-map-null-key",
            NonEmptySortedMap.ofEntryStream(Stream.of(entry((Integer) null, "one")), Comparator.<Integer>naturalOrder()).getError().code()
        );
        assertEquals(
            "non-empty-sorted-map-null-value",
            NonEmptySortedMap.ofEntryStream(Stream.of(entry(1, (String) null)), Comparator.<Integer>naturalOrder()).getError().code()
        );
        assertEquals("non-empty-sorted-map-invalid-entry", NonEmptySortedMap.ofEntryStream(Stream.<Map.Entry<Integer, String>>of((Map.Entry<Integer, String>) null)).getError().code());
        assertEquals(
            "non-empty-navigable-map-null-key",
            NonEmptyNavigableMap.ofEntryStream(Stream.of(entry((Integer) null, "one")), Comparator.<Integer>naturalOrder()).getError().code()
        );
        assertEquals(
            "non-empty-navigable-map-null-value",
            NonEmptyNavigableMap.ofEntryStream(Stream.of(entry(1, (String) null)), Comparator.<Integer>naturalOrder()).getError().code()
        );
        assertEquals("non-empty-navigable-map-invalid-entry", NonEmptyNavigableMap.ofEntryStream(Stream.<Map.Entry<Integer, String>>of((Map.Entry<Integer, String>) null)).getError().code());

        assertEquals("non-empty-map-invalid-entry", NonEmptyMap.ofEntryStream(failingEntryStream()).getError().code());
        assertEquals(
            "non-empty-sorted-map-invalid-entry",
            NonEmptySortedMap.ofEntryStream(StreamCollectionRefinedTest.<Integer, String>failingEntryStream()).getError().code()
        );
        assertEquals(
            "non-empty-navigable-map-invalid-entry",
            NonEmptyNavigableMap.ofEntryStream(StreamCollectionRefinedTest.<Integer, String>failingEntryStream()).getError().code()
        );

        assertThrows(RefinementException.class, () -> NonEmptyMap.unsafeOfEntryStream(Stream.<Map.Entry<String, Integer>>empty()));
        assertThrows(RefinementException.class, () -> NonEmptySortedMap.unsafeOfEntryStream(Stream.<Map.Entry<Integer, String>>empty()));
        assertThrows(RefinementException.class, () -> NonEmptySortedMap.unsafeOfEntryStream(
            Stream.<Map.Entry<Integer, String>>empty(),
            Collections.<Integer>reverseOrder()
        ));
        assertThrows(RefinementException.class, () -> NonEmptyNavigableMap.unsafeOfEntryStream(Stream.<Map.Entry<Integer, String>>empty()));
        assertThrows(RefinementException.class, () -> NonEmptyNavigableMap.unsafeOfEntryStream(
            Stream.<Map.Entry<Integer, String>>empty(),
            Collections.<Integer>reverseOrder()
        ));
    }

    @Test
    void sortedAndNavigableSetStreamFactoriesSupportNaturalAndCustomOrdering() {
        SortedSet<Integer> naturalSortedSet = NonEmptySortedSet.unsafeOfStream(Stream.of(2, 1));
        SortedSet<Integer> reverseSortedSet = NonEmptySortedSet.unsafeOfStream(Stream.of(2, 1), Collections.reverseOrder());
        NavigableSet<Integer> naturalNavigableSet = NonEmptyNavigableSet.unsafeOfStream(Stream.of(2, 1));
        NavigableSet<Integer> reverseNavigableSet = NonEmptyNavigableSet.unsafeOfStream(Stream.of(2, 1), Collections.reverseOrder());

        assertEquals(listOf(1, 2), snapshot(naturalSortedSet));
        assertEquals(listOf(2, 1), snapshot(reverseSortedSet));
        assertEquals(listOf(1, 2), snapshot(naturalNavigableSet));
        assertEquals(listOf(2, 1), snapshot(reverseNavigableSet));

        assertEquals(listOf(1), snapshot(NonEmptySortedSet.ofStream(Stream.of(1)).get()));
        assertEquals(listOf(1), snapshot(NonEmptySortedSet.ofStream(Stream.of(1), Collections.<Integer>reverseOrder()).get()));
        assertEquals(listOf(1), snapshot(NonEmptyNavigableSet.ofStream(Stream.of(1)).get()));
        assertEquals(listOf(1), snapshot(NonEmptyNavigableSet.ofStream(Stream.of(1), Collections.<Integer>reverseOrder()).get()));
    }

    @Test
    void sortedAndNavigableSetStreamFactoriesReturnInvalidForBrokenStreams() {
        assertEquals("non-empty-sorted-set-empty", NonEmptySortedSet.<Integer>ofStream(null).getError().code());
        assertEquals(
            "non-empty-sorted-set-empty",
            NonEmptySortedSet.<Integer>ofStream(null, Comparator.<Integer>naturalOrder()).getError().code()
        );
        assertEquals("non-empty-navigable-set-empty", NonEmptyNavigableSet.<Integer>ofStream(null).getError().code());
        assertEquals(
            "non-empty-navigable-set-empty",
            NonEmptyNavigableSet.<Integer>ofStream(null, Comparator.<Integer>naturalOrder()).getError().code()
        );

        assertEquals("non-empty-sorted-set-empty", NonEmptySortedSet.ofStream(Stream.<Integer>empty()).getError().code());
        assertEquals(
            "non-empty-navigable-set-empty",
            NonEmptyNavigableSet.ofStream(Stream.<Integer>empty()).getError().code()
        );

        assertEquals("non-empty-sorted-set-null-element", NonEmptySortedSet.ofStream(Stream.of(1, null)).getError().code());
        assertEquals(
            "non-empty-navigable-set-null-element",
            NonEmptyNavigableSet.ofStream(Stream.of(1, null)).getError().code()
        );

        assertEquals("non-empty-sorted-set-invalid-element", NonEmptySortedSet.ofStream(failingElementStream()).getError().code());
        assertEquals(
            "non-empty-sorted-set-invalid-element",
            NonEmptySortedSet.ofStream(Stream.of(new Object()), null).getError().code()
        );
        assertEquals(
            "non-empty-navigable-set-invalid-element",
            NonEmptyNavigableSet.ofStream(failingElementStream()).getError().code()
        );
        assertEquals(
            "non-empty-navigable-set-invalid-element",
            NonEmptyNavigableSet.ofStream(Stream.of(new Object()), null).getError().code()
        );

        assertThrows(RefinementException.class, () -> NonEmptySortedSet.unsafeOfStream(Stream.<Integer>empty()));
        assertThrows(RefinementException.class, () -> NonEmptySortedSet.unsafeOfStream(Stream.<Integer>empty(), Collections.<Integer>reverseOrder()));
        assertThrows(RefinementException.class, () -> NonEmptyNavigableSet.unsafeOfStream(Stream.<Integer>empty()));
        assertThrows(
            RefinementException.class,
            () -> NonEmptyNavigableSet.unsafeOfStream(Stream.<Integer>empty(), Collections.<Integer>reverseOrder())
        );
    }

    private static Stream<Integer> failingElementStream() {
        return StreamSupport.stream(
            Spliterators.spliteratorUnknownSize(new Iterator<Integer>() {
                @Override
                public boolean hasNext() {
                    return true;
                }

                @Override
                public Integer next() {
                    throw new IllegalStateException("boom");
                }
            }, 0),
            false
        );
    }

    @SuppressWarnings("unchecked")
    private static <K, V> Stream<Map.Entry<K, V>> failingEntryStream() {
        return StreamSupport.stream(
            Spliterators.spliteratorUnknownSize(new Iterator<Map.Entry<K, V>>() {
                @Override
                public boolean hasNext() {
                    return true;
                }

                @Override
                public Map.Entry<K, V> next() {
                    throw new IllegalStateException("boom");
                }
            }, 0),
            false
        );
    }

    @SuppressWarnings("unchecked")
    private static <K, V> Stream<Map.Entry<K, V>> entryStream(Object... values) {
        if (values.length % 2 != 0) {
            throw new IllegalArgumentException("values must contain key/value pairs");
        }
        Stream<Map.Entry<K, V>> stream = Stream.empty();
        for (int index = 0; index < values.length; index += 2) {
            K key = (K) values[index];
            V value = (V) values[index + 1];
            stream = Stream.concat(stream, Stream.of(entry(key, value)));
        }
        return stream;
    }

    private static <K, V> Map.Entry<K, V> entry(K key, V value) {
        return new AbstractMap.SimpleImmutableEntry<K, V>(key, value);
    }
}
