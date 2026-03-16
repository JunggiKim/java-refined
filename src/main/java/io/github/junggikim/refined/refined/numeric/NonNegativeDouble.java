package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a finite {@code double} value that is non-negative ({@code >= 0}). */
public final class NonNegativeDouble extends AbstractRefined<Double> implements Newtype<Double> {

    private static final Constraint<Double> CONSTRAINT = RefinedSupport.nonNegativeDouble();

    private NonNegativeDouble(Double value) {
        super(value);
    }

    public static Validation<Violation, NonNegativeDouble> of(Double value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonNegativeDouble::new);
    }

    public static NonNegativeDouble unsafeOf(Double value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonNegativeDouble::new);
    }
}
