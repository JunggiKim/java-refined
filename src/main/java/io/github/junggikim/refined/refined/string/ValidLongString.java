package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that is parsable as a {@code long}. */
public final class ValidLongString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.validLongString();

    private ValidLongString(String value) {
        super(value);
    }

    public static Validation<Violation, ValidLongString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, ValidLongString::new);
    }

    public static ValidLongString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, ValidLongString::new);
    }
}
