package io.github.junggikim.refined.predicate.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class NonDivisibleByLong implements Constraint<Long> {

    private final Constraint<Long> delegate;

    public NonDivisibleByLong(long divisor) {
        if (divisor == 0) {
            throw new IllegalArgumentException("divisor must not be 0");
        }
        this.delegate = RefinedSupport.require("non-divisible-by-long", "value must not be divisible by " + divisor, value -> value % divisor != 0);
    }

    @Override
    public Validation<Violation, Long> validate(Long value) {
        return delegate.validate(value);
    }
}
