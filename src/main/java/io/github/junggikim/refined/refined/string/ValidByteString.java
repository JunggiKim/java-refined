package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class ValidByteString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.validByteString();

    private ValidByteString(String value) {
        super(value);
    }

    public static Validation<Violation, ValidByteString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, ValidByteString::new);
    }

    public static ValidByteString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, ValidByteString::new);
    }
}
