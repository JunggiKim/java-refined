package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that is parsable as a {@code BigDecimal}. */
public final class ValidBigDecimalString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.validBigDecimalString();

    private ValidBigDecimalString(String value) {
        super(value);
    }

    public static Validation<Violation, ValidBigDecimalString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, ValidBigDecimalString::new);
    }

    public static ValidBigDecimalString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, ValidBigDecimalString::new);
    }
}
