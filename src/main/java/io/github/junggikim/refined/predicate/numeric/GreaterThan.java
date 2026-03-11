package io.github.junggikim.refined.predicate.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Objects;

public final class GreaterThan<T extends Comparable<T>> implements Constraint<T> {

    private final Constraint<T> delegate;

    public GreaterThan(T minimum) {
        T bound = Objects.requireNonNull(minimum, "minimum");
        this.delegate = RefinedSupport.greaterThan(bound, "greater-than", "value must be greater than " + bound);
    }

    @Override
    public Validation<Violation, T> validate(T value) {
        return delegate.validate(value);
    }
}
