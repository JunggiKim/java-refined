package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps an {@code int} value that is non-zero ({@code != 0}). */
public final class NonZeroInt extends AbstractRefined<Integer> implements Newtype<Integer> {

    private static final Constraint<Integer> CONSTRAINT = RefinedSupport.nonZeroInt();

    private NonZeroInt(Integer value) {
        super(value);
    }

    public static Validation<Violation, NonZeroInt> of(Integer value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonZeroInt::new);
    }

    public static NonZeroInt unsafeOf(Integer value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonZeroInt::new);
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
    public static NonZeroInt ofOrElse(@org.jetbrains.annotations.Nullable Integer value, @org.jetbrains.annotations.NotNull Integer defaultValue) {
        Validation<Violation, NonZeroInt> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
