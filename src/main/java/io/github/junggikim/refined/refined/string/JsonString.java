package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that is valid JSON. */
public final class JsonString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.jsonString();

    private JsonString(String value) {
        super(value);
    }

    public static Validation<Violation, JsonString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, JsonString::new);
    }

    public static JsonString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, JsonString::new);
    }
}
