package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that is non-empty ({@code length > 0}). */
public final class NonEmptyString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.nonEmptyString();

    private NonEmptyString(String value) {
        super(value);
    }

    public static Validation<Violation, NonEmptyString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonEmptyString::new);
    }

    public static NonEmptyString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonEmptyString::new);
    }
}
