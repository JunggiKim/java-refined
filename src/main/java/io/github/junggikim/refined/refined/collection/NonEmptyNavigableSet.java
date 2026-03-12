package io.github.junggikim.refined.refined.collection;

import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.stream.Stream;
import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class NonEmptyNavigableSet<T> extends AbstractSet<T> implements NavigableSet<T> {

    private final NavigableSet<T> elements;

    private NonEmptyNavigableSet(NavigableSet<T> elements) {
        this.elements = elements;
    }

    public static <T> Validation<Violation, NonEmptyNavigableSet<T>> of(NavigableSet<T> value) {
        return RefinedSupport.nonEmptyNavigableSetSnapshot(value).map(NonEmptyNavigableSet::new);
    }

    public static <T> NonEmptyNavigableSet<T> unsafeOf(NavigableSet<T> value) {
        Validation<Violation, NonEmptyNavigableSet<T>> result = of(value);
        if (result.isValid()) {
            return result.get();
        }
        throw new RefinementException(result.getError());
    }

    public static <T extends Comparable<? super T>> Validation<Violation, NonEmptyNavigableSet<T>> ofStream(
        Stream<T> value
    ) {
        return RefinedSupport.nonEmptyNavigableSetStreamSnapshot(value).map(snapshot -> new NonEmptyNavigableSet<T>(snapshot));
    }

    public static <T> Validation<Violation, NonEmptyNavigableSet<T>> ofStream(
        Stream<T> value,
        Comparator<? super T> comparator
    ) {
        return RefinedSupport.nonEmptyNavigableSetStreamSnapshot(value, comparator)
            .map(snapshot -> new NonEmptyNavigableSet<T>(snapshot));
    }

    public static <T extends Comparable<? super T>> NonEmptyNavigableSet<T> unsafeOfStream(Stream<T> value) {
        Validation<Violation, NonEmptyNavigableSet<T>> result = ofStream(value);
        if (result.isValid()) {
            return result.get();
        }
        throw new RefinementException(result.getError());
    }

    public static <T> NonEmptyNavigableSet<T> unsafeOfStream(
        Stream<T> value,
        Comparator<? super T> comparator
    ) {
        Validation<Violation, NonEmptyNavigableSet<T>> result = ofStream(value, comparator);
        if (result.isValid()) {
            return result.get();
        }
        throw new RefinementException(result.getError());
    }

    @Override
    public Iterator<T> iterator() {
        return elements.iterator();
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public boolean contains(Object value) {
        return elements.contains(value);
    }

    @Override
    public T lower(T value) {
        return elements.lower(value);
    }

    @Override
    public T floor(T value) {
        return elements.floor(value);
    }

    @Override
    public T ceiling(T value) {
        return elements.ceiling(value);
    }

    @Override
    public T higher(T value) {
        return elements.higher(value);
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
    public Comparator<? super T> comparator() {
        return elements.comparator();
    }

    @Override
    public NavigableSet<T> descendingSet() {
        return elements.descendingSet();
    }

    @Override
    public Iterator<T> descendingIterator() {
        return elements.descendingIterator();
    }

    @Override
    public NavigableSet<T> subSet(T fromElement, boolean fromInclusive, T toElement, boolean toInclusive) {
        return elements.subSet(fromElement, fromInclusive, toElement, toInclusive);
    }

    @Override
    public NavigableSet<T> headSet(T toElement, boolean inclusive) {
        return elements.headSet(toElement, inclusive);
    }

    @Override
    public NavigableSet<T> tailSet(T fromElement, boolean inclusive) {
        return elements.tailSet(fromElement, inclusive);
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return elements.subSet(fromElement, true, toElement, false);
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        return elements.headSet(toElement, false);
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return elements.tailSet(fromElement, true);
    }

    @Override
    public T first() {
        return elements.first();
    }

    @Override
    public T last() {
        return elements.last();
    }
}
