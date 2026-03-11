package io.github.junggikim.refined.core;

import io.github.junggikim.refined.violation.Violation;

/**
 * Exception thrown by {@code unsafeOf(...)} style constructors when refinement fails.
 */
public final class RefinementException extends IllegalArgumentException {
    private static final long serialVersionUID = 1L;

    private final Violation violation;

    /**
     * Creates an exception for a failed refinement.
     *
     * @param violation structured refinement failure
     */
    public RefinementException(Violation violation) {
        super(violation.message());
        this.violation = violation;
    }

    /**
     * Returns the underlying structured violation.
     *
     * @return refinement failure details
     */
    public Violation violation() {
        return violation;
    }
}
