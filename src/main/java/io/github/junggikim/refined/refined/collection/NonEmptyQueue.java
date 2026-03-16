package io.github.junggikim.refined.refined.collection;

import java.util.AbstractList;
import java.util.List;
import java.util.stream.Stream;
import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Queue;

/** A non-empty immutable {@code Queue} snapshot with no null elements. */
public final class NonEmptyQueue<T> extends AbstractList<T> implements Queue<T> {

    private final List<T> elements;

    private NonEmptyQueue(List<T> elements) {
        this.elements = elements;
    }

    public static <T> Validation<Violation, NonEmptyQueue<T>> of(Queue<T> value) {
        return RefinedSupport.nonEmptyQueueSnapshot(value).map(NonEmptyQueue::new);
    }

    public static <T> NonEmptyQueue<T> unsafeOf(Queue<T> value) {
        Validation<Violation, NonEmptyQueue<T>> result = of(value);
        if (result.isValid()) {
            return result.get();
        }
        throw new RefinementException(result.getError());
    }

    public static <T> Validation<Violation, NonEmptyQueue<T>> of(Stream<T> value) {
        return ofStream(value);
    }

    public static <T> NonEmptyQueue<T> unsafeOf(Stream<T> value) {
        return unsafeOfStream(value);
    }

    public static <T> Validation<Violation, NonEmptyQueue<T>> ofStream(Stream<T> value) {
        return RefinedSupport.nonEmptyQueueStreamSnapshot(value).map(snapshot -> new NonEmptyQueue<T>(snapshot));
    }

    public static <T> NonEmptyQueue<T> unsafeOfStream(Stream<T> value) {
        Validation<Violation, NonEmptyQueue<T>> result = ofStream(value);
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

    @Override
    public boolean offer(T value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T poll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T peek() {
        return get(0);
    }

    @Override
    public T element() {
        return get(0);
    }

    public T last() {
        return elements.get(elements.size() - 1);
    }
}
