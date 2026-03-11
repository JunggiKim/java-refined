package io.github.junggikim.refined.predicate.logical;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Objects;

public final class Not<T> implements Constraint<T> {

    private final Constraint<T> constraint;

    public Not(Constraint<T> constraint) {
        this.constraint = Objects.requireNonNull(constraint, "constraint");
    }

    @Override
    public Validation<Violation, T> validate(T value) {
        if (value == null) {
            return Validation.invalid(Violation.of("not", "value must fail the delegated constraint"));
        }
        return constraint.validate(value).isValid()
            ? Validation.invalid(Violation.of("not", "value must fail the delegated constraint"))
            : Validation.valid(value);
    }
}
