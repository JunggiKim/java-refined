package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.math.BigInteger;

public final class NaturalBigInteger extends AbstractRefined<BigInteger> implements Newtype<BigInteger> {

    private static final Constraint<BigInteger> CONSTRAINT = RefinedSupport.naturalBigInteger();

    private NaturalBigInteger(BigInteger value) {
        super(value);
    }

    public static Validation<Violation, NaturalBigInteger> of(BigInteger value) {
        return RefinedSupport.refine(value, CONSTRAINT, NaturalBigInteger::new);
    }

    public static NaturalBigInteger unsafeOf(BigInteger value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NaturalBigInteger::new);
    }
}
