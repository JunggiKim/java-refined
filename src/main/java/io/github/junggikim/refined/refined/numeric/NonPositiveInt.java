package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps an {@code int} value that is non-positive ({@code <= 0}). */
public final class NonPositiveInt extends AbstractRefined<Integer> implements Newtype<Integer> {

    private static final Constraint<Integer> CONSTRAINT = RefinedSupport.nonPositiveInt();

    private NonPositiveInt(Integer value) {
        super(value);
    }

    public static Validation<Violation, NonPositiveInt> of(Integer value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonPositiveInt::new);
    }

    public static NonPositiveInt unsafeOf(Integer value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonPositiveInt::new);
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
    public static NonPositiveInt ofOrElse(@org.jetbrains.annotations.Nullable Integer value, @org.jetbrains.annotations.NotNull Integer defaultValue) {
        Validation<Violation, NonPositiveInt> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
