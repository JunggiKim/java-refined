package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code short} value that is non-negative ({@code >= 0}). */
public final class NonNegativeShort extends AbstractRefined<Short> implements Newtype<Short> {

    private static final Constraint<Short> CONSTRAINT = RefinedSupport.nonNegativeShort();

    private NonNegativeShort(Short value) {
        super(value);
    }

    public static Validation<Violation, NonNegativeShort> of(Short value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonNegativeShort::new);
    }

    public static NonNegativeShort unsafeOf(Short value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonNegativeShort::new);
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
    public static NonNegativeShort ofOrElse(@org.jetbrains.annotations.Nullable Short value, @org.jetbrains.annotations.NotNull Short defaultValue) {
        Validation<Violation, NonNegativeShort> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
