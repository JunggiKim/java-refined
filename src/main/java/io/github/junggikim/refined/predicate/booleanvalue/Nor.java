package io.github.junggikim.refined.predicate.booleanvalue;

import java.util.List;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class Nor implements Constraint<List<Boolean>> {

    private final Constraint<List<Boolean>> delegate = RefinedSupport.norBooleanList();

    @Override
    public Validation<Violation, List<Boolean>> validate(List<Boolean> value) {
        return delegate.validate(value);
    }
}
