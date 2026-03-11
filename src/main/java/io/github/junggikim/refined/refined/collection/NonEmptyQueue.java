package io.github.junggikim.refined.refined.collection;

import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.List;
import java.util.Queue;

public final class NonEmptyQueue<T> extends AbstractRefined<List<T>> implements Newtype<List<T>> {

    private NonEmptyQueue(List<T> value) {
        super(value);
    }

    public static <T> Validation<Violation, NonEmptyQueue<T>> of(Queue<T> value) {
        return RefinedSupport.nonEmptyQueueSnapshot(value).map(NonEmptyQueue::new);
    }

    public static <T> NonEmptyQueue<T> unsafeOf(Queue<T> value) {
        Validation<Violation, NonEmptyQueue<T>> result = of(value);
        if (result.isValid()) {
            return result.get();
        }
        throw new RefinementException(result.getError());
    }

    public T peek() {
        return value().get(0);
    }

    public T last() {
        return value().get(value().size() - 1);
    }
}
