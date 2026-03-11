package io.github.junggikim.refined.predicate.collection;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public final class IndexSatisfies<T> implements Constraint<List<T>> {

    private final Constraint<List<T>> delegate;

    public IndexSatisfies(int index, Predicate<T> predicate) {
        Predicate<T> checked = Objects.requireNonNull(predicate, "predicate");
        this.delegate = RefinedSupport.require(
            "index-satisfies",
            "the indexed element must satisfy the predicate",
            value -> index >= 0 && index < value.size() && checked.test(value.get(index))
        );
    }

    @Override
    public Validation<Violation, List<T>> validate(List<T> value) {
        return delegate.validate(value);
    }
}
