package io.github.junggikim.refined.newtype;

import io.github.junggikim.refined.core.Refined;

/**
 * Marker interface for wrappers that semantically specialize an existing value domain.
 *
 * @param <T> wrapped runtime value type
 */
public interface Subtype<T> extends Refined<T> {
}
