package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a finite {@code float} value that is non-positive ({@code <= 0}). */
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

    /**
     * Returns a validated instance, or falls back to {@code defaultValue} if invalid.
     *
     * @param value input to validate
     * @param defaultValue fallback (must itself be valid)
     * @return refined instance
     * @throws io.github.junggikim.refined.core.RefinementException if defaultValue is also invalid
     */
    @org.jetbrains.annotations.NotNull
    public static NonPositiveFloat ofOrElse(@org.jetbrains.annotations.Nullable Float value, @org.jetbrains.annotations.NotNull Float defaultValue) {
        Validation<Violation, NonPositiveFloat> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
