package io.github.junggikim.refined.collectors;

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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;

public final class RefinedCollectors {

    private RefinedCollectors() {
    }

    public static <T> Collector<T, List<T>, NonEmptyList<T>> toNonEmptyList() {
        return Collector.of(
            ArrayList::new,
            List::add,
            RefinedCollectors::concat,
            values -> NonEmptyList.unsafeOfStream(values.stream())
        );
    }

    public static <T> Collector<T, List<T>, NonEmptySet<T>> toNonEmptySet() {
        return Collector.of(
            ArrayList::new,
            List::add,
            RefinedCollectors::concat,
            values -> NonEmptySet.unsafeOfStream(values.stream())
        );
    }

    public static <T> Collector<T, List<T>, NonEmptyQueue<T>> toNonEmptyQueue() {
        return Collector.of(
            ArrayList::new,
            List::add,
            RefinedCollectors::concat,
            values -> NonEmptyQueue.unsafeOfStream(values.stream())
        );
    }

    public static <T> Collector<T, List<T>, NonEmptyDeque<T>> toNonEmptyDeque() {
        return Collector.of(
            ArrayList::new,
            List::add,
            RefinedCollectors::concat,
            values -> NonEmptyDeque.unsafeOfStream(values.stream())
        );
    }

    public static <T> Collector<T, List<T>, NonEmptyIterable<T>> toNonEmptyIterable() {
        return Collector.of(
            ArrayList::new,
            List::add,
            RefinedCollectors::concat,
            values -> NonEmptyIterable.unsafeOfStream(values.stream())
        );
    }

    public static <T extends Comparable<? super T>> Collector<T, List<T>, NonEmptySortedSet<T>> toNonEmptySortedSet() {
        return Collector.of(
            ArrayList::new,
            List::add,
            RefinedCollectors::concat,
            values -> NonEmptySortedSet.unsafeOfStream(values.stream())
        );
    }

    public static <T> Collector<T, List<T>, NonEmptySortedSet<T>> toNonEmptySortedSet(Comparator<? super T> comparator) {
        return Collector.of(
            ArrayList::new,
            List::add,
            RefinedCollectors::concat,
            values -> NonEmptySortedSet.unsafeOfStream(values.stream(), comparator)
        );
    }

    public static <T extends Comparable<? super T>> Collector<T, List<T>, NonEmptyNavigableSet<T>> toNonEmptyNavigableSet() {
        return Collector.of(
            ArrayList::new,
            List::add,
            RefinedCollectors::concat,
            values -> NonEmptyNavigableSet.unsafeOfStream(values.stream())
        );
    }

    public static <T> Collector<T, List<T>, NonEmptyNavigableSet<T>> toNonEmptyNavigableSet(Comparator<? super T> comparator) {
        return Collector.of(
            ArrayList::new,
            List::add,
            RefinedCollectors::concat,
            values -> NonEmptyNavigableSet.unsafeOfStream(values.stream(), comparator)
        );
    }

    public static <K, V> Collector<Map.Entry<K, V>, List<Map.Entry<K, V>>, NonEmptyMap<K, V>> toNonEmptyMap() {
        return Collector.of(
            ArrayList::new,
            List::add,
            RefinedCollectors::concat,
            entries -> NonEmptyMap.unsafeOfEntryStream(entries.stream())
        );
    }

    public static <T, K, V> Collector<T, List<Map.Entry<K, V>>, NonEmptyMap<K, V>> toNonEmptyMap(
        Function<? super T, ? extends K> keyMapper,
        Function<? super T, ? extends V> valueMapper
    ) {
        return Collector.of(
            ArrayList::new,
            (entries, value) -> entries.add(entry(keyMapper.apply(value), valueMapper.apply(value))),
            RefinedCollectors::concat,
            entries -> NonEmptyMap.unsafeOfEntryStream(entries.stream())
        );
    }

    public static <K extends Comparable<? super K>, V> Collector<Map.Entry<K, V>, List<Map.Entry<K, V>>, NonEmptySortedMap<K, V>> toNonEmptySortedMap() {
        return Collector.of(
            ArrayList::new,
            List::add,
            RefinedCollectors::concat,
            entries -> NonEmptySortedMap.unsafeOfEntryStream(entries.stream())
        );
    }

    public static <K, V> Collector<Map.Entry<K, V>, List<Map.Entry<K, V>>, NonEmptySortedMap<K, V>> toNonEmptySortedMap(
        Comparator<? super K> comparator
    ) {
        return Collector.of(
            ArrayList::new,
            List::add,
            RefinedCollectors::concat,
            entries -> NonEmptySortedMap.unsafeOfEntryStream(entries.stream(), comparator)
        );
    }

    public static <T, K extends Comparable<? super K>, V> Collector<T, List<Map.Entry<K, V>>, NonEmptySortedMap<K, V>> toNonEmptySortedMap(
        Function<? super T, ? extends K> keyMapper,
        Function<? super T, ? extends V> valueMapper
    ) {
        return Collector.of(
            ArrayList::new,
            (entries, value) -> entries.add(entry(keyMapper.apply(value), valueMapper.apply(value))),
            RefinedCollectors::concat,
            entries -> NonEmptySortedMap.unsafeOfEntryStream(entries.stream())
        );
    }

    public static <T, K, V> Collector<T, List<Map.Entry<K, V>>, NonEmptySortedMap<K, V>> toNonEmptySortedMap(
        Function<? super T, ? extends K> keyMapper,
        Function<? super T, ? extends V> valueMapper,
        Comparator<? super K> comparator
    ) {
        return Collector.of(
            ArrayList::new,
            (entries, value) -> entries.add(entry(keyMapper.apply(value), valueMapper.apply(value))),
            RefinedCollectors::concat,
            entries -> NonEmptySortedMap.unsafeOfEntryStream(entries.stream(), comparator)
        );
    }

    public static <K extends Comparable<? super K>, V> Collector<Map.Entry<K, V>, List<Map.Entry<K, V>>, NonEmptyNavigableMap<K, V>> toNonEmptyNavigableMap() {
        return Collector.of(
            ArrayList::new,
            List::add,
            RefinedCollectors::concat,
            entries -> NonEmptyNavigableMap.unsafeOfEntryStream(entries.stream())
        );
    }

    public static <K, V> Collector<Map.Entry<K, V>, List<Map.Entry<K, V>>, NonEmptyNavigableMap<K, V>> toNonEmptyNavigableMap(
        Comparator<? super K> comparator
    ) {
        return Collector.of(
            ArrayList::new,
            List::add,
            RefinedCollectors::concat,
            entries -> NonEmptyNavigableMap.unsafeOfEntryStream(entries.stream(), comparator)
        );
    }

    public static <T, K extends Comparable<? super K>, V> Collector<T, List<Map.Entry<K, V>>, NonEmptyNavigableMap<K, V>> toNonEmptyNavigableMap(
        Function<? super T, ? extends K> keyMapper,
        Function<? super T, ? extends V> valueMapper
    ) {
        return Collector.of(
            ArrayList::new,
            (entries, value) -> entries.add(entry(keyMapper.apply(value), valueMapper.apply(value))),
            RefinedCollectors::concat,
            entries -> NonEmptyNavigableMap.unsafeOfEntryStream(entries.stream())
        );
    }

    public static <T, K, V> Collector<T, List<Map.Entry<K, V>>, NonEmptyNavigableMap<K, V>> toNonEmptyNavigableMap(
        Function<? super T, ? extends K> keyMapper,
        Function<? super T, ? extends V> valueMapper,
        Comparator<? super K> comparator
    ) {
        return Collector.of(
            ArrayList::new,
            (entries, value) -> entries.add(entry(keyMapper.apply(value), valueMapper.apply(value))),
            RefinedCollectors::concat,
            entries -> NonEmptyNavigableMap.unsafeOfEntryStream(entries.stream(), comparator)
        );
    }

    private static <T> List<T> concat(List<T> left, List<T> right) {
        left.addAll(right);
        return left;
    }

    private static <K, V> Map.Entry<K, V> entry(K key, V value) {
        return new AbstractMap.SimpleImmutableEntry<K, V>(key, value);
    }
}
