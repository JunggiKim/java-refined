package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code double} value that is strictly negative ({@code < 0}). */
public final class NegativeDouble extends AbstractRefined<Double> implements Newtype<Double> {

    private static final Constraint<Double> CONSTRAINT = RefinedSupport.negativeDouble();

    private NegativeDouble(Double value) {
        super(value);
    }

    public static Validation<Violation, NegativeDouble> of(Double value) {
        return RefinedSupport.refine(value, CONSTRAINT, NegativeDouble::new);
    }

    public static NegativeDouble unsafeOf(Double value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NegativeDouble::new);
    }
}
