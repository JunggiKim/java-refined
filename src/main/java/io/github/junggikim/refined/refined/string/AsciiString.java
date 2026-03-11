package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class AsciiString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.asciiString();

    private AsciiString(String value) {
        super(value);
    }

    public static Validation<Violation, AsciiString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, AsciiString::new);
    }

    public static AsciiString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, AsciiString::new);
    }
}
