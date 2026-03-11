package io.github.junggikim.refined.refined.collection;

import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Map;

public final class NonEmptyMap<K, V> extends AbstractRefined<Map<K, V>> implements Newtype<Map<K, V>> {

    private NonEmptyMap(Map<K, V> value) {
        super(value);
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
}
