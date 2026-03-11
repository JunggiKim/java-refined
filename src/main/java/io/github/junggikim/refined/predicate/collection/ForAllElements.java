package io.github.junggikim.refined.predicate.collection;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

public final class ForAllElements<T> implements Constraint<Collection<T>> {

    private final Constraint<Collection<T>> delegate;

    public ForAllElements(Predicate<T> predicate) {
        Predicate<T> checked = Objects.requireNonNull(predicate, "predicate");
        this.delegate = RefinedSupport.require("for-all-elements", "all elements must satisfy the predicate", value -> value.stream().allMatch(checked));
    }

    @Override
    public Validation<Violation, Collection<T>> validate(Collection<T> value) {
        return delegate.validate(value);
    }
}
