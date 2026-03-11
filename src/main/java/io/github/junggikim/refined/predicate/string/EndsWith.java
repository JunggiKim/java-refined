package io.github.junggikim.refined.predicate.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Objects;

public final class EndsWith implements Constraint<String> {

    private final Constraint<String> delegate;

    public EndsWith(String suffix) {
        String token = Objects.requireNonNull(suffix, "suffix");
        this.delegate = RefinedSupport.require("ends-with", "value must end with " + token, value -> value.endsWith(token));
    }

    @Override
    public Validation<Violation, String> validate(String value) {
        return delegate.validate(value);
    }
}
