package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class NegativeLong extends AbstractRefined<Long> implements Newtype<Long> {

    private static final Constraint<Long> CONSTRAINT = RefinedSupport.negativeLong();

    private NegativeLong(Long value) {
        super(value);
    }

    public static Validation<Violation, NegativeLong> of(Long value) {
        return RefinedSupport.refine(value, CONSTRAINT, NegativeLong::new);
    }

    public static NegativeLong unsafeOf(Long value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NegativeLong::new);
    }
}
