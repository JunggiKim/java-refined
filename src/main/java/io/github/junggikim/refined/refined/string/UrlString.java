package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class UrlString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.urlString();

    private UrlString(String value) {
        super(value);
    }

    public static Validation<Violation, UrlString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, UrlString::new);
    }

    public static UrlString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, UrlString::new);
    }
}
