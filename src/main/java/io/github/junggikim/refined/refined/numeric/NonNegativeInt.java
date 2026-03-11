package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class NonNegativeInt extends AbstractRefined<Integer> implements Newtype<Integer> {

    private static final Constraint<Integer> CONSTRAINT = RefinedSupport.nonNegativeInt();

    private NonNegativeInt(Integer value) {
        super(value);
    }

    public static Validation<Violation, NonNegativeInt> of(Integer value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonNegativeInt::new);
    }

    public static NonNegativeInt unsafeOf(Integer value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonNegativeInt::new);
    }
}
