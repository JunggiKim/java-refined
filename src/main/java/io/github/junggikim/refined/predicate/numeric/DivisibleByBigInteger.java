package io.github.junggikim.refined.predicate.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.math.BigInteger;

public final class DivisibleByBigInteger implements Constraint<BigInteger> {

    private final Constraint<BigInteger> delegate;

    public DivisibleByBigInteger(BigInteger divisor) {
        if (divisor == null || BigInteger.ZERO.equals(divisor)) {
            throw new IllegalArgumentException("divisor must not be 0");
        }
        this.delegate = RefinedSupport.require(
            "divisible-by-big-integer",
            "value must be divisible by " + divisor,
            value -> value.mod(divisor).equals(BigInteger.ZERO)
        );
    }

    @Override
    public Validation<Violation, BigInteger> validate(BigInteger value) {
        return delegate.validate(value);
    }
}
