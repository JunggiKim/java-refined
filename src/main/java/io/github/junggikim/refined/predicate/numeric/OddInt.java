package io.github.junggikim.refined.predicate.numeric;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class OddInt implements Constraint<Integer> {

    private final Constraint<Integer> delegate = RefinedSupport.oddInt();

    @Override
    public Validation<Violation, Integer> validate(Integer value) {
        return delegate.validate(value);
    }
}
