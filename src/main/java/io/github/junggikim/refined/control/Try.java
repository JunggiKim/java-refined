package io.github.junggikim.refined.control;

import java.util.Objects;
import java.util.function.Function;

/**
 * Minimal try-like abstraction that captures checked and unchecked exceptions.
 *
 * @param <T> success value type
 */
public interface Try<T> {

    /**
     * Functional supplier that may throw checked exceptions.
     *
     * @param <T> supplied value type
     */
    @FunctionalInterface
    interface CheckedSupplier<T> {
        /**
         * Produces a value or throws.
         *
         * @return supplied value
         * @throws Exception when evaluation fails
         */
        T get() throws Exception;
    }

    /**
     * Evaluates a supplier and captures thrown exceptions as a failure.
     *
     * @param supplier value supplier
     * @param <T> success type
     * @return success or failure
     */
    static <T> Try<T> of(CheckedSupplier<T> supplier) {
        try {
            return success(supplier.get());
        } catch (Exception exception) {
            return failure(exception);
        }
    }

    /**
     * Creates a success value.
     *
     * @param value success value
     * @param <T> success type
     * @return success result
     */
    static <T> Try<T> success(T value) {
        return new Success<>(value);
    }

    /**
     * Creates a failure value.
     *
     * @param throwable captured throwable
     * @param <T> success type
     * @return failure result
     */
    static <T> Try<T> failure(Throwable throwable) {
        return new Failure<>(throwable);
    }

    /**
     * Indicates whether this result is successful.
     *
     * @return {@code true} when success
     */
    boolean isSuccess();

    /**
     * Indicates whether this result is a failure.
     *
     * @return {@code true} when failure
     */
    default boolean isFailure() {
        return !isSuccess();
    }

    /**
     * Maps the success value when present.
     *
     * @param mapper success mapper
     * @param <R> mapped success type
     * @return mapped try
     */
    <R> Try<R> map(Function<? super T, ? extends R> mapper);

    /**
     * Flat-maps the success value when present.
     *
     * @param mapper success mapper
     * @param <R> mapped success type
     * @return mapped try
     * @throws NullPointerException when the mapper returns {@code null}
     */
    <R> Try<R> flatMap(Function<? super T, Try<R>> mapper);

    /**
     * Recovers a failure into a success value.
     *
     * @param mapper recovery function
     * @return original success or recovered success
     */
    Try<T> recover(Function<? super Throwable, ? extends T> mapper);

    /**
     * Returns the success value or rethrows the failure.
     *
     * @return success value
     */
    T get();

    /**
     * Returns the captured failure or throws when already successful.
     *
     * @return captured failure
     */
    Throwable getFailure();

    final class Success<T> implements Try<T> {
        private final T value;

        public Success(T value) {
            this.value = Objects.requireNonNull(value, "value");
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public <R> Try<R> map(Function<? super T, ? extends R> mapper) {
            return Try.of(() -> mapper.apply(value));
        }

        @Override
        public <R> Try<R> flatMap(Function<? super T, Try<R>> mapper) {
            return Objects.requireNonNull(mapper.apply(value), "mapper result");
        }

        @Override
        public Try<T> recover(Function<? super Throwable, ? extends T> mapper) {
            return this;
        }

        @Override
        public T get() {
            return value;
        }

        @Override
        public Throwable getFailure() {
            throw new IllegalStateException("Success does not contain a failure");
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Success)) {
                return false;
            }
            Success<?> success = (Success<?>) other;
            return value.equals(success.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return "Success[value=" + value + "]";
        }
    }

    final class Failure<T> implements Try<T> {
        private final Throwable throwable;

        public Failure(Throwable throwable) {
            this.throwable = Objects.requireNonNull(throwable, "throwable");
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public <R> Try<R> map(Function<? super T, ? extends R> mapper) {
            return Try.failure(throwable);
        }

        @Override
        public <R> Try<R> flatMap(Function<? super T, Try<R>> mapper) {
            return Try.failure(throwable);
        }

        @Override
        public Try<T> recover(Function<? super Throwable, ? extends T> mapper) {
            return Try.of(() -> mapper.apply(throwable));
        }

        @Override
        public T get() {
            if (throwable instanceof Error) {
                throw (Error) throwable;
            }
            if (throwable instanceof RuntimeException) {
                throw (RuntimeException) throwable;
            }
            throw new RuntimeException(throwable);
        }

        @Override
        public Throwable getFailure() {
            return throwable;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Failure)) {
                return false;
            }
            Failure<?> failure = (Failure<?>) other;
            return throwable.equals(failure.throwable);
        }

        @Override
        public int hashCode() {
            return Objects.hash(throwable);
        }

        @Override
        public String toString() {
            return "Failure[throwable=" + throwable + "]";
        }
    }
}
