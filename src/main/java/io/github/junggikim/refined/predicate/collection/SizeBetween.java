package io.github.junggikim.refined.predicate.collection;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Collection;

public final class SizeBetween<T extends Collection<?>> implements Constraint<T> {

    private final Constraint<T> delegate;

    public SizeBetween(int minimum, int maximum) {
        if (minimum > maximum) {
            throw new IllegalArgumentException("size-between minimum must be less than or equal to maximum");
        }
        this.delegate = RefinedSupport.require(
            "size-between",
            "collection size must be between " + minimum + " and " + maximum,
            value -> value.size() >= minimum && value.size() <= maximum
        );
    }

    @Override
    public Validation<Violation, T> validate(T value) {
        return delegate.validate(value);
    }
}
