package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} where all cased characters are uppercase. */
public final class UpperCaseString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.upperCaseString();

    private UpperCaseString(String value) {
        super(value);
    }

    public static Validation<Violation, UpperCaseString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, UpperCaseString::new);
    }

    public static UpperCaseString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, UpperCaseString::new);
    }
}
