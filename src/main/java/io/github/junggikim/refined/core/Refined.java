package io.github.junggikim.refined.core;

/**
 * Marker interface for values that have already satisfied a domain constraint.
 *
 * @param <T> wrapped runtime value type
 */
public interface Refined<T> {

    /**
     * Returns the validated runtime value.
     *
     * @return immutable validated value
     */
    T value();

    /**
     * Returns a human-readable type name for diagnostics and {@code toString()} output.
     *
     * @return simple refined type name
     */
    default String typeName() {
        return getClass().getSimpleName();
    }
}
