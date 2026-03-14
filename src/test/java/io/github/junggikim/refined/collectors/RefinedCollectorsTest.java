package io.github.junggikim.refined.collectors;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class RefinedCollectorsTest {

    @Test
    void linearCollectorsProduceRefinedCollections() {
        NonEmptyList<String> list = Stream.of("a", "b").collect(RefinedCollectors.toNonEmptyList());
        NonEmptySet<String> set = Stream.of("a", "b").collect(RefinedCollectors.toNonEmptySet());
        NonEmptyQueue<String> queue = Stream.of("a", "b").collect(RefinedCollectors.toNonEmptyQueue());
        NonEmptyDeque<String> deque = Stream.of("a", "b").collect(RefinedCollectors.toNonEmptyDeque());
        NonEmptyIterable<String> iterable = Stream.of("a", "b").collect(RefinedCollectors.toNonEmptyIterable());

        assertEquals(listOf("a", "b"), list);
        assertEquals(setOf("a", "b"), set);
        assertEquals(listOf("a", "b"), queue);
        assertEquals(listOf("a", "b"), deque);
        assertEquals(listOf("a", "b"), iterable);

        Collector<String, List<String>, NonEmptyList<String>> collector = RefinedCollectors.toNonEmptyList();
        List<String> merged = collector.combiner().apply(new ArrayList<String>(listOf("x")), new ArrayList<String>(listOf("y")));
        assertEquals(listOf("x", "y"), merged);
    }

    @Test
    void sortedAndNavigableSetCollectorsRespectOrdering() {
        NonEmptySortedSet<Integer> naturalSortedSet =
            Stream.of(2, 1).collect(RefinedCollectors.toNonEmptySortedSet());
        NonEmptySortedSet<Integer> reverseSortedSet =
            Stream.of(2, 1).collect(RefinedCollectors.toNonEmptySortedSet(Collections.reverseOrder()));
        NonEmptyNavigableSet<Integer> naturalNavigableSet =
            Stream.of(2, 1).collect(RefinedCollectors.toNonEmptyNavigableSet());
        NonEmptyNavigableSet<Integer> reverseNavigableSet =
            Stream.of(2, 1).collect(RefinedCollectors.toNonEmptyNavigableSet(Collections.reverseOrder()));

        assertEquals(listOf(1, 2), snapshot(naturalSortedSet));
        assertEquals(listOf(2, 1), snapshot(reverseSortedSet));
        assertEquals(listOf(1, 2), snapshot(naturalNavigableSet));
        assertEquals(listOf(2, 1), snapshot(reverseNavigableSet));
    }

    @Test
    void entryBasedMapCollectorsWork() {
        NonEmptyMap<String, Integer> map = Stream.of(entry("a", 1), entry("b", 2))
            .collect(RefinedCollectors.toNonEmptyMap());
        NonEmptySortedMap<Integer, String> sortedMap = Stream.of(entry(2, "two"), entry(1, "one"))
            .collect(RefinedCollectors.toNonEmptySortedMap());
        NonEmptySortedMap<Integer, String> reverseSortedMap = Stream.of(entry(2, "two"), entry(1, "one"))
            .collect(RefinedCollectors.toNonEmptySortedMap(Collections.<Integer>reverseOrder()));
        NonEmptyNavigableMap<Integer, String> navigableMap = Stream.of(entry(2, "two"), entry(1, "one"))
            .collect(RefinedCollectors.toNonEmptyNavigableMap());
        NonEmptyNavigableMap<Integer, String> reverseNavigableMap = Stream.of(entry(2, "two"), entry(1, "one"))
            .collect(RefinedCollectors.toNonEmptyNavigableMap(Collections.<Integer>reverseOrder()));

        assertEquals(mapOf("a", 1, "b", 2), map);
        assertEquals(listOf(1, 2), snapshot(sortedMap.keySet()));
        assertEquals(listOf(2, 1), snapshot(reverseSortedMap.keySet()));
        assertEquals(listOf(1, 2), snapshot(navigableMap.keySet()));
        assertEquals(listOf(2, 1), snapshot(reverseNavigableMap.keySet()));
    }

    @Test
    void mapperBasedMapCollectorsWork() {
        NonEmptyMap<String, Integer> map = Stream.of("a", "bb")
            .collect(RefinedCollectors.toNonEmptyMap(value -> value, String::length));
        NonEmptyMap<String, Integer> duplicateKeys = Stream.of("first", "second")
            .collect(RefinedCollectors.toNonEmptyMap(value -> "k", String::length));
        NonEmptySortedMap<String, Integer> sortedMap = Stream.of("b", "a")
            .collect(RefinedCollectors.toNonEmptySortedMap(value -> value, String::length));
        NonEmptySortedMap<String, Integer> reverseSortedMap = Stream.of("b", "a")
            .collect(RefinedCollectors.toNonEmptySortedMap(value -> value, String::length, Collections.reverseOrder()));
        NonEmptyNavigableMap<String, Integer> navigableMap = Stream.of("b", "a")
            .collect(RefinedCollectors.toNonEmptyNavigableMap(value -> value, String::length));
        NonEmptyNavigableMap<String, Integer> reverseNavigableMap = Stream.of("b", "a")
            .collect(RefinedCollectors.toNonEmptyNavigableMap(value -> value, String::length, Collections.reverseOrder()));

        assertEquals(mapOf("a", 1, "bb", 2), map);
        assertEquals(mapOf("k", 6), duplicateKeys);
        assertEquals(listOf("a", "b"), snapshot(sortedMap.keySet()));
        assertEquals(listOf("b", "a"), snapshot(reverseSortedMap.keySet()));
        assertEquals(listOf("a", "b"), snapshot(navigableMap.keySet()));
        assertEquals(listOf("b", "a"), snapshot(reverseNavigableMap.keySet()));
    }

    @Test
    void collectorsThrowOnEmptyOrNullValues() {
        assertThrows(RefinementException.class, () -> Stream.<Integer>empty().collect(RefinedCollectors.toNonEmptyList()));
        assertThrows(RefinementException.class, () -> Stream.of(1, null).collect(RefinedCollectors.toNonEmptySet()));
        assertThrows(
            RefinementException.class,
            () -> Stream.<Map.Entry<String, Integer>>of((Map.Entry<String, Integer>) null).collect(RefinedCollectors.toNonEmptyMap())
        );
        assertThrows(
            RefinementException.class,
            () -> Stream.of("x").collect(RefinedCollectors.toNonEmptyMap(value -> null, String::length))
        );
    }

    private static <K, V> Map.Entry<K, V> entry(K key, V value) {
        return new AbstractMap.SimpleImmutableEntry<K, V>(key, value);
    }
}
