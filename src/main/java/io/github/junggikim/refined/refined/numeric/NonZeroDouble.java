package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a finite {@code double} value that is non-zero ({@code != 0}). */
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

    /**
     * Returns a validated instance, or falls back to {@code defaultValue} if invalid.
     *
     * @param value input to validate
     * @param defaultValue fallback (must itself be valid)
     * @return refined instance
     * @throws io.github.junggikim.refined.core.RefinementException if defaultValue is also invalid
     */
    @org.jetbrains.annotations.NotNull
    public static NonZeroDouble ofOrElse(@org.jetbrains.annotations.Nullable Double value, @org.jetbrains.annotations.NotNull Double defaultValue) {
        Validation<Violation, NonZeroDouble> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
