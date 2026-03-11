package io.github.junggikim.refined.predicate.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class OpenInterval<T extends Comparable<T>> implements Constraint<T> {

    private final Constraint<T> delegate;

    public OpenInterval(T minimum, T maximum) {
        RefinedSupport.validateIntervalBounds(minimum, maximum, false, "open interval");
        this.delegate = RefinedSupport.openInterval(minimum, maximum, "open-interval", "value must be inside the open interval");
    }

    @Override
    public Validation<Violation, T> validate(T value) {
        return delegate.validate(value);
    }
}
