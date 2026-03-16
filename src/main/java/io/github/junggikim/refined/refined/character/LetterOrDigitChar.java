package io.github.junggikim.refined.refined.character;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code char} that is a Unicode letter or digit ({@code Character.isLetterOrDigit}). */
public final class LetterOrDigitChar extends AbstractRefined<Character> implements Newtype<Character> {

    private static final Constraint<Character> CONSTRAINT = RefinedSupport.letterOrDigitChar();

    private LetterOrDigitChar(Character value) {
        super(value);
    }

    public static Validation<Violation, LetterOrDigitChar> of(Character value) {
        return RefinedSupport.refine(value, CONSTRAINT, LetterOrDigitChar::new);
    }

    public static LetterOrDigitChar unsafeOf(Character value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, LetterOrDigitChar::new);
    }
}
