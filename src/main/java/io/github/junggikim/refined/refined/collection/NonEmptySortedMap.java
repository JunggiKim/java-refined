package io.github.junggikim.refined.refined.collection;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.stream.Stream;
import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** A non-empty immutable {@code SortedMap} snapshot with no null keys or values. */
public final class NonEmptySortedMap<K, V> extends AbstractMap<K, V> implements SortedMap<K, V> {

    private final SortedMap<K, V> entries;

    private NonEmptySortedMap(SortedMap<K, V> entries) {
        this.entries = entries;
    }

    public static <K, V> Validation<Violation, NonEmptySortedMap<K, V>> of(SortedMap<K, V> value) {
        return RefinedSupport.nonEmptySortedMapSnapshot(value).map(NonEmptySortedMap::new);
    }

    public static <K, V> NonEmptySortedMap<K, V> unsafeOf(SortedMap<K, V> value) {
        Validation<Violation, NonEmptySortedMap<K, V>> result = of(value);
        if (result.isValid()) {
            return result.get();
        }
        throw new RefinementException(result.getError());
    }

    public static <K extends Comparable<? super K>, V> Validation<Violation, NonEmptySortedMap<K, V>> of(
        Stream<Map.Entry<K, V>> value
    ) {
        return ofEntryStream(value);
    }

    public static <K, V> Validation<Violation, NonEmptySortedMap<K, V>> of(
        Stream<Map.Entry<K, V>> value,
        Comparator<? super K> comparator
    ) {
        return ofEntryStream(value, comparator);
    }

    public static <K extends Comparable<? super K>, V> NonEmptySortedMap<K, V> unsafeOf(Stream<Map.Entry<K, V>> value) {
        return unsafeOfEntryStream(value);
    }

    public static <K, V> NonEmptySortedMap<K, V> unsafeOf(Stream<Map.Entry<K, V>> value, Comparator<? super K> comparator) {
        return unsafeOfEntryStream(value, comparator);
    }

    public static <K extends Comparable<? super K>, V> Validation<Violation, NonEmptySortedMap<K, V>> ofEntryStream(
        Stream<Map.Entry<K, V>> value
    ) {
        return RefinedSupport.nonEmptySortedMapEntryStreamSnapshot(value).map(NonEmptySortedMap::new);
    }

    public static <K, V> Validation<Violation, NonEmptySortedMap<K, V>> ofEntryStream(
        Stream<Map.Entry<K, V>> value,
        Comparator<? super K> comparator
    ) {
        return RefinedSupport.nonEmptySortedMapEntryStreamSnapshot(value, comparator).map(NonEmptySortedMap::new);
    }

    public static <K extends Comparable<? super K>, V> NonEmptySortedMap<K, V> unsafeOfEntryStream(
        Stream<Map.Entry<K, V>> value
    ) {
        Validation<Violation, NonEmptySortedMap<K, V>> result = ofEntryStream(value);
        if (result.isValid()) {
            return result.get();
        }
        throw new RefinementException(result.getError());
    }

    public static <K, V> NonEmptySortedMap<K, V> unsafeOfEntryStream(
        Stream<Map.Entry<K, V>> value,
        Comparator<? super K> comparator
    ) {
        Validation<Violation, NonEmptySortedMap<K, V>> result = ofEntryStream(value, comparator);
        if (result.isValid()) {
            return result.get();
        }
        throw new RefinementException(result.getError());
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return entries.entrySet();
    }

    @Override
    public Comparator<? super K> comparator() {
        return entries.comparator();
    }

    @Override
    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        return entries.subMap(fromKey, toKey);
    }

    @Override
    public SortedMap<K, V> headMap(K toKey) {
        return entries.headMap(toKey);
    }

    @Override
    public SortedMap<K, V> tailMap(K fromKey) {
        return entries.tailMap(fromKey);
    }

    @Override
    public K firstKey() {
        return entries.firstKey();
    }

    @Override
    public K lastKey() {
        return entries.lastKey();
    }

    @Override
    public V get(Object key) {
        return entries.get(key);
    }

    @Override
    public boolean containsKey(Object key) {
        return entries.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return entries.containsValue(value);
    }

    @Override
    public int size() {
        return entries.size();
    }
}
