package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code short} value that is non-zero ({@code != 0}). */
public final class NonZeroShort extends AbstractRefined<Short> implements Newtype<Short> {

    private static final Constraint<Short> CONSTRAINT = RefinedSupport.nonZeroShort();

    private NonZeroShort(Short value) {
        super(value);
    }

    public static Validation<Violation, NonZeroShort> of(Short value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonZeroShort::new);
    }

    public static NonZeroShort unsafeOf(Short value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonZeroShort::new);
    }
}
