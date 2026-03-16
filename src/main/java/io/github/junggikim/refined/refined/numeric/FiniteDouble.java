package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code double} value that is finite (not infinity or NaN). */
public final class FiniteDouble extends AbstractRefined<Double> implements Newtype<Double> {

    private static final Constraint<Double> CONSTRAINT = RefinedSupport.finiteDouble();

    private FiniteDouble(Double value) {
        super(value);
    }

    public static Validation<Violation, FiniteDouble> of(Double value) {
        return RefinedSupport.refine(value, CONSTRAINT, FiniteDouble::new);
    }

    public static FiniteDouble unsafeOf(Double value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, FiniteDouble::new);
    }
}
