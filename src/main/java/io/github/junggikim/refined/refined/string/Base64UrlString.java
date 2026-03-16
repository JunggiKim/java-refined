package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that is valid Base64url-encoded. */
public final class Base64UrlString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.base64UrlString();

    private Base64UrlString(String value) {
        super(value);
    }

    public static Validation<Violation, Base64UrlString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, Base64UrlString::new);
    }

    public static Base64UrlString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, Base64UrlString::new);
    }
}
