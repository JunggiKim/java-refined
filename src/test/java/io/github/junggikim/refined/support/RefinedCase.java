package io.github.junggikim.refined.support;

import io.github.junggikim.refined.core.Refined;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.function.Function;

public final class RefinedCase<T> {
    private final String typeName;
    private final String code;
    private final Function<T, Validation<Violation, ? extends Refined<T>>> of;
    private final Function<T, ? extends Refined<T>> unsafeOf;
    private final T valid;
    private final T invalid;

    public RefinedCase(
        String typeName,
        String code,
        Function<T, Validation<Violation, ? extends Refined<T>>> of,
        Function<T, ? extends Refined<T>> unsafeOf,
        T valid,
        T invalid
    ) {
        this.typeName = typeName;
        this.code = code;
        this.of = of;
        this.unsafeOf = unsafeOf;
        this.valid = valid;
        this.invalid = invalid;
    }

    public String typeName() {
        return typeName;
    }

    public String code() {
        return code;
    }

    public Function<T, Validation<Violation, ? extends Refined<T>>> of() {
        return of;
    }

    public Function<T, ? extends Refined<T>> unsafeOf() {
        return unsafeOf;
    }

    public T valid() {
        return valid;
    }

    public T invalid() {
        return invalid;
    }
}
