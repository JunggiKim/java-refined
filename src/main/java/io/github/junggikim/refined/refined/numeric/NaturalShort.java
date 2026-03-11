package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class NaturalShort extends AbstractRefined<Short> implements Newtype<Short> {

    private static final Constraint<Short> CONSTRAINT = RefinedSupport.naturalShort();

    private NaturalShort(Short value) {
        super(value);
    }

    public static Validation<Violation, NaturalShort> of(Short value) {
        return RefinedSupport.refine(value, CONSTRAINT, NaturalShort::new);
    }

    public static NaturalShort unsafeOf(Short value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NaturalShort::new);
    }
}
