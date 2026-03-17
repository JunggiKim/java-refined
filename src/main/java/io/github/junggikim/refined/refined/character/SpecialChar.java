package io.github.junggikim.refined.refined.character;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code char} that is a special character (not a letter, digit, or whitespace). */
public final class SpecialChar extends AbstractRefined<Character> implements Newtype<Character> {

    private static final Constraint<Character> CONSTRAINT = RefinedSupport.specialChar();

    private SpecialChar(Character value) {
        super(value);
    }

    public static Validation<Violation, SpecialChar> of(Character value) {
        return RefinedSupport.refine(value, CONSTRAINT, SpecialChar::new);
    }

    public static SpecialChar unsafeOf(Character value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, SpecialChar::new);
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
    public static SpecialChar ofOrElse(@org.jetbrains.annotations.Nullable Character value, @org.jetbrains.annotations.NotNull Character defaultValue) {
        Validation<Violation, SpecialChar> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
