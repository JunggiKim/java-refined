package io.github.junggikim.refined.predicate.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class ClosedInterval<T extends Comparable<T>> implements Constraint<T> {

    private final Constraint<T> delegate;

    public ClosedInterval(T minimum, T maximum) {
        RefinedSupport.validateIntervalBounds(minimum, maximum, true, "closed interval");
        this.delegate = RefinedSupport.closedInterval(minimum, maximum, "closed-interval", "value must be inside the closed interval");
    }

    @Override
    public Validation<Violation, T> validate(T value) {
        return delegate.validate(value);
    }
}
