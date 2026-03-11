package io.github.junggikim.refined.predicate.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class FiniteFloatPredicate implements Constraint<Float> {

    private final Constraint<Float> delegate = RefinedSupport.finiteFloat();

    @Override
    public Validation<Violation, Float> validate(Float value) {
        return delegate.validate(value);
    }
}
