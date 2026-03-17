package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps an {@code int} value that is strictly negative ({@code < 0}). */
public final class NegativeInt extends AbstractRefined<Integer> implements Newtype<Integer> {

    private static final Constraint<Integer> CONSTRAINT = RefinedSupport.negativeInt();

    private NegativeInt(Integer value) {
        super(value);
    }

    public static Validation<Violation, NegativeInt> of(Integer value) {
        return RefinedSupport.refine(value, CONSTRAINT, NegativeInt::new);
    }

    public static NegativeInt unsafeOf(Integer value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NegativeInt::new);
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
    public static NegativeInt ofOrElse(@org.jetbrains.annotations.Nullable Integer value, @org.jetbrains.annotations.NotNull Integer defaultValue) {
        Validation<Violation, NegativeInt> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
