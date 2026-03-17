package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a finite {@code double} value in the range {@code [0, 1]}. */
public final class ZeroToOneDouble extends AbstractRefined<Double> implements Newtype<Double> {

    private static final Constraint<Double> CONSTRAINT = RefinedSupport.zeroToOneDouble();

    private ZeroToOneDouble(Double value) {
        super(value);
    }

    public static Validation<Violation, ZeroToOneDouble> of(Double value) {
        return RefinedSupport.refine(value, CONSTRAINT, ZeroToOneDouble::new);
    }

    public static ZeroToOneDouble unsafeOf(Double value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, ZeroToOneDouble::new);
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
    public static ZeroToOneDouble ofOrElse(@org.jetbrains.annotations.Nullable Double value, @org.jetbrains.annotations.NotNull Double defaultValue) {
        Validation<Violation, ZeroToOneDouble> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
