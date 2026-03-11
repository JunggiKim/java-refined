package io.github.junggikim.refined.predicate.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Objects;

public final class GreaterOrEqual<T extends Comparable<T>> implements Constraint<T> {

    private final Constraint<T> delegate;

    public GreaterOrEqual(T minimum) {
        T bound = Objects.requireNonNull(minimum, "minimum");
        this.delegate = RefinedSupport.greaterOrEqual(bound, "greater-or-equal", "value must be greater than or equal to " + bound);
    }

    @Override
    public Validation<Violation, T> validate(T value) {
        return delegate.validate(value);
    }
}
