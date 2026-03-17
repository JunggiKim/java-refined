package io.github.junggikim.refined.refined.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** Wraps a {@code String} that is a valid IANA time-zone ID. */
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

    /**
     * Returns a validated instance, or falls back to {@code defaultValue} if invalid.
     *
     * @param value input to validate
     * @param defaultValue fallback (must itself be valid)
     * @return refined instance
     * @throws io.github.junggikim.refined.core.RefinementException if defaultValue is also invalid
     */
    @org.jetbrains.annotations.NotNull
    public static TimeZoneIdString ofOrElse(@org.jetbrains.annotations.Nullable String value, @org.jetbrains.annotations.NotNull String defaultValue) {
        Validation<Violation, TimeZoneIdString> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
