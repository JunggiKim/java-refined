package io.github.junggikim.refined.control;

import java.util.Objects;
import java.util.function.Function;

/**
 * Two-branch value that models a left or right result.
 *
 * @param <L> left branch type, typically the error or alternative branch
 * @param <R> right branch type, typically the success branch
 */
public interface Either<L, R> {

    /**
     * Creates a left value.
     *
     * @param value non-null left value
     * @param <L> left type
     * @param <R> right type
     * @return left branch
     */
    static <L, R> Either<L, R> left(L value) {
        return new Left<>(value);
    }

    /**
     * Creates a right value.
     *
     * @param value non-null right value
     * @param <L> left type
     * @param <R> right type
     * @return right branch
     */
    static <L, R> Either<L, R> right(R value) {
        return new Right<>(value);
    }

    /**
     * Indicates whether this is a right value.
     *
     * @return {@code true} when right
     */
    boolean isRight();

    /**
     * Indicates whether this is a left value.
     *
     * @return {@code true} when left
     */
    default boolean isLeft() {
        return !isRight();
    }

    /**
     * Maps the right value when present.
     *
     * @param mapper right mapper
     * @param <RR> mapped right type
     * @return mapped either
     */
    <RR> Either<L, RR> map(Function<? super R, ? extends RR> mapper);

    /**
     * Maps the left value when present.
     *
     * @param mapper left mapper
     * @param <LL> mapped left type
     * @return mapped either
     */
    <LL> Either<LL, R> mapLeft(Function<? super L, ? extends LL> mapper);

    /**
     * Flat-maps the right value when present.
     *
     * @param mapper right mapper
     * @param <RR> mapped right type
     * @return mapped either
     */
    <RR> Either<L, RR> flatMap(Function<? super R, Either<L, RR>> mapper);

    /**
     * Folds both branches into a single value.
     *
     * @param onLeft left handler
     * @param onRight right handler
     * @param <T> fold result type
     * @return branch result
     */
    <T> T fold(Function<? super L, ? extends T> onLeft, Function<? super R, ? extends T> onRight);

    /**
     * Swaps left and right branches.
     *
     * @return swapped either
     */
    Either<R, L> swap();

    final class Left<L, R> implements Either<L, R> {
        private final L value;

        public Left(L value) {
            this.value = Objects.requireNonNull(value, "value");
        }

        @Override
        public boolean isRight() {
            return false;
        }

        @Override
        public <RR> Either<L, RR> map(Function<? super R, ? extends RR> mapper) {
            return Either.left(value);
        }

        @Override
        public <LL> Either<LL, R> mapLeft(Function<? super L, ? extends LL> mapper) {
            return Either.left(mapper.apply(value));
        }

        @Override
        public <RR> Either<L, RR> flatMap(Function<? super R, Either<L, RR>> mapper) {
            return Either.left(value);
        }

        @Override
        public <T> T fold(Function<? super L, ? extends T> onLeft, Function<? super R, ? extends T> onRight) {
            return onLeft.apply(value);
        }

        @Override
        public Either<R, L> swap() {
            return Either.right(value);
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Left)) {
                return false;
            }
            Left<?, ?> left = (Left<?, ?>) other;
            return value.equals(left.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return "Left[value=" + value + "]";
        }
    }

    final class Right<L, R> implements Either<L, R> {
        private final R value;

        public Right(R value) {
            this.value = Objects.requireNonNull(value, "value");
        }

        @Override
        public boolean isRight() {
            return true;
        }

        @Override
        public <RR> Either<L, RR> map(Function<? super R, ? extends RR> mapper) {
            return Either.right(mapper.apply(value));
        }

        @Override
        public <LL> Either<LL, R> mapLeft(Function<? super L, ? extends LL> mapper) {
            return Either.right(value);
        }

        @Override
        public <RR> Either<L, RR> flatMap(Function<? super R, Either<L, RR>> mapper) {
            return Objects.requireNonNull(mapper.apply(value), "mapper result");
        }

        @Override
        public <T> T fold(Function<? super L, ? extends T> onLeft, Function<? super R, ? extends T> onRight) {
            return onRight.apply(value);
        }

        @Override
        public Either<R, L> swap() {
            return Either.left(value);
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Right)) {
                return false;
            }
            Right<?, ?> right = (Right<?, ?>) other;
            return value.equals(right.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return "Right[value=" + value + "]";
        }
    }
}
