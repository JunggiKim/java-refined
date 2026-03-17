package io.github.junggikim.refined.refined.character;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code char} that is a Unicode letter ({@code Character.isLetter}). */
public final class LetterChar extends AbstractRefined<Character> implements Newtype<Character> {

    private static final Constraint<Character> CONSTRAINT = RefinedSupport.letterChar();

    private LetterChar(Character value) {
        super(value);
    }

    public static Validation<Violation, LetterChar> of(Character value) {
        return RefinedSupport.refine(value, CONSTRAINT, LetterChar::new);
    }

    public static LetterChar unsafeOf(Character value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, LetterChar::new);
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
    public static LetterChar ofOrElse(@org.jetbrains.annotations.Nullable Character value, @org.jetbrains.annotations.NotNull Character defaultValue) {
        Validation<Violation, LetterChar> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
