package io.github.junggikim.refined.refined.collection;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.stream.Stream;
import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class NonEmptyNavigableMap<K, V> extends AbstractMap<K, V> implements NavigableMap<K, V> {

    private final NavigableMap<K, V> entries;

    private NonEmptyNavigableMap(NavigableMap<K, V> entries) {
        this.entries = entries;
    }

    public static <K, V> Validation<Violation, NonEmptyNavigableMap<K, V>> of(NavigableMap<K, V> value) {
        return RefinedSupport.nonEmptyNavigableMapSnapshot(value).map(NonEmptyNavigableMap::new);
    }

    public static <K, V> NonEmptyNavigableMap<K, V> unsafeOf(NavigableMap<K, V> value) {
        Validation<Violation, NonEmptyNavigableMap<K, V>> result = of(value);
        if (result.isValid()) {
            return result.get();
        }
        throw new RefinementException(result.getError());
    }

    public static <K extends Comparable<? super K>, V> Validation<Violation, NonEmptyNavigableMap<K, V>> of(
        Stream<Map.Entry<K, V>> value
    ) {
        return ofEntryStream(value);
    }

    public static <K, V> Validation<Violation, NonEmptyNavigableMap<K, V>> of(
        Stream<Map.Entry<K, V>> value,
        Comparator<? super K> comparator
    ) {
        return ofEntryStream(value, comparator);
    }

    public static <K extends Comparable<? super K>, V> NonEmptyNavigableMap<K, V> unsafeOf(
        Stream<Map.Entry<K, V>> value
    ) {
        return unsafeOfEntryStream(value);
    }

    public static <K, V> NonEmptyNavigableMap<K, V> unsafeOf(Stream<Map.Entry<K, V>> value, Comparator<? super K> comparator) {
        return unsafeOfEntryStream(value, comparator);
    }

    public static <K extends Comparable<? super K>, V> Validation<Violation, NonEmptyNavigableMap<K, V>> ofEntryStream(
        Stream<Map.Entry<K, V>> value
    ) {
        return RefinedSupport.nonEmptyNavigableMapEntryStreamSnapshot(value).map(NonEmptyNavigableMap::new);
    }

    public static <K, V> Validation<Violation, NonEmptyNavigableMap<K, V>> ofEntryStream(
        Stream<Map.Entry<K, V>> value,
        Comparator<? super K> comparator
    ) {
        return RefinedSupport.nonEmptyNavigableMapEntryStreamSnapshot(value, comparator).map(NonEmptyNavigableMap::new);
    }

    public static <K extends Comparable<? super K>, V> NonEmptyNavigableMap<K, V> unsafeOfEntryStream(
        Stream<Map.Entry<K, V>> value
    ) {
        Validation<Violation, NonEmptyNavigableMap<K, V>> result = ofEntryStream(value);
        if (result.isValid()) {
            return result.get();
        }
        throw new RefinementException(result.getError());
    }

    public static <K, V> NonEmptyNavigableMap<K, V> unsafeOfEntryStream(
        Stream<Map.Entry<K, V>> value,
        Comparator<? super K> comparator
    ) {
        Validation<Violation, NonEmptyNavigableMap<K, V>> result = ofEntryStream(value, comparator);
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

    @Override
    public Comparator<? super K> comparator() {
        return entries.comparator();
    }

    @Override
    public Map.Entry<K, V> lowerEntry(K key) {
        return entries.lowerEntry(key);
    }

    @Override
    public K lowerKey(K key) {
        return entries.lowerKey(key);
    }

    @Override
    public Map.Entry<K, V> floorEntry(K key) {
        return entries.floorEntry(key);
    }

    @Override
    public K floorKey(K key) {
        return entries.floorKey(key);
    }

    @Override
    public Map.Entry<K, V> ceilingEntry(K key) {
        return entries.ceilingEntry(key);
    }

    @Override
    public K ceilingKey(K key) {
        return entries.ceilingKey(key);
    }

    @Override
    public Map.Entry<K, V> higherEntry(K key) {
        return entries.higherEntry(key);
    }

    @Override
    public K higherKey(K key) {
        return entries.higherKey(key);
    }

    @Override
    public Map.Entry<K, V> firstEntry() {
        return entries.firstEntry();
    }

    @Override
    public Map.Entry<K, V> lastEntry() {
        return entries.lastEntry();
    }

    @Override
    public Map.Entry<K, V> pollFirstEntry() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map.Entry<K, V> pollLastEntry() {
        throw new UnsupportedOperationException();
    }

    @Override
    public NavigableMap<K, V> descendingMap() {
        return entries.descendingMap();
    }

    @Override
    public NavigableSet<K> navigableKeySet() {
        return entries.navigableKeySet();
    }

    @Override
    public NavigableSet<K> descendingKeySet() {
        return entries.descendingKeySet();
    }

    @Override
    public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        return entries.subMap(fromKey, fromInclusive, toKey, toInclusive);
    }

    @Override
    public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
        return entries.headMap(toKey, inclusive);
    }

    @Override
    public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
        return entries.tailMap(fromKey, inclusive);
    }

    @Override
    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        return entries.subMap(fromKey, true, toKey, false);
    }

    @Override
    public SortedMap<K, V> headMap(K toKey) {
        return entries.headMap(toKey, false);
    }

    @Override
    public SortedMap<K, V> tailMap(K fromKey) {
        return entries.tailMap(fromKey, true);
    }

    @Override
    public K firstKey() {
        return entries.firstKey();
    }

    @Override
    public K lastKey() {
        return entries.lastKey();
    }
}
