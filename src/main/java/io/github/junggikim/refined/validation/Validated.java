package io.github.junggikim.refined.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

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
    static <E, A> Validated<E, A> valid(A value) {
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
    static <E, A> Validated<E, A> invalid(List<E> errors) {
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
    <B> Validated<E, B> map(Function<? super A, ? extends B> mapper);

    /**
     * Zips two validated results and accumulates both error lists when both sides fail.
     *
     * @param other second validated result
     * @param mapper success combiner
     * @param <B> other success type
     * @param <C> combined success type
     * @return combined validated result
     */
    default <B, C> Validated<E, C> zip(Validated<E, B> other, BiFunction<? super A, ? super B, ? extends C> mapper) {
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
    A get();

    /**
     * Returns the accumulated errors or throws if this result is valid.
     *
     * @return immutable error list
     */
    List<E> getErrors();

    /**
     * Concatenates two immutable error lists.
     *
     * @param left left error list
     * @param right right error list
     * @param <E> error element type
     * @return merged immutable list
     */
    static <E> List<E> merge(List<E> left, List<E> right) {
        ArrayList<E> merged = new ArrayList<E>(left.size() + right.size());
        merged.addAll(left);
        merged.addAll(right);
        return Collections.unmodifiableList(merged);
    }

    final class Valid<E, A> implements Validated<E, A> {
        private final A value;

        public Valid(A value) {
            this.value = Objects.requireNonNull(value, "value");
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public <B> Validated<E, B> map(Function<? super A, ? extends B> mapper) {
            return valid(mapper.apply(value));
        }

        @Override
        public A get() {
            return value;
        }

        @Override
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
        public String toString() {
            return "Valid[value=" + value + "]";
        }
    }

    final class Invalid<E, A> implements Validated<E, A> {
        private final List<E> errors;

        public Invalid(List<E> errors) {
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
        public <B> Validated<E, B> map(Function<? super A, ? extends B> mapper) {
            return invalid(errors);
        }

        @Override
        public A get() {
            throw new IllegalStateException("Invalid does not contain a value");
        }

        @Override
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
    static <E> List<E> copyErrors(List<E> errors) {
        ArrayList<E> copy = new ArrayList<E>(errors.size());
        for (E error : errors) {
            copy.add(Objects.requireNonNull(error, "error"));
        }
        return Collections.unmodifiableList(copy);
    }
}
