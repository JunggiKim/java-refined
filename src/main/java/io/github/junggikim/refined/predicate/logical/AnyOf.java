package io.github.junggikim.refined.predicate.logical;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class AnyOf<T> implements Constraint<T> {

    private final List<Constraint<T>> constraints;

    public AnyOf(List<Constraint<T>> constraints) {
        Objects.requireNonNull(constraints, "constraints");
        ArrayList<Constraint<T>> copy = new ArrayList<Constraint<T>>(constraints.size());
        for (Constraint<T> constraint : constraints) {
            copy.add(Objects.requireNonNull(constraint, "constraint"));
        }
        this.constraints = Collections.unmodifiableList(copy);
    }

    @Override
    public Validation<Violation, T> validate(T value) {
        if (value == null) {
            return Validation.invalid(Violation.of("any-of", "value must satisfy at least one constraint"));
        }
        for (Constraint<T> constraint : constraints) {
            if (constraint.validate(value).isValid()) {
                return Validation.valid(value);
            }
        }
        return Validation.invalid(Violation.of("any-of", "value must satisfy at least one constraint"));
    }
}
