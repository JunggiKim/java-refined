package io.github.junggikim.refined.control;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Inclusive-or value that can contain a left value, a right value, or both.
 *
 * @param <L> left branch type
 * @param <R> right branch type
 */
public interface Ior<L, R> {

    /**
     * Creates a left-only value.
     *
     * @param value left value
     * @param <L> left type
     * @param <R> right type
     * @return left branch
     */
    static <L, R> Ior<L, R> left(L value) {
        return new Left<>(value);
    }

    /**
     * Creates a right-only value.
     *
     * @param value right value
     * @param <L> left type
     * @param <R> right type
     * @return right branch
     */
    static <L, R> Ior<L, R> right(R value) {
        return new Right<>(value);
    }

    /**
     * Creates a value containing both branches.
     *
     * @param left left value
     * @param right right value
     * @param <L> left type
     * @param <R> right type
     * @return both branches
     */
    static <L, R> Ior<L, R> both(L left, R right) {
        return new Both<>(left, right);
    }

    /**
     * Folds all three states into a single value.
     *
     * @param onLeft left-only handler
     * @param onRight right-only handler
     * @param onBoth both-branch handler
     * @param <T> fold result type
     * @return fold result
     */
    <T> T fold(
        Function<? super L, ? extends T> onLeft,
        Function<? super R, ? extends T> onRight,
        BiFunction<? super L, ? super R, ? extends T> onBoth
    );

    final class Left<L, R> implements Ior<L, R> {
        private final L value;

        public Left(L value) {
            this.value = Objects.requireNonNull(value, "value");
        }

        @Override
        public <T> T fold(
            Function<? super L, ? extends T> onLeft,
            Function<? super R, ? extends T> onRight,
            BiFunction<? super L, ? super R, ? extends T> onBoth
        ) {
            return onLeft.apply(value);
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

    final class Right<L, R> implements Ior<L, R> {
        private final R value;

        public Right(R value) {
            this.value = Objects.requireNonNull(value, "value");
        }

        @Override
        public <T> T fold(
            Function<? super L, ? extends T> onLeft,
            Function<? super R, ? extends T> onRight,
            BiFunction<? super L, ? super R, ? extends T> onBoth
        ) {
            return onRight.apply(value);
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

    final class Both<L, R> implements Ior<L, R> {
        private final L left;
        private final R right;

        public Both(L left, R right) {
            this.left = Objects.requireNonNull(left, "left");
            this.right = Objects.requireNonNull(right, "right");
        }

        @Override
        public <T> T fold(
            Function<? super L, ? extends T> onLeft,
            Function<? super R, ? extends T> onRight,
            BiFunction<? super L, ? super R, ? extends T> onBoth
        ) {
            return onBoth.apply(left, right);
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Both)) {
                return false;
            }
            Both<?, ?> both = (Both<?, ?>) other;
            return left.equals(both.left) && right.equals(both.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right);
        }

        @Override
        public String toString() {
            return "Both[left=" + left + ", right=" + right + "]";
        }
    }
}
