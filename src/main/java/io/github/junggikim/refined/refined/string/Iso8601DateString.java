package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class Iso8601DateString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.iso8601DateString();

    private Iso8601DateString(String value) {
        super(value);
    }

    public static Validation<Violation, Iso8601DateString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, Iso8601DateString::new);
    }

    public static Iso8601DateString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, Iso8601DateString::new);
    }
}
