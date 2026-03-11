package io.github.junggikim.refined.predicate.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class OpenClosedInterval<T extends Comparable<T>> implements Constraint<T> {

    private final Constraint<T> delegate;

    public OpenClosedInterval(T minimum, T maximum) {
        RefinedSupport.validateIntervalBounds(minimum, maximum, false, "open-closed interval");
        this.delegate = RefinedSupport.openClosedInterval(minimum, maximum, "open-closed-interval", "value must be inside the open-closed interval");
    }

    @Override
    public Validation<Violation, T> validate(T value) {
        return delegate.validate(value);
    }
}
