package io.github.junggikim.refined.predicate.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Objects;
import java.util.regex.Pattern;

public final class MatchesRegex implements Constraint<String> {

    private final Constraint<String> delegate;

    public MatchesRegex(String pattern) {
        Pattern compiled = Pattern.compile(Objects.requireNonNull(pattern, "pattern"));
        this.delegate = RefinedSupport.require("matches-regex", "value must match pattern " + pattern, value -> compiled.matcher(value).matches());
    }

    @Override
    public Validation<Violation, String> validate(String value) {
        return delegate.validate(value);
    }
}
