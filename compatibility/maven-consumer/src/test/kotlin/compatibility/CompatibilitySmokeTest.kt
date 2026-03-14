package compatibility

import io.github.junggikim.refined.kotlin.toNonBlankStringOrThrow
import io.github.junggikim.refined.kotlin.toNonEmptyQueueOrThrow
import io.github.junggikim.refined.refined.numeric.PositiveInt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CompatibilitySmokeTest {

    @Test
    fun javaRefinedArtifactsWorkFromAMavenConsumer() {
        val name = "Ada".toNonBlankStringOrThrow()
        val queue = sequenceOf(1, 2, 3).toNonEmptyQueueOrThrow()
        val age = PositiveInt.of(18).get()

        assertEquals("Ada", name.value)
        assertEquals(3, queue.last)
        assertEquals(18, age.value())
        assertTrue(queue.contains(2))
    }
}
