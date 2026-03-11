package io.github.junggikim.refined.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class TestCollections {

    private TestCollections() {
    }

    @SafeVarargs
    public static <T> List<T> listOf(T... values) {
        return Collections.unmodifiableList(new ArrayList<T>(Arrays.asList(values.clone())));
    }

    @SafeVarargs
    public static <T> Set<T> setOf(T... values) {
        LinkedHashSet<T> set = new LinkedHashSet<T>();
        Collections.addAll(set, values.clone());
        return Collections.unmodifiableSet(set);
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> mapOf(Object... values) {
        if (values.length % 2 != 0) {
            throw new IllegalArgumentException("values must contain key/value pairs");
        }
        LinkedHashMap<K, V> map = new LinkedHashMap<K, V>();
        for (int index = 0; index < values.length; index += 2) {
            map.put((K) values[index], (V) values[index + 1]);
        }
        return Collections.unmodifiableMap(map);
    }

    public static <T> List<T> snapshot(Iterable<? extends T> values) {
        ArrayList<T> copy = new ArrayList<T>();
        for (T value : values) {
            copy.add(value);
        }
        return Collections.unmodifiableList(copy);
    }
}
