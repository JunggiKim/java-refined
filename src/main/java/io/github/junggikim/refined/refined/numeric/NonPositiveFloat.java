package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class NonPositiveFloat extends AbstractRefined<Float> implements Newtype<Float> {

    private static final Constraint<Float> CONSTRAINT = RefinedSupport.nonPositiveFloat();

    private NonPositiveFloat(Float value) {
        super(value);
    }

    public static Validation<Violation, NonPositiveFloat> of(Float value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonPositiveFloat::new);
    }

    public static NonPositiveFloat unsafeOf(Float value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonPositiveFloat::new);
    }
}
