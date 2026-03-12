package io.github.junggikim.refined.kotlin

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test

class KotlinCompileSurfaceTest {

    @Test
    fun readOnlyWrappersAndBeanPropertiesCompile() {
        val result = compile(
            """
            import io.github.junggikim.refined.kotlin.*

            fun demo() {
                val list: List<Int> = listOf(1, 2).toNonEmptyListOrThrow()
                val map: Map<String, Int> = mapOf("a" to 1).toNonEmptyMapOrThrow()
                val value: String = "Ada".toNonBlankStringOrThrow().value
                val code: String = (null as String?).toNonBlankString().errorOrNull()!!.code

                check(list.contains(1))
                check(map["a"] == 1)
                check(value == "Ada")
                check(code == "non-blank-string")
            }
            """.trimIndent()
        )

        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode, result.messages)
    }

    @Test
    fun listMutatorDoesNotCompile() {
        assertCompilationError(
            """
            import io.github.junggikim.refined.kotlin.*

            fun demo() {
                val list = listOf(1, 2).toNonEmptyListOrThrow()
                list.add(3)
            }
            """.trimIndent()
        )
    }

    @Test
    fun mapMutatorDoesNotCompile() {
        assertCompilationError(
            """
            import io.github.junggikim.refined.kotlin.*

            fun demo() {
                val map = mapOf("a" to 1).toNonEmptyMapOrThrow()
                map.put("b", 2)
            }
            """.trimIndent()
        )
    }

    @Test
    fun queueMutatorDoesNotCompile() {
        assertCompilationError(
            """
            import io.github.junggikim.refined.kotlin.*
            import java.util.ArrayDeque

            fun demo() {
                val queue = ArrayDeque(listOf(1, 2)).toNonEmptyQueueOrThrow()
                queue.offer(3)
            }
            """.trimIndent()
        )
    }

    @Test
    fun dequeMutatorDoesNotCompile() {
        assertCompilationError(
            """
            import io.github.junggikim.refined.kotlin.*
            import java.util.ArrayDeque

            fun demo() {
                val deque = ArrayDeque(listOf(1, 2)).toNonEmptyDequeOrThrow()
                deque.addFirst(0)
            }
            """.trimIndent()
        )
    }

    private fun assertCompilationError(source: String) {
        val result = compile(source)
        assertEquals(KotlinCompilation.ExitCode.COMPILATION_ERROR, result.exitCode, result.messages)
        assertTrue(
            result.messages.contains("Unresolved reference") || result.messages.contains("Read-only view"),
            result.messages
        )
    }

    private fun compile(source: String): KotlinCompilation.Result =
        KotlinCompilation().apply {
            inheritClassPath = true
            sources = listOf(SourceFile.kotlin("Snippet.kt", source))
        }.compile()
}
