package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class NonNegativeByte extends AbstractRefined<Byte> implements Newtype<Byte> {

    private static final Constraint<Byte> CONSTRAINT = RefinedSupport.nonNegativeByte();

    private NonNegativeByte(Byte value) {
        super(value);
    }

    public static Validation<Violation, NonNegativeByte> of(Byte value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonNegativeByte::new);
    }

    public static NonNegativeByte unsafeOf(Byte value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonNegativeByte::new);
    }
}
