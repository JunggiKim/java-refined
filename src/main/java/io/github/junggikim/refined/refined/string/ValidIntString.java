package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that is parsable as an {@code int}. */
public final class ValidIntString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.validIntString();

    private ValidIntString(String value) {
        super(value);
    }

    public static Validation<Violation, ValidIntString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, ValidIntString::new);
    }

    public static ValidIntString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, ValidIntString::new);
    }
}
