package io.github.junggikim.refined.predicate.logical;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class AllOf<T> implements Constraint<T> {

    private final List<Constraint<T>> constraints;

    public AllOf(List<Constraint<T>> constraints) {
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
            return Validation.invalid(Violation.of("all-of", "value must satisfy all constraints"));
        }
        for (Constraint<T> constraint : constraints) {
            Validation<Violation, T> result = constraint.validate(value);
            if (result.isInvalid()) {
                return result;
            }
        }
        return Validation.valid(value);
    }
}
