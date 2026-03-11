package io.github.junggikim.refined.predicate.numeric;

import java.math.BigInteger;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class EvenBigInteger implements Constraint<BigInteger> {

    private final Constraint<BigInteger> delegate = RefinedSupport.evenBigInteger();

    @Override
    public Validation<Violation, BigInteger> validate(BigInteger value) {
        return delegate.validate(value);
    }
}
