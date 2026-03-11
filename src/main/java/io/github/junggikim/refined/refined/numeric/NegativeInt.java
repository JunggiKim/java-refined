package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class NegativeInt extends AbstractRefined<Integer> implements Newtype<Integer> {

    private static final Constraint<Integer> CONSTRAINT = RefinedSupport.negativeInt();

    private NegativeInt(Integer value) {
        super(value);
    }

    public static Validation<Violation, NegativeInt> of(Integer value) {
        return RefinedSupport.refine(value, CONSTRAINT, NegativeInt::new);
    }

    public static NegativeInt unsafeOf(Integer value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NegativeInt::new);
    }
}
