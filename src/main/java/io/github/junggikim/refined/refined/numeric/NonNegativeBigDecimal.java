package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.math.BigDecimal;

public final class NonNegativeBigDecimal extends AbstractRefined<BigDecimal> implements Newtype<BigDecimal> {

    private static final Constraint<BigDecimal> CONSTRAINT = RefinedSupport.nonNegativeBigDecimal();

    private NonNegativeBigDecimal(BigDecimal value) {
        super(value);
    }

    public static Validation<Violation, NonNegativeBigDecimal> of(BigDecimal value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonNegativeBigDecimal::new);
    }

    public static NonNegativeBigDecimal unsafeOf(BigDecimal value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonNegativeBigDecimal::new);
    }
}
