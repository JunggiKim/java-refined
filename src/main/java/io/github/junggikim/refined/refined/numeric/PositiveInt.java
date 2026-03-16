package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps an {@code int} value that is strictly positive ({@code > 0}). */
public final class PositiveInt extends AbstractRefined<Integer> implements Newtype<Integer> {

    private static final Constraint<Integer> CONSTRAINT = RefinedSupport.positiveInt();

    private PositiveInt(Integer value) {
        super(value);
    }

    public static Validation<Violation, PositiveInt> of(Integer value) {
        return RefinedSupport.refine(value, CONSTRAINT, PositiveInt::new);
    }

    public static PositiveInt unsafeOf(Integer value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, PositiveInt::new);
    }
}
