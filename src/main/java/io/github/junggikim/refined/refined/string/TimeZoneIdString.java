package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class TimeZoneIdString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.timeZoneIdString();

    private TimeZoneIdString(String value) {
        super(value);
    }

    public static Validation<Violation, TimeZoneIdString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, TimeZoneIdString::new);
    }

    public static TimeZoneIdString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, TimeZoneIdString::new);
    }
}
