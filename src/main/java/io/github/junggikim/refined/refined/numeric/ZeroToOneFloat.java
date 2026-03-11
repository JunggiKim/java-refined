package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class ZeroToOneFloat extends AbstractRefined<Float> implements Newtype<Float> {

    private static final Constraint<Float> CONSTRAINT = RefinedSupport.zeroToOneFloat();

    private ZeroToOneFloat(Float value) {
        super(value);
    }

    public static Validation<Violation, ZeroToOneFloat> of(Float value) {
        return RefinedSupport.refine(value, CONSTRAINT, ZeroToOneFloat::new);
    }

    public static ZeroToOneFloat unsafeOf(Float value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, ZeroToOneFloat::new);
    }
}
