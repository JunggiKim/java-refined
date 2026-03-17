package io.github.junggikim.refined.kotlin

import io.github.junggikim.refined.core.RefinementException
import io.github.junggikim.refined.validation.Validated
import io.github.junggikim.refined.validation.Validation
import io.github.junggikim.refined.violation.Violation

// getOrNull(), onValid(), onInvalid(), getOrElse(Function), recover(Function), mapError(Function)
// are now Java default methods on Validation<E, A>.
// Kotlin callers invoke the Java defaults directly via SAM conversion.
// Only Kotlin-specific extensions (errorOrNull, getOrThrow, getOrDefault, toResult) remain here.

/**
 * Returns the error if invalid, otherwise `null`.
 *
 * Mirrors `kotlin.Result.exceptionOrNull`.
 */
fun <E, A> Validation<E, A>.errorOrNull(): E? =
    if (isInvalid) error else null

/**
 * Returns the value if valid, otherwise throws [RefinementException].
 */
fun <A> Validation<Violation, A>.getOrThrow(): A =
    if (isValid) get() else throw RefinementException(error)

/**
 * Returns the value if valid, otherwise returns [default].
 *
 * Kotlin-idiomatic alias for Java `Validation.getOrElse(A)`.
 * Mirrors `kotlin.Result.getOrDefault`.
 */
fun <E, A> Validation<E, A>.getOrDefault(default: A): A =
    if (isValid) get() else default

/**
 * Converts this Validation to a `kotlin.Result`.
 *
 * Valid becomes `Result.success`, Invalid becomes `Result.failure` with [RefinementException].
 */
fun <A> Validation<Violation, A>.toResult(): Result<A> =
    if (isValid) Result.success(get())
    else Result.failure(RefinementException(error))

/**
 * Returns the value if valid, otherwise `null`.
 *
 * Mirrors `kotlin.Result.getOrNull`.
 */
fun <E, A> Validated<E, A>.valueOrNull(): A? =
    if (isValid) get() else null

/**
 * Returns the error list if invalid, otherwise `null`.
 */
fun <E, A> Validated<E, A>.errorsOrNull(): List<E>? =
    if (isInvalid) errors else null
