package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code short} value that is non-positive ({@code <= 0}). */
public final class NonPositiveShort extends AbstractRefined<Short> implements Newtype<Short> {

    private static final Constraint<Short> CONSTRAINT = RefinedSupport.nonPositiveShort();

    private NonPositiveShort(Short value) {
        super(value);
    }

    public static Validation<Violation, NonPositiveShort> of(Short value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonPositiveShort::new);
    }

    public static NonPositiveShort unsafeOf(Short value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonPositiveShort::new);
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
    public static NonPositiveShort ofOrElse(@org.jetbrains.annotations.Nullable Short value, @org.jetbrains.annotations.NotNull Short defaultValue) {
        Validation<Violation, NonPositiveShort> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
