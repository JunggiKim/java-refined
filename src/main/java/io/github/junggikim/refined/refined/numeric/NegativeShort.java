package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code short} value that is strictly negative ({@code < 0}). */
public final class NegativeShort extends AbstractRefined<Short> implements Newtype<Short> {

    private static final Constraint<Short> CONSTRAINT = RefinedSupport.negativeShort();

    private NegativeShort(Short value) {
        super(value);
    }

    public static Validation<Violation, NegativeShort> of(Short value) {
        return RefinedSupport.refine(value, CONSTRAINT, NegativeShort::new);
    }

    public static NegativeShort unsafeOf(Short value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NegativeShort::new);
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
    public static NegativeShort ofOrElse(@org.jetbrains.annotations.Nullable Short value, @org.jetbrains.annotations.NotNull Short defaultValue) {
        Validation<Violation, NegativeShort> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
