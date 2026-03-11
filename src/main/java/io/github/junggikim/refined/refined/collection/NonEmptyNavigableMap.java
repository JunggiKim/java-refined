package io.github.junggikim.refined.refined.collection;

import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.NavigableMap;

public final class NonEmptyNavigableMap<K, V> extends AbstractRefined<NavigableMap<K, V>> implements Newtype<NavigableMap<K, V>> {

    private NonEmptyNavigableMap(NavigableMap<K, V> value) {
        super(value);
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

    public K firstKey() {
        return value().firstKey();
    }

    public K lastKey() {
        return value().lastKey();
    }
}
