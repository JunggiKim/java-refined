package io.github.junggikim.refined.core;

import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/**
 * Validation rule that either accepts an input value or returns a structured violation.
 *
 * @param <T> input type validated by this constraint
 */
@FunctionalInterface
public interface Constraint<T> {

    /**
     * Validates an input value.
     *
     * @param value input value, possibly {@code null}
     * @return {@link Validation#valid(Object)} when accepted, otherwise an invalid result with a {@link Violation}
     */
    Validation<Violation, T> validate(T value);

    /**
     * Returns a short human-readable description for diagnostics.
     *
     * @return default description based on the implementation type
     */
    default String description() {
        return getClass().getSimpleName();
    }
}
