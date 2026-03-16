package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.math.BigInteger;

/** Wraps a {@code BigInteger} value that is non-negative ({@code >= 0}). */
public final class NonNegativeBigInteger extends AbstractRefined<BigInteger> implements Newtype<BigInteger> {

    private static final Constraint<BigInteger> CONSTRAINT = RefinedSupport.nonNegativeBigInteger();

    private NonNegativeBigInteger(BigInteger value) {
        super(value);
    }

    public static Validation<Violation, NonNegativeBigInteger> of(BigInteger value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonNegativeBigInteger::new);
    }

    public static NonNegativeBigInteger unsafeOf(BigInteger value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonNegativeBigInteger::new);
    }
}
