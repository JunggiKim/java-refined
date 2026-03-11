package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class NonBlankString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.nonBlankString();

    private NonBlankString(String value) {
        super(value);
    }

    public static Validation<Violation, NonBlankString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonBlankString::new);
    }

    public static NonBlankString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonBlankString::new);
    }
}
