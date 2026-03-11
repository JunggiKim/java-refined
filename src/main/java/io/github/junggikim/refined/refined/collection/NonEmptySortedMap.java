package io.github.junggikim.refined.refined.collection;

import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.SortedMap;

public final class NonEmptySortedMap<K, V> extends AbstractRefined<SortedMap<K, V>> implements Newtype<SortedMap<K, V>> {

    private NonEmptySortedMap(SortedMap<K, V> value) {
        super(value);
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

    public K firstKey() {
        return value().firstKey();
    }

    public K lastKey() {
        return value().lastKey();
    }
}
