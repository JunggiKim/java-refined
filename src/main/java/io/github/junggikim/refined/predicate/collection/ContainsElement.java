package io.github.junggikim.refined.predicate.collection;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Collection;

public final class ContainsElement<T> implements Constraint<Collection<T>> {

    private final Constraint<Collection<T>> delegate;

    public ContainsElement(T expected) {
        this.delegate = RefinedSupport.require("contains-element", "collection must contain the expected element", value -> value.contains(expected));
    }

    @Override
    public Validation<Violation, Collection<T>> validate(Collection<T> value) {
        return delegate.validate(value);
    }
}
