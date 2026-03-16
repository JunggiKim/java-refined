package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that is a valid UUID. */
public final class UuidString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.uuidString();

    private UuidString(String value) {
        super(value);
    }

    public static Validation<Violation, UuidString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, UuidString::new);
    }

    public static UuidString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, UuidString::new);
    }
}
