package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class NaturalLong extends AbstractRefined<Long> implements Newtype<Long> {

    private static final Constraint<Long> CONSTRAINT = RefinedSupport.naturalLong();

    private NaturalLong(Long value) {
        super(value);
    }

    public static Validation<Violation, NaturalLong> of(Long value) {
        return RefinedSupport.refine(value, CONSTRAINT, NaturalLong::new);
    }

    public static NaturalLong unsafeOf(Long value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NaturalLong::new);
    }
}
