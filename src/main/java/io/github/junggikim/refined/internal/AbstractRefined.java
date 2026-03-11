package io.github.junggikim.refined.internal;

import io.github.junggikim.refined.core.Refined;
import java.util.Objects;

/**
 * Shared immutable base class for concrete refined wrappers.
 *
 * @param <T> wrapped value type
 */
public abstract class AbstractRefined<T> implements Refined<T> {

    private final T value;

    /**
     * Creates a refined wrapper around a validated runtime value.
     *
     * @param value validated non-null value
     */
    protected AbstractRefined(T value) {
        this.value = Objects.requireNonNull(value, "value");
    }

    @Override
    public final T value() {
        return value;
    }

    @Override
    public final boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        AbstractRefined<?> that = (AbstractRefined<?>) other;
        return value.equals(that.value);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getClass(), value);
    }

    @Override
    public final String toString() {
        return typeName() + "[value=" + value + "]";
    }
}
