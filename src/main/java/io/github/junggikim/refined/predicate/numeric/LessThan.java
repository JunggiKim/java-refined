package io.github.junggikim.refined.predicate.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Objects;

public final class LessThan<T extends Comparable<T>> implements Constraint<T> {

    private final Constraint<T> delegate;

    public LessThan(T maximum) {
        T bound = Objects.requireNonNull(maximum, "maximum");
        this.delegate = RefinedSupport.lessThan(bound, "less-than", "value must be less than " + bound);
    }

    @Override
    public Validation<Violation, T> validate(T value) {
        return delegate.validate(value);
    }
}
