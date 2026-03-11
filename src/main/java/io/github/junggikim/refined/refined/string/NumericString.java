package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class NumericString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.numericString();

    private NumericString(String value) {
        super(value);
    }

    public static Validation<Violation, NumericString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, NumericString::new);
    }

    public static NumericString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NumericString::new);
    }
}
