package io.github.junggikim.refined.refined.character;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code char} that is a Unicode digit ({@code Character.isDigit}). */
public final class DigitChar extends AbstractRefined<Character> implements Newtype<Character> {

    private static final Constraint<Character> CONSTRAINT = RefinedSupport.digitChar();

    private DigitChar(Character value) {
        super(value);
    }

    public static Validation<Violation, DigitChar> of(Character value) {
        return RefinedSupport.refine(value, CONSTRAINT, DigitChar::new);
    }

    public static DigitChar unsafeOf(Character value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, DigitChar::new);
    }
}
