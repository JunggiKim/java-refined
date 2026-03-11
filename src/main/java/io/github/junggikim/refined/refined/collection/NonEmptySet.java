package io.github.junggikim.refined.refined.collection;

import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Set;

public final class NonEmptySet<T> extends AbstractRefined<Set<T>> implements Newtype<Set<T>> {

    private NonEmptySet(Set<T> value) {
        super(value);
    }

    public static <T> Validation<Violation, NonEmptySet<T>> of(Set<T> value) {
        return RefinedSupport.nonEmptySetSnapshot(value).map(NonEmptySet::new);
    }

    public static <T> NonEmptySet<T> unsafeOf(Set<T> value) {
        Validation<Violation, NonEmptySet<T>> result = of(value);
        if (result.isValid()) {
            return result.get();
        }
        throw new RefinementException(result.getError());
    }
}
