package io.github.junggikim.refined.refined.collection;

import java.util.AbstractList;
import java.util.List;
import java.util.stream.Stream;
import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** A non-empty immutable {@code List} snapshot with no null elements. */
public final class NonEmptyList<T> extends AbstractList<T> {

    private final List<T> elements;

    private NonEmptyList(List<T> elements) {
        this.elements = elements;
    }

    public static <T> Validation<Violation, NonEmptyList<T>> of(List<T> value) {
        return RefinedSupport.nonEmptyListSnapshot(value).map(NonEmptyList::new);
    }

    public static <T> NonEmptyList<T> unsafeOf(List<T> value) {
        Validation<Violation, NonEmptyList<T>> result = of(value);
        if (result.isValid()) {
            return result.get();
        }
        throw new RefinementException(result.getError());
    }

    public static <T> Validation<Violation, NonEmptyList<T>> of(Stream<T> value) {
        return ofStream(value);
    }

    public static <T> NonEmptyList<T> unsafeOf(Stream<T> value) {
        return unsafeOfStream(value);
    }

    public static <T> Validation<Violation, NonEmptyList<T>> ofStream(Stream<T> value) {
        return RefinedSupport.nonEmptyListStreamSnapshot(value).map(snapshot -> new NonEmptyList<T>(snapshot));
    }

    public static <T> NonEmptyList<T> unsafeOfStream(Stream<T> value) {
        Validation<Violation, NonEmptyList<T>> result = ofStream(value);
        if (result.isValid()) {
            return result.get();
        }
        throw new RefinementException(result.getError());
    }

    @Override
    public T get(int index) {
        return elements.get(index);
    }

    @Override
    public int size() {
        return elements.size();
    }

    public T head() {
        return get(0);
    }

    public List<T> tail() {
        return elements.subList(1, elements.size());
    }

    /**
     * Returns a validated instance, or falls back to {@code defaultValue} if invalid.
     *
     * @param value input to validate
     * @param defaultValue fallback (must itself be valid)
     * @param <T> element type
     * @return refined instance
     * @throws RefinementException if defaultValue is also invalid
     */
    @org.jetbrains.annotations.NotNull
    public static <T> NonEmptyList<T> ofOrElse(
        @org.jetbrains.annotations.Nullable List<T> value,
        @org.jetbrains.annotations.NotNull List<T> defaultValue
    ) {
        Validation<Violation, NonEmptyList<T>> result = of(value);
        return result.isValid() ? result.get() : unsafeOf(defaultValue);
    }
}
