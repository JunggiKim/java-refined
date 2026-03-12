package io.github.junggikim.refined.kotlin

import io.github.junggikim.refined.core.RefinementException
import io.github.junggikim.refined.validation.Validated
import io.github.junggikim.refined.validation.Validation
import io.github.junggikim.refined.violation.Violation

fun <E, A> Validation<E, A>.getOrNull(): A? =
    if (isValid) get() else null

fun <E, A> Validation<E, A>.errorOrNull(): E? =
    if (isInvalid) error else null

fun <A> Validation<Violation, A>.getOrThrow(): A =
    if (isValid) get() else throw RefinementException(error)

fun <E, A> Validation<E, A>.onValid(block: (A) -> Unit): Validation<E, A> {
    if (isValid) {
        block(get())
    }
    return this
}

fun <E, A> Validation<E, A>.onInvalid(block: (E) -> Unit): Validation<E, A> {
    if (isInvalid) {
        block(error)
    }
    return this
}

fun <E, A> Validated<E, A>.valueOrNull(): A? =
    if (isValid) get() else null

fun <E, A> Validated<E, A>.errorsOrNull(): List<E>? =
    if (isInvalid) errors else null
