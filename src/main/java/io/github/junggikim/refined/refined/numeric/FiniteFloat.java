package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code float} value that is finite (not infinity or NaN). */
public final class FiniteFloat extends AbstractRefined<Float> implements Newtype<Float> {

    private static final Constraint<Float> CONSTRAINT = RefinedSupport.finiteFloat();

    private FiniteFloat(Float value) {
        super(value);
    }

    public static Validation<Violation, FiniteFloat> of(Float value) {
        return RefinedSupport.refine(value, CONSTRAINT, FiniteFloat::new);
    }

    public static FiniteFloat unsafeOf(Float value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, FiniteFloat::new);
    }
}
