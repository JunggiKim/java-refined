package io.github.junggikim.refined.predicate.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class OddLong implements Constraint<Long> {

    private final Constraint<Long> delegate = RefinedSupport.oddLong();

    @Override
    public Validation<Violation, Long> validate(Long value) {
        return delegate.validate(value);
    }
}
