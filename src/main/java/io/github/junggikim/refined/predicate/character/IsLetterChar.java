package io.github.junggikim.refined.predicate.character;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;

public final class IsLetterChar implements Constraint<Character> {

    private final Constraint<Character> delegate = RefinedSupport.letterChar();

    @Override
    public Validation<Violation, Character> validate(Character value) {
        return delegate.validate(value);
    }
}
