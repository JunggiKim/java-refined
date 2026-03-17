package io.github.junggikim.refined.kotlin

import io.github.junggikim.refined.core.RefinementException
import io.github.junggikim.refined.validation.Validation
import io.github.junggikim.refined.violation.Violation
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ValidationExtensionsTest {

    private val violation: Violation = Violation.of("test-error", "test error message")
    private val valid: Validation<Violation, String> = Validation.valid("hello")
    private val invalid: Validation<Violation, String> = Validation.invalid(violation)

    // -- errorOrNull (Kotlin extension) --

    @Test
    fun errorOrNullReturnsNullWhenValid() {
        assertNull(valid.errorOrNull())
    }

    @Test
    fun errorOrNullReturnsErrorWhenInvalid() {
        assertEquals("test-error", invalid.errorOrNull()!!.code)
    }

    // -- getOrThrow (Kotlin extension) --

    @Test
    fun getOrThrowReturnsValueWhenValid() {
        assertEquals("hello", valid.getOrThrow())
    }

    @Test
    fun getOrThrowThrowsWhenInvalid() {
        val e = assertThrows<RefinementException> { invalid.getOrThrow() }
        assertEquals("test-error", e.violation().code)
    }

    // -- getOrDefault (Kotlin extension) --

    @Test
    fun getOrDefaultReturnsValueWhenValid() {
        assertEquals("hello", valid.getOrDefault("fallback"))
    }

    @Test
    fun getOrDefaultReturnsDefaultWhenInvalid() {
        assertEquals("fallback", invalid.getOrDefault("fallback"))
    }

    // -- toResult (Kotlin extension) --

    @Test
    fun toResultReturnsSuccessWhenValid() {
        val result = valid.toResult()
        assertTrue(result.isSuccess)
        assertEquals("hello", result.getOrNull())
    }

    @Test
    fun toResultReturnsFailureWhenInvalid() {
        val result = invalid.toResult()
        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertIs<RefinementException>(exception)
        assertEquals("test-error", exception.violation().code)
    }

    // -- Java default methods accessible from Kotlin (smoke tests) --

    @Test
    fun javaGetOrElseValueOverloadWorksFromKotlin() {
        assertEquals("hello", valid.getOrElse("fallback"))
        assertEquals("fallback", invalid.getOrElse("fallback"))
    }

    @Test
    fun javaGetOrElseLambdaOverloadWorksFromKotlin() {
        assertEquals("hello", valid.getOrElse { "fallback" })
        assertEquals("fallback", invalid.getOrElse { "fallback" })
    }

    @Test
    fun javaRecoverWorksFromKotlin() {
        assertTrue(valid.recover { "recovered" }.isValid)
        val recovered = invalid.recover { "recovered" }
        assertTrue(recovered.isValid)
        assertEquals("recovered", recovered.get())
    }

    @Test
    fun javaMapErrorWorksFromKotlin() {
        val passThrough: Validation<String, String> = valid.mapError { it.code }
        assertTrue(passThrough.isValid)
        assertEquals("hello", passThrough.get())

        val mapped: Validation<String, String> = invalid.mapError { it.code }
        assertTrue(mapped.isInvalid)
        assertEquals("test-error", mapped.error)
    }

    @Test
    fun javaOnValidOnInvalidWorksFromKotlin() {
        var validSeen = false
        var invalidSeen = false

        valid.onValid { validSeen = true }.onInvalid { invalidSeen = true }
        assertTrue(validSeen)
        assertTrue(!invalidSeen)

        validSeen = false
        invalid.onValid { validSeen = true }.onInvalid { invalidSeen = true }
        assertTrue(!validSeen)
        assertTrue(invalidSeen)
    }

    @Test
    fun javaGetOrNullWorksFromKotlin() {
        assertEquals("hello", valid.getOrNull())
        assertNull(invalid.getOrNull())
    }

    @Test
    fun javaToOptionalWorksFromKotlin() {
        assertTrue(valid.toOptional().isPresent)
        assertFalse(invalid.toOptional().isPresent)
    }
}
