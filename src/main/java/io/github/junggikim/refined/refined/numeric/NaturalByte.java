package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code byte} value that is a natural number ({@code >= 0}). */
public final class NaturalByte extends AbstractRefined<Byte> implements Newtype<Byte> {

    private static final Constraint<Byte> CONSTRAINT = RefinedSupport.naturalByte();

    private NaturalByte(Byte value) {
        super(value);
    }

    public static Validation<Violation, NaturalByte> of(Byte value) {
        return RefinedSupport.refine(value, CONSTRAINT, NaturalByte::new);
    }

    public static NaturalByte unsafeOf(Byte value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NaturalByte::new);
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
    public static NaturalByte ofOrElse(@org.jetbrains.annotations.Nullable Byte value, @org.jetbrains.annotations.NotNull Byte defaultValue) {
        Validation<Violation, NaturalByte> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
