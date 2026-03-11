package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class LowerCaseString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.lowerCaseString();

    private LowerCaseString(String value) {
        super(value);
    }

    public static Validation<Violation, LowerCaseString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, LowerCaseString::new);
    }

    public static LowerCaseString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, LowerCaseString::new);
    }
}
