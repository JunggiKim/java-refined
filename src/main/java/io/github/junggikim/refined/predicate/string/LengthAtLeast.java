package io.github.junggikim.refined.predicate.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class LengthAtLeast implements Constraint<String> {

    private final Constraint<String> delegate;

    public LengthAtLeast(int minimum) {
        this.delegate = RefinedSupport.require("length-at-least", "value length must be at least " + minimum, value -> value.length() >= minimum);
    }

    @Override
    public Validation<Violation, String> validate(String value) {
        return delegate.validate(value);
    }
}
