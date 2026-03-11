package io.github.junggikim.refined.predicate.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class ClosedOpenInterval<T extends Comparable<T>> implements Constraint<T> {

    private final Constraint<T> delegate;

    public ClosedOpenInterval(T minimum, T maximum) {
        RefinedSupport.validateIntervalBounds(minimum, maximum, false, "closed-open interval");
        this.delegate = RefinedSupport.closedOpenInterval(minimum, maximum, "closed-open-interval", "value must be inside the closed-open interval");
    }

    @Override
    public Validation<Violation, T> validate(T value) {
        return delegate.validate(value);
    }
}
