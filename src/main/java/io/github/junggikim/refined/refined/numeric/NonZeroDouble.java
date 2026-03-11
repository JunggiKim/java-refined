package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class NonZeroDouble extends AbstractRefined<Double> implements Newtype<Double> {

    private static final Constraint<Double> CONSTRAINT = RefinedSupport.nonZeroDouble();

    private NonZeroDouble(Double value) {
        super(value);
    }

    public static Validation<Violation, NonZeroDouble> of(Double value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonZeroDouble::new);
    }

    public static NonZeroDouble unsafeOf(Double value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonZeroDouble::new);
    }
}
