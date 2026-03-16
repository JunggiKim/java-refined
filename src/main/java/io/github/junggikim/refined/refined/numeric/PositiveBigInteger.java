package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.math.BigInteger;

/** Wraps a {@code BigInteger} value that is strictly positive ({@code > 0}). */
public final class PositiveBigInteger extends AbstractRefined<BigInteger> implements Newtype<BigInteger> {

    private static final Constraint<BigInteger> CONSTRAINT = RefinedSupport.positiveBigInteger();

    private PositiveBigInteger(BigInteger value) {
        super(value);
    }

    public static Validation<Violation, PositiveBigInteger> of(BigInteger value) {
        return RefinedSupport.refine(value, CONSTRAINT, PositiveBigInteger::new);
    }

    public static PositiveBigInteger unsafeOf(BigInteger value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, PositiveBigInteger::new);
    }
}
