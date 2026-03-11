package io.github.junggikim.refined.predicate.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class ModuloInt implements Constraint<Integer> {

    private final Constraint<Integer> delegate;

    public ModuloInt(int divisor, int remainder) {
        if (divisor == 0) {
            throw new IllegalArgumentException("divisor must not be 0");
        }
        this.delegate = RefinedSupport.require("modulo-int", "value % " + divisor + " must equal " + remainder, value -> value % divisor == remainder);
    }

    @Override
    public Validation<Violation, Integer> validate(Integer value) {
        return delegate.validate(value);
    }
}
