package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that is parsable as a {@code float}. */
public final class ValidFloatString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.validFloatString();

    private ValidFloatString(String value) {
        super(value);
    }

    public static Validation<Violation, ValidFloatString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, ValidFloatString::new);
    }

    public static ValidFloatString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, ValidFloatString::new);
    }
}
