package io.github.junggikim.refined.predicate.collection;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public final class InitSatisfies<T> implements Constraint<List<T>> {

    private final Constraint<List<T>> delegate;

    public InitSatisfies(Predicate<List<T>> predicate) {
        Predicate<List<T>> checked = Objects.requireNonNull(predicate, "predicate");
        this.delegate = RefinedSupport.require(
            "init-satisfies",
            "the init slice must satisfy the predicate",
            value -> !value.isEmpty() && checked.test(RefinedSupport.copyListSlice(value, 0, value.size() - 1))
        );
    }

    @Override
    public Validation<Violation, List<T>> validate(List<T> value) {
        return delegate.validate(value);
    }
}
