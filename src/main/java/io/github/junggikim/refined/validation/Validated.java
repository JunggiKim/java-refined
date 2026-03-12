package io.github.junggikim.refined.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

/**
 * Error-accumulating validation result used when multiple failures should be preserved.
 *
 * @param <E> error element type
 * @param <A> success type
 */
public interface Validated<E, A> {

    /**
     * Creates a successful validated result.
     *
     * @param value validated value
     * @param <E> error element type
     * @param <A> success type
     * @return valid result
     */
    @NotNull
    static <E, A> Validated<E, A> valid(@NotNull A value) {
        return new Valid<>(value);
    }

    /**
     * Creates an invalid validated result with one or more accumulated errors.
     *
     * @param errors non-empty immutable error list
     * @param <E> error element type
     * @param <A> success type
     * @return invalid result
     */
    @NotNull
    static <E, A> Validated<E, A> invalid(@NotNull List<E> errors) {
        return new Invalid<>(errors);
    }

    /**
     * Indicates whether this result contains a value.
     *
     * @return {@code true} when valid
     */
    boolean isValid();

    /**
     * Indicates whether this result contains errors.
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
    <B> Validated<E, B> map(@NotNull Function<? super A, ? extends B> mapper);

    /**
     * Zips two validated results and accumulates both error lists when both sides fail.
     *
     * @param other second validated result
     * @param mapper success combiner
     * @param <B> other success type
     * @param <C> combined success type
     * @return combined validated result
     */
    @NotNull
    default <B, C> Validated<E, C> zip(@NotNull Validated<E, B> other, @NotNull BiFunction<? super A, ? super B, ? extends C> mapper) {
        if (isValid() && other.isValid()) {
            return valid(mapper.apply(get(), other.get()));
        }
        if (isInvalid() && other.isInvalid()) {
            return invalid(merge(getErrors(), other.getErrors()));
        }
        return isInvalid() ? invalid(getErrors()) : invalid(other.getErrors());
    }

    /**
     * Returns the success value or throws if this result is invalid.
     *
     * @return validated value
     */
    @NotNull
    A get();

    /**
     * Returns the accumulated errors or throws if this result is valid.
     *
     * @return immutable error list
     */
    @NotNull
    List<E> getErrors();

    /**
     * Concatenates two immutable error lists.
     *
     * @param left left error list
     * @param right right error list
     * @param <E> error element type
     * @return merged immutable list
     */
    @NotNull
    static <E> List<E> merge(@NotNull List<E> left, @NotNull List<E> right) {
        ArrayList<E> merged = new ArrayList<E>(left.size() + right.size());
        merged.addAll(left);
        merged.addAll(right);
        return Collections.unmodifiableList(merged);
    }

    final class Valid<E, A> implements Validated<E, A> {
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
        public <B> Validated<E, B> map(@NotNull Function<? super A, ? extends B> mapper) {
            return valid(mapper.apply(value));
        }

        @Override
        @NotNull
        public A get() {
            return value;
        }

        @Override
        @NotNull
        public List<E> getErrors() {
            throw new IllegalStateException("Valid does not contain errors");
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

    final class Invalid<E, A> implements Validated<E, A> {
        private final List<E> errors;

        public Invalid(@NotNull List<E> errors) {
            Objects.requireNonNull(errors, "errors");
            this.errors = copyErrors(errors);
            if (this.errors.isEmpty()) {
                throw new IllegalArgumentException("errors must not be empty");
            }
        }

        @Override
        public boolean isValid() {
            return false;
        }

        @Override
        @NotNull
        public <B> Validated<E, B> map(@NotNull Function<? super A, ? extends B> mapper) {
            return invalid(errors);
        }

        @Override
        @NotNull
        public A get() {
            throw new IllegalStateException("Invalid does not contain a value");
        }

        @Override
        @NotNull
        public List<E> getErrors() {
            return Collections.unmodifiableList(new ArrayList<E>(errors));
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
            return errors.equals(invalid.errors);
        }

        @Override
        public int hashCode() {
            return Objects.hash(errors);
        }

        @Override
        @NotNull
        public String toString() {
            return "Invalid[errors=" + errors + "]";
        }
    }

    /**
     * Copies and validates an error list, ensuring null-free immutable storage.
     *
     * @param errors source error list
     * @param <E> error element type
     * @return immutable copied list
     */
    @NotNull
    static <E> List<E> copyErrors(@NotNull List<E> errors) {
        ArrayList<E> copy = new ArrayList<E>(errors.size());
        for (E error : errors) {
            copy.add(Objects.requireNonNull(error, "error"));
        }
        return Collections.unmodifiableList(copy);
    }
}
