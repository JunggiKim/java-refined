package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code float} value that is strictly positive ({@code > 0}). */
public final class PositiveFloat extends AbstractRefined<Float> implements Newtype<Float> {

    private static final Constraint<Float> CONSTRAINT = RefinedSupport.positiveFloat();

    private PositiveFloat(Float value) {
        super(value);
    }

    public static Validation<Violation, PositiveFloat> of(Float value) {
        return RefinedSupport.refine(value, CONSTRAINT, PositiveFloat::new);
    }

    public static PositiveFloat unsafeOf(Float value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, PositiveFloat::new);
    }
}
