package io.github.junggikim.refined.predicate.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class LengthBetween implements Constraint<String> {

    private final Constraint<String> delegate;

    public LengthBetween(int minimum, int maximum) {
        if (minimum > maximum) {
            throw new IllegalArgumentException("length-between minimum must be less than or equal to maximum");
        }
        this.delegate = RefinedSupport.require(
            "length-between",
            "value length must be between " + minimum + " and " + maximum,
            value -> value.length() >= minimum && value.length() <= maximum
        );
    }

    @Override
    public Validation<Violation, String> validate(String value) {
        return delegate.validate(value);
    }
}
