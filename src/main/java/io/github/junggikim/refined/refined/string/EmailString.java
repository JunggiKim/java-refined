package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that is a valid email address. */
public final class EmailString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.emailString();

    private EmailString(String value) {
        super(value);
    }

    public static Validation<Violation, EmailString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, EmailString::new);
    }

    public static EmailString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, EmailString::new);
    }
}
