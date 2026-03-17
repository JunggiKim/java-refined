package io.github.junggikim.refined.refined.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.AbstractRefined;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.newtype.Newtype;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.math.BigInteger;

/** Wraps a {@code BigInteger} value that is strictly negative ({@code < 0}). */
public final class NegativeBigInteger extends AbstractRefined<BigInteger> implements Newtype<BigInteger> {

    private static final Constraint<BigInteger> CONSTRAINT = RefinedSupport.negativeBigInteger();

    private NegativeBigInteger(BigInteger value) {
        super(value);
    }

    public static Validation<Violation, NegativeBigInteger> of(BigInteger value) {
        return RefinedSupport.refine(value, CONSTRAINT, NegativeBigInteger::new);
    }

    public static NegativeBigInteger unsafeOf(BigInteger value) {
        return RefinedSupport.unsafeRefine(value, CONSTRAINT, NegativeBigInteger::new);
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
    public static NegativeBigInteger ofOrElse(@org.jetbrains.annotations.Nullable BigInteger value, @org.jetbrains.annotations.NotNull BigInteger defaultValue) {
        Validation<Violation, NegativeBigInteger> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
