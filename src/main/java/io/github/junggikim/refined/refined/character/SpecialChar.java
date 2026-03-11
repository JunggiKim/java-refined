package io.github.junggikim.refined.refined.character;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

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
}
