package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code long} value that is a natural number ({@code >= 0}). */
public final class NaturalLong extends AbstractRefined<Long> implements Newtype<Long> {

    private static final Constraint<Long> CONSTRAINT = RefinedSupport.naturalLong();

    private NaturalLong(Long value) {
        super(value);
    }

    public static Validation<Violation, NaturalLong> of(Long value) {
        return RefinedSupport.refine(value, CONSTRAINT, NaturalLong::new);
    }

    public static NaturalLong unsafeOf(Long value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NaturalLong::new);
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
    public static NaturalLong ofOrElse(@org.jetbrains.annotations.Nullable Long value, @org.jetbrains.annotations.NotNull Long defaultValue) {
        Validation<Violation, NaturalLong> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
