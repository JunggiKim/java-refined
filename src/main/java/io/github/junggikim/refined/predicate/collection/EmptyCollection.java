package io.github.junggikim.refined.predicate.collection;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Collection;

public final class EmptyCollection<T extends Collection<?>> implements Constraint<T> {

    private final Constraint<T> delegate;

    public EmptyCollection() {
        this.delegate = RefinedSupport.require("empty-collection", "collection must be empty", Collection::isEmpty);
    }

    @Override
    public Validation<Violation, T> validate(T value) {
        return delegate.validate(value);
    }
}
