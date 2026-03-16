package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that is a valid credit-card number (Luhn check). */
public final class CreditCardString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.creditCardString();

    private CreditCardString(String value) {
        super(value);
    }

    public static Validation<Violation, CreditCardString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, CreditCardString::new);
    }

    public static CreditCardString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, CreditCardString::new);
    }
}
