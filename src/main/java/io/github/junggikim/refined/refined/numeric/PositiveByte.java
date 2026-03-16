package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code byte} value that is strictly positive ({@code > 0}). */
public final class PositiveByte extends AbstractRefined<Byte> implements Newtype<Byte> {

    private static final Constraint<Byte> CONSTRAINT = RefinedSupport.positiveByte();

    private PositiveByte(Byte value) {
        super(value);
    }

    public static Validation<Violation, PositiveByte> of(Byte value) {
        return RefinedSupport.refine(value, CONSTRAINT, PositiveByte::new);
    }

    public static PositiveByte unsafeOf(Byte value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, PositiveByte::new);
    }
}
