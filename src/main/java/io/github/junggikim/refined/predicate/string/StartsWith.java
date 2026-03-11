package io.github.junggikim.refined.predicate.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Objects;

public final class StartsWith implements Constraint<String> {

    private final Constraint<String> delegate;

    public StartsWith(String prefix) {
        String token = Objects.requireNonNull(prefix, "prefix");
        this.delegate = RefinedSupport.require("starts-with", "value must start with " + token, value -> value.startsWith(token));
    }

    @Override
    public Validation<Violation, String> validate(String value) {
        return delegate.validate(value);
    }
}
