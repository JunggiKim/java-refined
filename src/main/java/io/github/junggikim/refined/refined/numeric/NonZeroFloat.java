package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code float} value that is non-zero ({@code != 0}). */
public final class NonZeroFloat extends AbstractRefined<Float> implements Newtype<Float> {

    private static final Constraint<Float> CONSTRAINT = RefinedSupport.nonZeroFloat();

    private NonZeroFloat(Float value) {
        super(value);
    }

    public static Validation<Violation, NonZeroFloat> of(Float value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonZeroFloat::new);
    }

    public static NonZeroFloat unsafeOf(Float value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonZeroFloat::new);
    }
}
