package io.github.junggikim.refined.refined.collection;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Stream;
import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Deque;

/** A non-empty immutable {@code Deque} snapshot with no null elements. */
public final class NonEmptyDeque<T> extends AbstractList<T> implements Deque<T> {

    private final List<T> elements;

    private NonEmptyDeque(List<T> elements) {
        this.elements = elements;
    }

    public static <T> Validation<Violation, NonEmptyDeque<T>> of(Deque<T> value) {
        return RefinedSupport.nonEmptyDequeSnapshot(value).map(NonEmptyDeque::new);
    }

    public static <T> NonEmptyDeque<T> unsafeOf(Deque<T> value) {
        Validation<Violation, NonEmptyDeque<T>> result = of(value);
        if (result.isValid()) {
            return result.get();
        }
        throw new RefinementException(result.getError());
    }

    public static <T> Validation<Violation, NonEmptyDeque<T>> of(Stream<T> value) {
        return ofStream(value);
    }

    public static <T> NonEmptyDeque<T> unsafeOf(Stream<T> value) {
        return unsafeOfStream(value);
    }

    public static <T> Validation<Violation, NonEmptyDeque<T>> ofStream(Stream<T> value) {
        return RefinedSupport.nonEmptyDequeStreamSnapshot(value).map(snapshot -> new NonEmptyDeque<T>(snapshot));
    }

    public static <T> NonEmptyDeque<T> unsafeOfStream(Stream<T> value) {
        Validation<Violation, NonEmptyDeque<T>> result = ofStream(value);
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

    public T first() {
        return get(0);
    }

    public T last() {
        return elements.get(elements.size() - 1);
    }

    @Override
    public void addFirst(T value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addLast(T value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean offerFirst(T value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean offerLast(T value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T removeFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T removeLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T pollFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T pollLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T getFirst() {
        return first();
    }

    @Override
    public T getLast() {
        return last();
    }

    @Override
    public T peekFirst() {
        return first();
    }

    @Override
    public T peekLast() {
        return last();
    }

    @Override
    public boolean removeFirstOccurrence(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeLastOccurrence(Object value) {
        throw new UnsupportedOperationException();
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
    public T element() {
        return first();
    }

    @Override
    public T peek() {
        return first();
    }

    @Override
    public void push(T value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T pop() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> descendingIterator() {
        final ListIterator<T> iterator = elements.listIterator(elements.size());
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return iterator.hasPrevious();
            }

            @Override
            public T next() {
                return iterator.previous();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
