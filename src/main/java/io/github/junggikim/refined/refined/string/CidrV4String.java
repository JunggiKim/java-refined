package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that is a valid CIDR v4 notation. */
public final class CidrV4String extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.cidrV4String();

    private CidrV4String(String value) {
        super(value);
    }

    public static Validation<Violation, CidrV4String> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, CidrV4String::new);
    }

    public static CidrV4String unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, CidrV4String::new);
    }
}
