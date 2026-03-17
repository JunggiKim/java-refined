package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.math.BigDecimal;

/** Wraps a {@code BigDecimal} value that is non-zero ({@code != 0}). */
public final class NonZeroBigDecimal extends AbstractRefined<BigDecimal> implements Newtype<BigDecimal> {

    private static final Constraint<BigDecimal> CONSTRAINT = RefinedSupport.nonZeroBigDecimal();

    private NonZeroBigDecimal(BigDecimal value) {
        super(value);
    }

    public static Validation<Violation, NonZeroBigDecimal> of(BigDecimal value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonZeroBigDecimal::new);
    }

    public static NonZeroBigDecimal unsafeOf(BigDecimal value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonZeroBigDecimal::new);
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
    public static NonZeroBigDecimal ofOrElse(@org.jetbrains.annotations.Nullable BigDecimal value, @org.jetbrains.annotations.NotNull BigDecimal defaultValue) {
        Validation<Violation, NonZeroBigDecimal> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
