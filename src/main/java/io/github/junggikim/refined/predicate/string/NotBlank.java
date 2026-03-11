package io.github.junggikim.refined.predicate.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class NotBlank implements Constraint<String> {

    private final Constraint<String> delegate = RefinedSupport.require("not-blank", "value must not be blank", value -> !RefinedSupport.isBlank(value));

    @Override
    public Validation<Violation, String> validate(String value) {
        return delegate.validate(value);
    }
}
