package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that is a valid ISO 8601 duration. */
public final class Iso8601DurationString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.iso8601DurationString();

    private Iso8601DurationString(String value) {
        super(value);
    }

    public static Validation<Violation, Iso8601DurationString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, Iso8601DurationString::new);
    }

    public static Iso8601DurationString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, Iso8601DurationString::new);
    }
}
