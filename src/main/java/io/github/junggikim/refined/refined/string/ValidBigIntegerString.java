package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that is parsable as a {@code BigInteger}. */
public final class ValidBigIntegerString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.validBigIntegerString();

    private ValidBigIntegerString(String value) {
        super(value);
    }

    public static Validation<Violation, ValidBigIntegerString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, ValidBigIntegerString::new);
    }

    public static ValidBigIntegerString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, ValidBigIntegerString::new);
    }
}
