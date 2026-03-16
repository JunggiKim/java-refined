package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that is a valid hostname (RFC 952/1123). */
public final class HostnameString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.hostnameString();

    private HostnameString(String value) {
        super(value);
    }

    public static Validation<Violation, HostnameString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, HostnameString::new);
    }

    public static HostnameString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, HostnameString::new);
    }
}
