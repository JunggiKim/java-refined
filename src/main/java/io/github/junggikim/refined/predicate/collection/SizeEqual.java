package io.github.junggikim.refined.predicate.collection;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Collection;

public final class SizeEqual<T extends Collection<?>> implements Constraint<T> {

    private final Constraint<T> delegate;

    public SizeEqual(int size) {
        this.delegate = RefinedSupport.require("size-equal", "collection size must equal " + size, value -> value.size() == size);
    }

    @Override
    public Validation<Violation, T> validate(T value) {
        return delegate.validate(value);
    }
}
