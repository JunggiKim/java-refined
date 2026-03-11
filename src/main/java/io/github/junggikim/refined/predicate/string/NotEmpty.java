package io.github.junggikim.refined.predicate.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class NotEmpty implements Constraint<String> {

    private final Constraint<String> delegate = RefinedSupport.require("not-empty", "value must not be empty", value -> !value.isEmpty());

    @Override
    public Validation<Violation, String> validate(String value) {
        return delegate.validate(value);
    }
}
