package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that is a valid IPv6 address. */
public final class Ipv6String extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.ipv6String();

    private Ipv6String(String value) {
        super(value);
    }

    public static Validation<Violation, Ipv6String> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, Ipv6String::new);
    }

    public static Ipv6String unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, Ipv6String::new);
    }
}
