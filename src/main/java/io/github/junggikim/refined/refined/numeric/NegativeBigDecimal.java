package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.math.BigDecimal;

public final class NegativeBigDecimal extends AbstractRefined<BigDecimal> implements Newtype<BigDecimal> {

    private static final Constraint<BigDecimal> CONSTRAINT = RefinedSupport.negativeBigDecimal();

    private NegativeBigDecimal(BigDecimal value) {
        super(value);
    }

    public static Validation<Violation, NegativeBigDecimal> of(BigDecimal value) {
        return RefinedSupport.refine(value, CONSTRAINT, NegativeBigDecimal::new);
    }

    public static NegativeBigDecimal unsafeOf(BigDecimal value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NegativeBigDecimal::new);
    }
}
