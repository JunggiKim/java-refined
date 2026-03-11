package io.github.junggikim.refined.control;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Minimal optional value type used by the library without external dependencies.
 *
 * @param <T> wrapped value type
 */
public interface Option<T> {

    /**
     * Creates a defined option.
     *
     * @param value non-null value
     * @param <T> value type
     * @return defined option
     */
    static <T> Option<T> some(T value) {
        return new Some<>(value);
    }

    /**
     * Returns the shared empty option singleton.
     *
     * @param <T> value type
     * @return empty option
     */
    @SuppressWarnings("unchecked")
    static <T> Option<T> none() {
        return (Option<T>) None.INSTANCE;
    }

    /**
     * Creates {@code some(value)} for non-null values and {@code none()} otherwise.
     *
     * @param value nullable value
     * @param <T> value type
     * @return optional value
     */
    static <T> Option<T> ofNullable(T value) {
        return value == null ? none() : some(value);
    }

    /**
     * Indicates whether a value is present.
     *
     * @return {@code true} when defined
     */
    boolean isDefined();

    /**
     * Indicates whether this option is empty.
     *
     * @return {@code true} when empty
     */
    default boolean isEmpty() {
        return !isDefined();
    }

    /**
     * Maps the contained value when present.
     *
     * @param mapper mapping function
     * @param <R> mapped value type
     * @return mapped option
     */
    <R> Option<R> map(Function<? super T, ? extends R> mapper);

    /**
     * Flat-maps the contained value when present.
     *
     * @param mapper mapping function
     * @param <R> mapped value type
     * @return mapped option
     */
    <R> Option<R> flatMap(Function<? super T, Option<R>> mapper);

    /**
     * Keeps the value only when the predicate matches.
     *
     * @param predicate filter predicate
     * @return filtered option
     */
    Option<T> filter(Predicate<? super T> predicate);

    /**
     * Returns the contained value or a fallback.
     *
     * @param defaultValue fallback value
     * @return contained value or fallback
     */
    T getOrElse(T defaultValue);

    /**
     * Returns the contained value or computes a fallback lazily.
     *
     * @param supplier fallback supplier
     * @return contained value or supplied fallback
     */
    T getOrElseGet(Supplier<? extends T> supplier);

    /**
     * Returns the contained value or throws when empty.
     *
     * @return contained value
     */
    T get();

    final class Some<T> implements Option<T> {
        private final T value;

        public Some(T value) {
            this.value = Objects.requireNonNull(value, "value");
        }

        @Override
        public boolean isDefined() {
            return true;
        }

        @Override
        public <R> Option<R> map(Function<? super T, ? extends R> mapper) {
            return Option.ofNullable(mapper.apply(value));
        }

        @Override
        public <R> Option<R> flatMap(Function<? super T, Option<R>> mapper) {
            return Objects.requireNonNull(mapper.apply(value), "mapper result");
        }

        @Override
        public Option<T> filter(Predicate<? super T> predicate) {
            return predicate.test(value) ? this : Option.none();
        }

        @Override
        public T getOrElse(T defaultValue) {
            return value;
        }

        @Override
        public T getOrElseGet(Supplier<? extends T> supplier) {
            return value;
        }

        @Override
        public T get() {
            return value;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Some)) {
                return false;
            }
            Some<?> some = (Some<?>) other;
            return value.equals(some.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return "Some[value=" + value + "]";
        }
    }

    final class None<T> implements Option<T> {
        private static final None<?> INSTANCE = new None<>();

        private None() {
        }

        @Override
        public boolean isDefined() {
            return false;
        }

        @Override
        public <R> Option<R> map(Function<? super T, ? extends R> mapper) {
            return Option.none();
        }

        @Override
        public <R> Option<R> flatMap(Function<? super T, Option<R>> mapper) {
            return Option.none();
        }

        @Override
        public Option<T> filter(Predicate<? super T> predicate) {
            return this;
        }

        @Override
        public T getOrElse(T defaultValue) {
            return defaultValue;
        }

        @Override
        public T getOrElseGet(Supplier<? extends T> supplier) {
            return supplier.get();
        }

        @Override
        public T get() {
            throw new NoSuchElementException("Option.none");
        }

        @Override
        public String toString() {
            return "None";
        }
    }
}
