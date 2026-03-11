package io.github.junggikim.refined.predicate.booleanvalue;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class TrueValue implements Constraint<Boolean> {

    private final Constraint<Boolean> delegate = RefinedSupport.trueValue();

    @Override
    public Validation<Violation, Boolean> validate(Boolean value) {
        return delegate.validate(value);
    }
}
