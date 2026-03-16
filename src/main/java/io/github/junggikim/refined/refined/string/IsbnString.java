package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that is a valid ISBN (10 or 13). */
public final class IsbnString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.isbnString();

    private IsbnString(String value) {
        super(value);
    }

    public static Validation<Violation, IsbnString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, IsbnString::new);
    }

    public static IsbnString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, IsbnString::new);
    }
}
