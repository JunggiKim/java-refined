package io.github.junggikim.refined.predicate.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class DivisibleByLong implements Constraint<Long> {

    private final Constraint<Long> delegate;

    public DivisibleByLong(long divisor) {
        if (divisor == 0L) {
            throw new IllegalArgumentException("divisor must not be 0");
        }
        this.delegate = RefinedSupport.require("divisible-by-long", "value must be divisible by " + divisor, value -> value % divisor == 0L);
    }

    @Override
    public Validation<Violation, Long> validate(Long value) {
        return delegate.validate(value);
    }
}
