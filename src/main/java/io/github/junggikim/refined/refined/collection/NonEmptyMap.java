package io.github.junggikim.refined.refined.collection;

import java.util.AbstractMap;
import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public final class NonEmptyMap<K, V> extends AbstractMap<K, V> {

    private final Map<K, V> entries;

    private NonEmptyMap(Map<K, V> entries) {
        this.entries = entries;
    }

    public static <K, V> Validation<Violation, NonEmptyMap<K, V>> of(Map<K, V> value) {
        return RefinedSupport.nonEmptyMapSnapshot(value).map(NonEmptyMap::new);
    }

    public static <K, V> NonEmptyMap<K, V> unsafeOf(Map<K, V> value) {
        Validation<Violation, NonEmptyMap<K, V>> result = of(value);
        if (result.isValid()) {
            return result.get();
        }
        throw new RefinementException(result.getError());
    }

    public static <K, V> Validation<Violation, NonEmptyMap<K, V>> of(Stream<Map.Entry<K, V>> value) {
        return ofEntryStream(value);
    }

    public static <K, V> NonEmptyMap<K, V> unsafeOf(Stream<Map.Entry<K, V>> value) {
        return unsafeOfEntryStream(value);
    }

    public static <K, V> Validation<Violation, NonEmptyMap<K, V>> ofEntryStream(
        Stream<Map.Entry<K, V>> value
    ) {
        return RefinedSupport.nonEmptyMapEntryStreamSnapshot(value).map(NonEmptyMap::new);
    }

    public static <K, V> NonEmptyMap<K, V> unsafeOfEntryStream(
        Stream<Map.Entry<K, V>> value
    ) {
        Validation<Violation, NonEmptyMap<K, V>> result = ofEntryStream(value);
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
}
