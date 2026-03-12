package io.github.junggikim.refined.core;

import org.jetbrains.annotations.NotNull;

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
    @NotNull
    T value();

    /**
     * Kotlin-friendly bean getter bridge for {@link #value()}.
     *
     * @return immutable validated value
     */
    @NotNull
    default T getValue() {
        return value();
    }

    /**
     * Returns a human-readable type name for diagnostics and {@code toString()} output.
     *
     * @return simple refined type name
     */
    @NotNull
    default String typeName() {
        return getClass().getSimpleName();
    }
}
