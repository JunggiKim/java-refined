package io.github.junggikim.refined.predicate.collection;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Collection;

public final class MinSize<T extends Collection<?>> implements Constraint<T> {

    private final Constraint<T> delegate;

    public MinSize(int minimum) {
        this.delegate = RefinedSupport.require("min-size", "collection size must be at least " + minimum, value -> value.size() >= minimum);
    }

    @Override
    public Validation<Violation, T> validate(T value) {
        return delegate.validate(value);
    }
}
