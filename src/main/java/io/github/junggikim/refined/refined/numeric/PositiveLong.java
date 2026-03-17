package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code long} value that is strictly positive ({@code > 0}). */
public final class PositiveLong extends AbstractRefined<Long> implements Newtype<Long> {

    private static final Constraint<Long> CONSTRAINT = RefinedSupport.positiveLong();

    private PositiveLong(Long value) {
        super(value);
    }

    public static Validation<Violation, PositiveLong> of(Long value) {
        return RefinedSupport.refine(value, CONSTRAINT, PositiveLong::new);
    }

    public static PositiveLong unsafeOf(Long value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, PositiveLong::new);
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
    public static PositiveLong ofOrElse(@org.jetbrains.annotations.Nullable Long value, @org.jetbrains.annotations.NotNull Long defaultValue) {
        Validation<Violation, PositiveLong> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
