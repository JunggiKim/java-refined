package io.github.junggikim.refined.predicate.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Objects;

public final class Contains implements Constraint<String> {

    private final Constraint<String> delegate;

    public Contains(String infix) {
        String token = Objects.requireNonNull(infix, "infix");
        this.delegate = RefinedSupport.require("contains", "value must contain " + token, value -> value.contains(token));
    }

    @Override
    public Validation<Violation, String> validate(String value) {
        return delegate.validate(value);
    }
}
