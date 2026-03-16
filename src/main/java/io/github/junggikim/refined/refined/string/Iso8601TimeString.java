package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that is a valid ISO 8601 time. */
public final class Iso8601TimeString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.iso8601TimeString();

    private Iso8601TimeString(String value) {
        super(value);
    }

    public static Validation<Violation, Iso8601TimeString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, Iso8601TimeString::new);
    }

    public static Iso8601TimeString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, Iso8601TimeString::new);
    }
}
