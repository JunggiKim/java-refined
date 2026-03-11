package io.github.junggikim.refined.newtype;

import io.github.junggikim.refined.core.Refined;

/**
 * Marker interface for nominal wrappers that preserve the underlying runtime value shape.
 *
 * @param <T> wrapped runtime value type
 */
public interface Newtype<T> extends Refined<T> {
}
