package io.github.junggikim.refined.refined.collection;

import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.NavigableSet;

public final class NonEmptyNavigableSet<T> extends AbstractRefined<NavigableSet<T>> implements Newtype<NavigableSet<T>> {

    private NonEmptyNavigableSet(NavigableSet<T> value) {
        super(value);
    }

    public static <T> Validation<Violation, NonEmptyNavigableSet<T>> of(NavigableSet<T> value) {
        return RefinedSupport.nonEmptyNavigableSetSnapshot(value).map(NonEmptyNavigableSet::new);
    }

    public static <T> NonEmptyNavigableSet<T> unsafeOf(NavigableSet<T> value) {
        Validation<Violation, NonEmptyNavigableSet<T>> result = of(value);
        if (result.isValid()) {
            return result.get();
        }
        throw new RefinementException(result.getError());
    }

    public T first() {
        return value().first();
    }

    public T last() {
        return value().last();
    }
}
