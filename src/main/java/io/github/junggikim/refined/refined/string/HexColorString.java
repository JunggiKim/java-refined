package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that is a valid hex color code (e.g. {@code #FF00AA}). */
public final class HexColorString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.hexColorString();

    private HexColorString(String value) {
        super(value);
    }

    public static Validation<Violation, HexColorString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, HexColorString::new);
    }

    public static HexColorString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, HexColorString::new);
    }
}
