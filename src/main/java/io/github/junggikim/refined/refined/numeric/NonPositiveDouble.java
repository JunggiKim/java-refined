package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a finite {@code double} value that is non-positive ({@code <= 0}). */
public final class NonPositiveDouble extends AbstractRefined<Double> implements Newtype<Double> {

    private static final Constraint<Double> CONSTRAINT = RefinedSupport.nonPositiveDouble();

    private NonPositiveDouble(Double value) {
        super(value);
    }

    public static Validation<Violation, NonPositiveDouble> of(Double value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonPositiveDouble::new);
    }

    public static NonPositiveDouble unsafeOf(Double value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonPositiveDouble::new);
    }
}
