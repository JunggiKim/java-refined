package io.github.junggikim.refined.predicate.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Objects;

public final class EqualTo<T extends Comparable<T>> implements Constraint<T> {

    private final Constraint<T> delegate;

    public EqualTo(T expected) {
        T bound = Objects.requireNonNull(expected, "expected");
        this.delegate = RefinedSupport.require("equal-to", "value must be equal to " + bound, value -> value.compareTo(bound) == 0);
    }

    @Override
    public Validation<Violation, T> validate(T value) {
        return delegate.validate(value);
    }
}
