package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.math.BigDecimal;

/** Wraps a {@code BigDecimal} value that is strictly negative ({@code < 0}). */
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

    /**
     * Returns a validated instance, or falls back to {@code defaultValue} if invalid.
     *
     * @param value input to validate
     * @param defaultValue fallback (must itself be valid)
     * @return refined instance
     * @throws io.github.junggikim.refined.core.RefinementException if defaultValue is also invalid
     */
    @org.jetbrains.annotations.NotNull
    public static NegativeBigDecimal ofOrElse(@org.jetbrains.annotations.Nullable BigDecimal value, @org.jetbrains.annotations.NotNull BigDecimal defaultValue) {
        Validation<Violation, NegativeBigDecimal> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
