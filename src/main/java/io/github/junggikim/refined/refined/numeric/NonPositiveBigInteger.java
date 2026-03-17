package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.math.BigInteger;

/** Wraps a {@code BigInteger} value that is non-positive ({@code <= 0}). */
public final class NonPositiveBigInteger extends AbstractRefined<BigInteger> implements Newtype<BigInteger> {

    private static final Constraint<BigInteger> CONSTRAINT = RefinedSupport.nonPositiveBigInteger();

    private NonPositiveBigInteger(BigInteger value) {
        super(value);
    }

    public static Validation<Violation, NonPositiveBigInteger> of(BigInteger value) {
        return RefinedSupport.refine(value, CONSTRAINT, NonPositiveBigInteger::new);
    }

    public static NonPositiveBigInteger unsafeOf(BigInteger value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NonPositiveBigInteger::new);
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
    public static NonPositiveBigInteger ofOrElse(@org.jetbrains.annotations.Nullable BigInteger value, @org.jetbrains.annotations.NotNull BigInteger defaultValue) {
        Validation<Violation, NonPositiveBigInteger> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
