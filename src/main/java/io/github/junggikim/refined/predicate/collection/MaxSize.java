package io.github.junggikim.refined.predicate.collection;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Collection;

public final class MaxSize<T extends Collection<?>> implements Constraint<T> {

    private final Constraint<T> delegate;

    public MaxSize(int maximum) {
        this.delegate = RefinedSupport.require("max-size", "collection size must be at most " + maximum, value -> value.size() <= maximum);
    }

    @Override
    public Validation<Violation, T> validate(T value) {
        return delegate.validate(value);
    }
}
