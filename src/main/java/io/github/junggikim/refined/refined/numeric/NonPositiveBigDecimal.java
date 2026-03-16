package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.math.BigDecimal;

/** Wraps a {@code BigDecimal} value that is non-positive ({@code <= 0}). */
public final class NonPositiveBigDecimal extends AbstractRefined<BigDecimal> implements Newtype<BigDecimal> {

    private static final Constraint<BigDecimal> CONSTRAINT = RefinedSupport.nonPositiveBigDecimal();

    private NonPositiveBigDecimal(BigDecimal value) {
        super(value);
    }

    public static Validation<Violation, NonPositiveBigDecimal> of(BigDecimal value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonPositiveBigDecimal::new);
    }

    public static NonPositiveBigDecimal unsafeOf(BigDecimal value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonPositiveBigDecimal::new);
    }
}
