package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that is valid Base64-encoded. */
public final class Base64String extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.base64String();

    private Base64String(String value) {
        super(value);
    }

    public static Validation<Violation, Base64String> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, Base64String::new);
    }

    public static Base64String unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, Base64String::new);
    }
}
