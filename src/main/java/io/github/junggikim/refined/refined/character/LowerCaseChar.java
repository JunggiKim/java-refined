package io.github.junggikim.refined.refined.character;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code char} that is a lowercase letter ({@code Character.isLowerCase}). */
public final class LowerCaseChar extends AbstractRefined<Character> implements Newtype<Character> {

    private static final Constraint<Character> CONSTRAINT = RefinedSupport.lowerCaseChar();

    private LowerCaseChar(Character value) {
        super(value);
    }

    public static Validation<Violation, LowerCaseChar> of(Character value) {
        return RefinedSupport.refine(value, CONSTRAINT, LowerCaseChar::new);
    }

    public static LowerCaseChar unsafeOf(Character value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, LowerCaseChar::new);
    }
}
