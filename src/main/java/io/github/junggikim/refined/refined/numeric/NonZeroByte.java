package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class NonZeroByte extends AbstractRefined<Byte> implements Newtype<Byte> {

    private static final Constraint<Byte> CONSTRAINT = RefinedSupport.nonZeroByte();

    private NonZeroByte(Byte value) {
        super(value);
    }

    public static Validation<Violation, NonZeroByte> of(Byte value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonZeroByte::new);
    }

    public static NonZeroByte unsafeOf(Byte value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonZeroByte::new);
    }
}
