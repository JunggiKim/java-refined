package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a finite {@code float} value that is non-negative ({@code >= 0}). */
public final class NonNegativeFloat extends AbstractRefined<Float> implements Newtype<Float> {

    private static final Constraint<Float> CONSTRAINT = RefinedSupport.nonNegativeFloat();

    private NonNegativeFloat(Float value) {
        super(value);
    }

    public static Validation<Violation, NonNegativeFloat> of(Float value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonNegativeFloat::new);
    }

    public static NonNegativeFloat unsafeOf(Float value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonNegativeFloat::new);
    }
}
