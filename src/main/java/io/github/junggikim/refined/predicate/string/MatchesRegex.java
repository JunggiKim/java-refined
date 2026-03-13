package io.github.junggikim.refined.predicate.string;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * String predicate that tests whether a value matches a compiled regular expression.
 *
 * <p>The supplied pattern is compiled once via {@link java.util.regex.Pattern#compile(String)}
 * and reused for every validation call. Because the pattern is provided by the caller,
 * <strong>care must be taken to avoid patterns susceptible to catastrophic backtracking
 * (ReDoS)</strong>.</p>
 *
 * <p>Patterns with nested quantifiers such as {@code (a+)+} or {@code (a|a)*}
 * can cause exponential evaluation time on certain inputs.</p>
 *
 * @see java.util.regex.Pattern
 */
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
