package io.github.junggikim.refined.predicate.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class ModuloLong implements Constraint<Long> {

    private final Constraint<Long> delegate;

    public ModuloLong(long divisor, long remainder) {
        if (divisor == 0) {
            throw new IllegalArgumentException("divisor must not be 0");
        }
        this.delegate = RefinedSupport.require("modulo-long", "value % " + divisor + " must equal " + remainder, value -> value % divisor == remainder);
    }

    @Override
    public Validation<Violation, Long> validate(Long value) {
        return delegate.validate(value);
    }
}
