package io.github.junggikim.refined.refined.collection;

import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.List;

public final class NonEmptyList<T> extends AbstractRefined<List<T>> implements Newtype<List<T>> {

    private NonEmptyList(List<T> value) {
        super(value);
    }

    public static <T> Validation<Violation, NonEmptyList<T>> of(List<T> value) {
        return RefinedSupport.nonEmptyListSnapshot(value).map(NonEmptyList::new);
    }

    public static <T> NonEmptyList<T> unsafeOf(List<T> value) {
        Validation<Violation, NonEmptyList<T>> result = of(value);
        if (result.isValid()) {
            return result.get();
        }
        throw new RefinementException(result.getError());
    }

    public T head() {
        return value().get(0);
    }

    public List<T> tail() {
        return value().subList(1, value().size());
    }
}
