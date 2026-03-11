package io.github.junggikim.refined.predicate.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class FiniteDoublePredicate implements Constraint<Double> {

    private final Constraint<Double> delegate = RefinedSupport.finiteDouble();

    @Override
    public Validation<Violation, Double> validate(Double value) {
        return delegate.validate(value);
    }
}
