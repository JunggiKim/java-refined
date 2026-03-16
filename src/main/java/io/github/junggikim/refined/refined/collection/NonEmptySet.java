package io.github.junggikim.refined.refined.collection;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;
import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

/** A non-empty immutable {@code Set} snapshot with no null elements. */
public final class NonEmptySet<T> extends AbstractSet<T> {

    private final Set<T> elements;

    private NonEmptySet(Set<T> elements) {
        this.elements = elements;
    }

    public static <T> Validation<Violation, NonEmptySet<T>> of(Set<T> value) {
        return RefinedSupport.nonEmptySetSnapshot(value).map(NonEmptySet::new);
    }

    public static <T> NonEmptySet<T> unsafeOf(Set<T> value) {
        Validation<Violation, NonEmptySet<T>> result = of(value);
        if (result.isValid()) {
            return result.get();
        }
        throw new RefinementException(result.getError());
    }

    public static <T> Validation<Violation, NonEmptySet<T>> of(Stream<T> value) {
        return ofStream(value);
    }

    public static <T> NonEmptySet<T> unsafeOf(Stream<T> value) {
        return unsafeOfStream(value);
    }

    public static <T> Validation<Violation, NonEmptySet<T>> ofStream(Stream<T> value) {
        return RefinedSupport.nonEmptySetStreamSnapshot(value).map(snapshot -> new NonEmptySet<T>(snapshot));
    }

    public static <T> NonEmptySet<T> unsafeOfStream(Stream<T> value) {
        Validation<Violation, NonEmptySet<T>> result = ofStream(value);
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
}
