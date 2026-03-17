package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code long} value that is strictly negative ({@code < 0}). */
public final class NegativeLong extends AbstractRefined<Long> implements Newtype<Long> {

    private static final Constraint<Long> CONSTRAINT = RefinedSupport.negativeLong();

    private NegativeLong(Long value) {
        super(value);
    }

    public static Validation<Violation, NegativeLong> of(Long value) {
        return RefinedSupport.refine(value, CONSTRAINT, NegativeLong::new);
    }

    public static NegativeLong unsafeOf(Long value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NegativeLong::new);
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
    public static NegativeLong ofOrElse(@org.jetbrains.annotations.Nullable Long value, @org.jetbrains.annotations.NotNull Long defaultValue) {
        Validation<Violation, NegativeLong> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
