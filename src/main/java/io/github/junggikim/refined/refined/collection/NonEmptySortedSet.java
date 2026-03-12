package io.github.junggikim.refined.refined.collection;

import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.stream.Stream;
import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class NonEmptySortedSet<T> extends AbstractSet<T> implements SortedSet<T> {

    private final SortedSet<T> elements;

    private NonEmptySortedSet(SortedSet<T> elements) {
        this.elements = elements;
    }

    public static <T> Validation<Violation, NonEmptySortedSet<T>> of(SortedSet<T> value) {
        return RefinedSupport.nonEmptySortedSetSnapshot(value).map(NonEmptySortedSet::new);
    }

    public static <T> NonEmptySortedSet<T> unsafeOf(SortedSet<T> value) {
        Validation<Violation, NonEmptySortedSet<T>> result = of(value);
        if (result.isValid()) {
            return result.get();
        }
        throw new RefinementException(result.getError());
    }

    public static <T extends Comparable<? super T>> Validation<Violation, NonEmptySortedSet<T>> ofStream(
        Stream<T> value
    ) {
        return RefinedSupport.nonEmptySortedSetStreamSnapshot(value).map(snapshot -> new NonEmptySortedSet<T>(snapshot));
    }

    public static <T> Validation<Violation, NonEmptySortedSet<T>> ofStream(
        Stream<T> value,
        Comparator<? super T> comparator
    ) {
        return RefinedSupport.nonEmptySortedSetStreamSnapshot(value, comparator).map(snapshot -> new NonEmptySortedSet<T>(snapshot));
    }

    public static <T extends Comparable<? super T>> NonEmptySortedSet<T> unsafeOfStream(Stream<T> value) {
        Validation<Violation, NonEmptySortedSet<T>> result = ofStream(value);
        if (result.isValid()) {
            return result.get();
        }
        throw new RefinementException(result.getError());
    }

    public static <T> NonEmptySortedSet<T> unsafeOfStream(
        Stream<T> value,
        Comparator<? super T> comparator
    ) {
        Validation<Violation, NonEmptySortedSet<T>> result = ofStream(value, comparator);
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
    public Comparator<? super T> comparator() {
        return elements.comparator();
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return elements.subSet(fromElement, toElement);
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        return elements.headSet(toElement);
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return elements.tailSet(fromElement);
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
