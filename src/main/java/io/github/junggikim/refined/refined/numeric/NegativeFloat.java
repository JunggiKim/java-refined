package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code float} value that is strictly negative ({@code < 0}). */
public final class NegativeFloat extends AbstractRefined<Float> implements Newtype<Float> {

    private static final Constraint<Float> CONSTRAINT = RefinedSupport.negativeFloat();

    private NegativeFloat(Float value) {
        super(value);
    }

    public static Validation<Violation, NegativeFloat> of(Float value) {
        return RefinedSupport.refine(value, CONSTRAINT, NegativeFloat::new);
    }

    public static NegativeFloat unsafeOf(Float value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NegativeFloat::new);
    }
}
