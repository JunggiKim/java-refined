package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that is valid XML. */
public final class XmlString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.xmlString();

    private XmlString(String value) {
        super(value);
    }

    public static Validation<Violation, XmlString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, XmlString::new);
    }

    public static XmlString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, XmlString::new);
    }
}
