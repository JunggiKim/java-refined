package io.github.junggikim.refined.refined.collection;

import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Deque;
import java.util.List;

public final class NonEmptyDeque<T> extends AbstractRefined<List<T>> implements Newtype<List<T>> {

    private NonEmptyDeque(List<T> value) {
        super(value);
    }

    public static <T> Validation<Violation, NonEmptyDeque<T>> of(Deque<T> value) {
        return RefinedSupport.nonEmptyDequeSnapshot(value).map(NonEmptyDeque::new);
    }

    public static <T> NonEmptyDeque<T> unsafeOf(Deque<T> value) {
        Validation<Violation, NonEmptyDeque<T>> result = of(value);
        if (result.isValid()) {
            return result.get();
        }
        throw new RefinementException(result.getError());
    }

    public T first() {
        return value().get(0);
    }

    public T last() {
        return value().get(value().size() - 1);
    }
}
