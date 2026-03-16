package io.github.junggikim.refined.refined.character;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code char} that is an uppercase letter ({@code Character.isUpperCase}). */
public final class UpperCaseChar extends AbstractRefined<Character> implements Newtype<Character> {

    private static final Constraint<Character> CONSTRAINT = RefinedSupport.upperCaseChar();

    private UpperCaseChar(Character value) {
        super(value);
    }

    public static Validation<Violation, UpperCaseChar> of(Character value) {
        return RefinedSupport.refine(value, CONSTRAINT, UpperCaseChar::new);
    }

    public static UpperCaseChar unsafeOf(Character value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, UpperCaseChar::new);
    }
}
