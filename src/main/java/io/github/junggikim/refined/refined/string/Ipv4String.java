package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that is a valid IPv4 address. */
public final class Ipv4String extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.ipv4String();

    private Ipv4String(String value) {
        super(value);
    }

    public static Validation<Violation, Ipv4String> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, Ipv4String::new);
    }

    public static Ipv4String unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, Ipv4String::new);
    }
}
