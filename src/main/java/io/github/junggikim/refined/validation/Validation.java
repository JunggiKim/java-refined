package io.github.junggikim.refined.validation;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

/**
 * Fail-fast validation result used by refined constructors and low-level constraints.
 *
 * @param <E> error type
 * @param <A> success type
 */
public interface Validation<E, A> {

    /**
     * Creates a successful validation result.
     *
     * @param value validated value
     * @param <E> error type
     * @param <A> success type
     * @return valid result
     */
    @NotNull
    static <E, A> Validation<E, A> valid(@NotNull A value) {
        return new Valid<>(value);
    }

    /**
     * Creates a failed validation result.
     *
     * @param error validation error
     * @param <E> error type
     * @param <A> success type
     * @return invalid result
     */
    @NotNull
    static <E, A> Validation<E, A> invalid(@NotNull E error) {
        return new Invalid<>(error);
    }

    /**
     * Indicates whether this result contains a validated value.
     *
     * @return {@code true} when valid
     */
    boolean isValid();

    /**
     * Indicates whether this result contains an error.
     *
     * @return {@code true} when invalid
     */
    default boolean isInvalid() {
        return !isValid();
    }

    /**
     * Maps the success value when valid.
     *
     * @param mapper mapping function
     * @param <B> mapped success type
     * @return mapped valid result or the original invalid result
     */
    @NotNull
    <B> Validation<E, B> map(@NotNull Function<? super A, ? extends B> mapper);

    /**
     * Chains another validation when this result is valid.
     *
     * @param mapper mapping function producing another validation
     * @param <B> mapped success type
     * @return chained validation or the original invalid result
     */
    @NotNull
    <B> Validation<E, B> flatMap(@NotNull Function<? super A, Validation<E, B>> mapper);

    /**
     * Folds both branches into a single value.
     *
     * @param onInvalid handler for the invalid branch
     * @param onValid handler for the valid branch
     * @param <B> fold result type
     * @return branch result
     */
    @NotNull
    <B> B fold(@NotNull Function<? super E, ? extends B> onInvalid, @NotNull Function<? super A, ? extends B> onValid);

    /**
     * Combines two validations and keeps the first encountered error when one side fails.
     *
     * @param other second validation
     * @param mapper success combiner
     * @param <B> other success type
     * @param <C> combined success type
     * @return combined validation result
     */
    @NotNull
    default <B, C> Validation<E, C> zip(
        @NotNull Validation<E, B> other,
        @NotNull BiFunction<? super A, ? super B, ? extends C> mapper
    ) {
        if (isValid() && other.isValid()) {
            return valid(mapper.apply(get(), other.get()));
        }
        return isInvalid() ? invalid(getError()) : invalid(other.getError());
    }

    /**
     * Returns the success value or throws if this is invalid.
     *
     * @return validated value
     */
    @NotNull
    A get();

    /**
     * Returns the error or throws if this is valid.
     *
     * @return validation error
     */
    @NotNull
    E getError();

    final class Valid<E, A> implements Validation<E, A> {
        private final A value;

        public Valid(@NotNull A value) {
            this.value = Objects.requireNonNull(value, "value");
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        @NotNull
        public <B> Validation<E, B> map(@NotNull Function<? super A, ? extends B> mapper) {
            return valid(mapper.apply(value));
        }

        @Override
        @NotNull
        public <B> Validation<E, B> flatMap(@NotNull Function<? super A, Validation<E, B>> mapper) {
            return Objects.requireNonNull(mapper.apply(value), "mapper result");
        }

        @Override
        @NotNull
        public <B> B fold(@NotNull Function<? super E, ? extends B> onInvalid, @NotNull Function<? super A, ? extends B> onValid) {
            return onValid.apply(value);
        }

        @Override
        @NotNull
        public A get() {
            return value;
        }

        @Override
        @NotNull
        public E getError() {
            throw new IllegalStateException("Valid does not contain an error");
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Valid)) {
                return false;
            }
            Valid<?, ?> valid = (Valid<?, ?>) other;
            return value.equals(valid.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        @NotNull
        public String toString() {
            return "Valid[value=" + value + "]";
        }
    }

    final class Invalid<E, A> implements Validation<E, A> {
        private final E error;

        public Invalid(@NotNull E error) {
            this.error = Objects.requireNonNull(error, "error");
        }

        @Override
        public boolean isValid() {
            return false;
        }

        @Override
        @NotNull
        public <B> Validation<E, B> map(@NotNull Function<? super A, ? extends B> mapper) {
            return invalid(error);
        }

        @Override
        @NotNull
        public <B> Validation<E, B> flatMap(@NotNull Function<? super A, Validation<E, B>> mapper) {
            return invalid(error);
        }

        @Override
        @NotNull
        public <B> B fold(@NotNull Function<? super E, ? extends B> onInvalid, @NotNull Function<? super A, ? extends B> onValid) {
            return onInvalid.apply(error);
        }

        @Override
        @NotNull
        public A get() {
            throw new IllegalStateException("Invalid does not contain a value");
        }

        @Override
        @NotNull
        public E getError() {
            return error;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Invalid)) {
                return false;
            }
            Invalid<?, ?> invalid = (Invalid<?, ?>) other;
            return error.equals(invalid.error);
        }

        @Override
        public int hashCode() {
            return Objects.hash(error);
        }

        @Override
        @NotNull
        public String toString() {
            return "Invalid[error=" + error + "]";
        }
    }
}
