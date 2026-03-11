package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.math.BigInteger;

public final class NonZeroBigInteger extends AbstractRefined<BigInteger> implements Newtype<BigInteger> {

    private static final Constraint<BigInteger> CONSTRAINT = RefinedSupport.nonZeroBigInteger();

    private NonZeroBigInteger(BigInteger value) {
        super(value);
    }

    public static Validation<Violation, NonZeroBigInteger> of(BigInteger value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonZeroBigInteger::new);
    }

    public static NonZeroBigInteger unsafeOf(BigInteger value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonZeroBigInteger::new);
    }
}
