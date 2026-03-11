package io.github.junggikim.refined.predicate.collection;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

public final class ExistsElement<T> implements Constraint<Collection<T>> {

    private final Constraint<Collection<T>> delegate;

    public ExistsElement(Predicate<T> predicate) {
        Predicate<T> checked = Objects.requireNonNull(predicate, "predicate");
        this.delegate = RefinedSupport.require("exists-element", "at least one element must satisfy the predicate", value -> value.stream().anyMatch(checked));
    }

    @Override
    public Validation<Violation, Collection<T>> validate(Collection<T> value) {
        return delegate.validate(value);
    }
}
