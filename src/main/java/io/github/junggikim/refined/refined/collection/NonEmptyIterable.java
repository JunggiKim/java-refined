package io.github.junggikim.refined.refined.collection;

import java.util.AbstractList;
import java.util.List;
import java.util.stream.Stream;
import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class NonEmptyIterable<T> extends AbstractList<T> {

    private final List<T> elements;

    private NonEmptyIterable(List<T> elements) {
        this.elements = elements;
    }

    public static <T> Validation<Violation, NonEmptyIterable<T>> of(Iterable<T> value) {
        return RefinedSupport.nonEmptyIterableSnapshot(value).map(NonEmptyIterable::new);
    }

    public static <T> NonEmptyIterable<T> unsafeOf(Iterable<T> value) {
        Validation<Violation, NonEmptyIterable<T>> result = of(value);
        if (result.isValid()) {
            return result.get();
        }
        throw new RefinementException(result.getError());
    }

    public static <T> Validation<Violation, NonEmptyIterable<T>> of(Stream<T> value) {
        return ofStream(value);
    }

    public static <T> NonEmptyIterable<T> unsafeOf(Stream<T> value) {
        return unsafeOfStream(value);
    }

    public static <T> Validation<Violation, NonEmptyIterable<T>> ofStream(Stream<T> value) {
        return RefinedSupport.nonEmptyIterableStreamSnapshot(value).map(snapshot -> new NonEmptyIterable<T>(snapshot));
    }

    public static <T> NonEmptyIterable<T> unsafeOfStream(Stream<T> value) {
        Validation<Violation, NonEmptyIterable<T>> result = ofStream(value);
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
