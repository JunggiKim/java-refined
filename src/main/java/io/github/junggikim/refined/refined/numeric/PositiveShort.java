package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code short} value that is strictly positive ({@code > 0}). */
public final class PositiveShort extends AbstractRefined<Short> implements Newtype<Short> {

    private static final Constraint<Short> CONSTRAINT = RefinedSupport.positiveShort();

    private PositiveShort(Short value) {
        super(value);
    }

    public static Validation<Violation, PositiveShort> of(Short value) {
        return RefinedSupport.refine(value, CONSTRAINT, PositiveShort::new);
    }

    public static PositiveShort unsafeOf(Short value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, PositiveShort::new);
    }
}
