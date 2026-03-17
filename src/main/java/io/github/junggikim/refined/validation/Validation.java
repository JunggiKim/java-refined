package io.github.junggikim.refined.validation;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    /**
     * Returns the value if valid, otherwise returns {@code other}.
     *
     * @param other fallback value
     * @return validated value or {@code other}
     */
    @NotNull
    default A getOrElse(@NotNull A other) {
        Objects.requireNonNull(other, "other");
        return isValid() ? get() : other;
    }

    /**
     * Returns the value if valid, otherwise computes a fallback from the error.
     *
     * @param handler function that converts the error into a fallback value
     * @return validated value or handler result
     */
    @NotNull
    default A getOrElse(@NotNull Function<? super E, ? extends A> handler) {
        Objects.requireNonNull(handler, "handler");
        return isValid() ? get() : Objects.requireNonNull(handler.apply(getError()), "handler result");
    }

    /**
     * Returns the value if valid, otherwise {@code null}.
     *
     * @return validated value or {@code null}
     */
    @Nullable
    default A getOrNull() {
        return isValid() ? get() : null;
    }

    /**
     * Returns an {@link Optional} containing the value if valid, or empty.
     *
     * @return optional of the validated value
     */
    @NotNull
    default Optional<A> toOptional() {
        return isValid() ? Optional.of(get()) : Optional.<A>empty();
    }

    /**
     * Executes the given action when this result is valid and returns {@code this} for chaining.
     *
     * @param action side-effect to run on the success value
     * @return this validation
     */
    @NotNull
    default Validation<E, A> onValid(@NotNull Consumer<? super A> action) {
        Objects.requireNonNull(action, "action");
        if (isValid()) {
            action.accept(get());
        }
        return this;
    }

    /**
     * Executes the given action when this result is invalid and returns {@code this} for chaining.
     *
     * @param action side-effect to run on the error
     * @return this validation
     */
    @NotNull
    default Validation<E, A> onInvalid(@NotNull Consumer<? super E> action) {
        Objects.requireNonNull(action, "action");
        if (isInvalid()) {
            action.accept(getError());
        }
        return this;
    }

    /**
     * Maps the error when invalid, passes through when valid.
     *
     * @param f error mapping function
     * @param <E2> new error type
     * @return mapped validation
     */
    @NotNull
    default <E2> Validation<E2, A> mapError(@NotNull Function<? super E, ? extends E2> f) {
        Objects.requireNonNull(f, "f");
        return isValid() ? valid(get()) : invalid(f.apply(getError()));
    }

    /**
     * Recovers from an error by converting it to a success value.
     *
     * @param f recovery function
     * @return this if valid, otherwise a valid result from {@code f}
     */
    @NotNull
    default Validation<E, A> recover(@NotNull Function<? super E, ? extends A> f) {
        Objects.requireNonNull(f, "f");
        return isValid() ? this : valid(f.apply(getError()));
    }

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
