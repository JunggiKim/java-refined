package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that contains only Unicode letters. */
public final class AlphabeticString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.alphabeticString();

    private AlphabeticString(String value) {
        super(value);
    }

    public static Validation<Violation, AlphabeticString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, AlphabeticString::new);
    }

    public static AlphabeticString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, AlphabeticString::new);
    }
}
