package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that is a valid XPath expression. */
public final class XPathString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.xpathString();

    private XPathString(String value) {
        super(value);
    }

    public static Validation<Violation, XPathString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, XPathString::new);
    }

    public static XPathString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, XPathString::new);
    }

    /**
     * Returns a validated instance, or falls back to {@code defaultValue} if invalid.
     *
     * @param value input to validate
     * @param defaultValue fallback (must itself be valid)
     * @return refined instance
     * @throws io.github.junggikim.refined.core.RefinementException if defaultValue is also invalid
     */
    @org.jetbrains.annotations.NotNull
    public static XPathString ofOrElse(@org.jetbrains.annotations.Nullable String value, @org.jetbrains.annotations.NotNull String defaultValue) {
        Validation<Violation, XPathString> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
