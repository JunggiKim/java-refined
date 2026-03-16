package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code float} value that is not NaN. */
public final class NonNaNFloat extends AbstractRefined<Float> implements Newtype<Float> {

    private static final Constraint<Float> CONSTRAINT = RefinedSupport.nonNaNFloat();

    private NonNaNFloat(Float value) {
        super(value);
    }

    public static Validation<Violation, NonNaNFloat> of(Float value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonNaNFloat::new);
    }

    public static NonNaNFloat unsafeOf(Float value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonNaNFloat::new);
    }
}
