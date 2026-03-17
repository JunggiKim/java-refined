package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that contains only hexadecimal characters ({@code 0-9, a-f, A-F}). */
public final class HexString extends AbstractRefined<String> implements Newtype<String> {

    private static final Constraint<String> CONSTRAINT = RefinedSupport.hexString();

    private HexString(String value) {
        super(value);
    }

    public static Validation<Violation, HexString> of(String value) {
        return RefinedSupport.refine(value, CONSTRAINT, HexString::new);
    }

    public static HexString unsafeOf(String value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, HexString::new);
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
    public static HexString ofOrElse(@org.jetbrains.annotations.Nullable String value, @org.jetbrains.annotations.NotNull String defaultValue) {
        Validation<Violation, HexString> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
