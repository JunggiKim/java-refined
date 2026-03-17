package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code byte} value that is non-zero ({@code != 0}). */
public final class NonZeroByte extends AbstractRefined<Byte> implements Newtype<Byte> {

    private static final Constraint<Byte> CONSTRAINT = RefinedSupport.nonZeroByte();

    private NonZeroByte(Byte value) {
        super(value);
    }

    public static Validation<Violation, NonZeroByte> of(Byte value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonZeroByte::new);
    }

    public static NonZeroByte unsafeOf(Byte value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonZeroByte::new);
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
    public static NonZeroByte ofOrElse(@org.jetbrains.annotations.Nullable Byte value, @org.jetbrains.annotations.NotNull Byte defaultValue) {
        Validation<Violation, NonZeroByte> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
