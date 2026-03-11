package io.github.junggikim.refined.predicate.collection;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.List;

public final class AscendingList<T extends Comparable<T>> implements Constraint<List<T>> {

    private final Constraint<List<T>> delegate = RefinedSupport.require(
        "ascending-list", "list elements must be in non-decreasing order",
        list -> {
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i).compareTo(list.get(i + 1)) > 0) {
                    return false;
                }
            }
            return true;
        });

    @Override
    public Validation<Violation, List<T>> validate(List<T> value) {
        return delegate.validate(value);
    }
}
