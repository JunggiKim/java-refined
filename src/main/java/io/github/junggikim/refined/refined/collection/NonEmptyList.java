package io.github.junggikim.refined.refined.collection;

import java.util.AbstractList;
import java.util.List;
import java.util.stream.Stream;
import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

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
}
