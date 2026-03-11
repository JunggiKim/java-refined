package io.github.junggikim.refined.refined.collection;

import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.SortedSet;

public final class NonEmptySortedSet<T> extends AbstractRefined<SortedSet<T>> implements Newtype<SortedSet<T>> {

    private NonEmptySortedSet(SortedSet<T> value) {
        super(value);
    }

    public static <T> Validation<Violation, NonEmptySortedSet<T>> of(SortedSet<T> value) {
        return RefinedSupport.nonEmptySortedSetSnapshot(value).map(NonEmptySortedSet::new);
    }

    public static <T> NonEmptySortedSet<T> unsafeOf(SortedSet<T> value) {
        Validation<Violation, NonEmptySortedSet<T>> result = of(value);
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
