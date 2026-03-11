package io.github.junggikim.refined.predicate.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class LengthAtMost implements Constraint<String> {

    private final Constraint<String> delegate;

    public LengthAtMost(int maximum) {
        this.delegate = RefinedSupport.require("length-at-most", "value length must be at most " + maximum, value -> value.length() <= maximum);
    }

    @Override
    public Validation<Violation, String> validate(String value) {
        return delegate.validate(value);
    }
}
