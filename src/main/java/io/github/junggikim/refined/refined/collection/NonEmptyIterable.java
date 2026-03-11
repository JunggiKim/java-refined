package io.github.junggikim.refined.refined.collection;

import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.List;

public final class NonEmptyIterable<T> extends AbstractRefined<List<T>> implements Newtype<List<T>>, Iterable<T> {

    private NonEmptyIterable(List<T> value) {
        super(value);
    }

    public static <T> Validation<Violation, NonEmptyIterable<T>> of(Iterable<T> value) {
        return RefinedSupport.nonEmptyIterableSnapshot(value).map(NonEmptyIterable::new);
    }

    public static <T> NonEmptyIterable<T> unsafeOf(Iterable<T> value) {
        Validation<Violation, NonEmptyIterable<T>> result = of(value);
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

    @Override
    public java.util.Iterator<T> iterator() {
        return value().iterator();
    }
}
