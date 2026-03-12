package io.github.junggikim.refined.kotlin

import io.github.junggikim.refined.refined.collection.NonEmptyDeque
import io.github.junggikim.refined.refined.collection.NonEmptyIterable
import io.github.junggikim.refined.refined.collection.NonEmptyList
import io.github.junggikim.refined.refined.collection.NonEmptyMap
import io.github.junggikim.refined.refined.collection.NonEmptyNavigableMap
import io.github.junggikim.refined.refined.collection.NonEmptyNavigableSet
import io.github.junggikim.refined.refined.collection.NonEmptyQueue
import io.github.junggikim.refined.refined.collection.NonEmptySet
import io.github.junggikim.refined.refined.collection.NonEmptySortedMap
import io.github.junggikim.refined.refined.collection.NonEmptySortedSet
import java.util.ArrayDeque
import java.util.Comparator
import java.util.LinkedHashMap
import java.util.LinkedHashSet
import java.util.TreeMap
import java.util.TreeSet
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test

class CollectionKotlinSupportTest {

    @Suppress("DEPRECATION_ERROR")
    @Test
    fun suppressedSequencedListMutatorsStillThrowAtRuntime() {
        val list = listOf(1, 2).toNonEmptyListOrThrow()
        val iterable = listOf(3, 4).asIterable().toNonEmptyIterableOrThrow()
        val queue = ArrayDeque(listOf(5, 6)).toNonEmptyQueueOrThrow()
        val deque = ArrayDeque(listOf(7, 8)).toNonEmptyDequeOrThrow()

        assertFailsWith<UnsupportedOperationException> { list.addFirst(0) }
        assertFailsWith<UnsupportedOperationException> { list.addLast(3) }
        assertFailsWith<UnsupportedOperationException> { list.removeFirst() }
        assertFailsWith<UnsupportedOperationException> { list.removeLast() }

        assertFailsWith<UnsupportedOperationException> { iterable.addFirst(2) }
        assertFailsWith<UnsupportedOperationException> { iterable.addLast(5) }
        assertFailsWith<UnsupportedOperationException> { iterable.removeFirst() }
        assertFailsWith<UnsupportedOperationException> { iterable.removeLast() }

        assertFailsWith<UnsupportedOperationException> { queue.addFirst(4) }
        assertFailsWith<UnsupportedOperationException> { queue.addLast(7) }
        assertFailsWith<UnsupportedOperationException> { queue.removeFirst() }
        assertFailsWith<UnsupportedOperationException> { queue.removeLast() }

        assertFailsWith<UnsupportedOperationException> { deque.addFirst(6) }
        assertFailsWith<UnsupportedOperationException> { deque.addLast(9) }
        assertFailsWith<UnsupportedOperationException> { deque.removeFirst() }
        assertFailsWith<UnsupportedOperationException> { deque.removeLast() }
    }

    @Test
    fun validationExtensionsAndBeanPropertyBridgesWork() {
        val valid = "Ada".toNonBlankString()
        val invalid = (null as String?).toNonBlankString()

        assertEquals("Ada", valid.getOrNull()!!.value)
        assertNull(invalid.getOrNull())
        assertNull(valid.errorOrNull())
        assertEquals("non-blank-string", invalid.errorOrNull()!!.code)
        assertEquals("value must not be blank", invalid.errorOrNull()!!.message)
        assertTrue(invalid.errorOrNull()!!.metadata.isEmpty())
        assertEquals("Ada", valid.onValid { assertEquals("Ada", it.value) }.getOrThrow().value)
        assertTrue(valid.onInvalid { error("valid value should not trigger onInvalid") }.isValid)
        assertTrue(invalid.onValid { error("invalid value should not trigger onValid") }.isInvalid)
        assertTrue(invalid.onInvalid { assertEquals("non-blank-string", it.code) }.isInvalid)
        assertEquals("Ada", "Ada".toNonBlankStringOrThrow().value)
        assertFailsWith<io.github.junggikim.refined.core.RefinementException> { invalid.getOrThrow() }
        assertEquals(
            "age",
            io.github.junggikim.refined.validation.Validated.invalid<String, Int>(listOf("age")).errorsOrNull()!!.single()
        )
        assertEquals(1, io.github.junggikim.refined.validation.Validated.valid<String, Int>(1).valueOrNull())
        assertNull(io.github.junggikim.refined.validation.Validated.valid<String, Int>(1).errorsOrNull())
        assertNull(io.github.junggikim.refined.validation.Validated.invalid<String, Int>(listOf("age")).valueOrNull())
    }

    @Test
    fun directCollectionFactoriesAndAdaptersWork() {
        val list = listOf(1, 2, 3).toNonEmptyListOrThrow()
        val set = linkedSetOf(1, 2).toNonEmptySetOrThrow()
        val map = linkedMapOf("a" to 1, "b" to 2).toNonEmptyMapOrThrow()
        val iterable = listOf(4, 5, 6).asIterable().toNonEmptyIterableOrThrow()
        val queueSource = ArrayDeque(listOf(7, 8, 9))
        val queue = queueSource.toNonEmptyQueueOrThrow()
        val dequeSource = ArrayDeque(listOf(10, 11, 12))
        val deque = dequeSource.toNonEmptyDequeOrThrow()
        val sortedSetSource = TreeSet(listOf(1, 2, 3))
        val sortedSet = sortedSetSource.toNonEmptySortedSetOrThrow()
        val sortedMapSource = TreeMap<String, Int>().apply {
            put("a", 1)
            put("b", 2)
        }
        val sortedMap = sortedMapSource.toNonEmptySortedMapOrThrow()
        val navigableSetSource = TreeSet(listOf(1, 2, 3))
        val navigableSet = navigableSetSource.toNonEmptyNavigableSetOrThrow()
        val navigableMapSource = TreeMap<String, Int>().apply {
            put("a", 1)
            put("b", 2)
            put("c", 3)
        }
        val navigableMap = navigableMapSource.toNonEmptyNavigableMapOrThrow()

        val listView: List<Int> = list
        val setView: Set<Int> = set
        val mapView: Map<String, Int> = map
        assertEquals(listOf(1, 2, 3), listView)
        assertEquals(linkedSetOf(1, 2), setView)
        assertEquals(linkedMapOf("a" to 1, "b" to 2), mapView)

        assertEquals(1, list.head)
        assertEquals(listOf(2, 3), list.tail)
        assertEquals(NonEmptyList.unsafeOf(listOf(1, 2, 3)), list.asJava())

        assertEquals(NonEmptySet.unsafeOf(LinkedHashSet(listOf(1, 2))), set.asJava())
        assertEquals(NonEmptyMap.unsafeOf(LinkedHashMap<String, Int>().apply {
            put("a", 1)
            put("b", 2)
        }), map.asJava())

        assertEquals(4, iterable.head)
        assertEquals(listOf(5, 6), iterable.tail)
        assertEquals(NonEmptyIterable.unsafeOf(listOf(4, 5, 6)), iterable.asJava())

        assertEquals(7, queue.peek())
        assertEquals(7, queue.element())
        assertEquals(9, queue.last)
        assertEquals(NonEmptyQueue.unsafeOf(ArrayDeque(listOf(7, 8, 9))), queue.asJava())

        assertEquals(10, deque.first)
        assertEquals(12, deque.last)
        assertEquals(10, deque.peek())
        assertEquals(10, deque.element())
        assertEquals(listOf(12, 11, 10), deque.descending())
        assertEquals(NonEmptyDeque.unsafeOf(ArrayDeque(listOf(10, 11, 12))), deque.asJava())

        assertNull(sortedSet.comparator())
        assertEquals(1, sortedSet.first)
        assertEquals(3, sortedSet.last)
        assertEquals(setOf(1), sortedSet.headSet(2))
        assertEquals(setOf(2, 3), sortedSet.tailSet(2))
        assertEquals(setOf(1, 2), sortedSet.subSet(1, 3))
        assertEquals(NonEmptySortedSet.unsafeOf(sortedSetSource), sortedSet.asJava())

        assertNull(sortedMap.comparator())
        assertEquals("a", sortedMap.firstKey)
        assertEquals("b", sortedMap.lastKey)
        assertEquals(mapOf("a" to 1), sortedMap.headMap("b"))
        assertEquals(mapOf("b" to 2), sortedMap.tailMap("b"))
        assertEquals(mapOf("a" to 1), sortedMap.subMap("a", "b"))
        assertEquals(NonEmptySortedMap.unsafeOf(sortedMapSource), sortedMap.asJava())

        assertNull(navigableSet.comparator())
        assertEquals(1, navigableSet.first)
        assertEquals(3, navigableSet.last)
        assertNull(navigableSet.lower(1))
        assertEquals(2, navigableSet.floor(2))
        assertEquals(2, navigableSet.ceiling(2))
        assertEquals(3, navigableSet.higher(2))
        assertEquals(setOf(3, 2, 1), navigableSet.descendingSet())
        assertEquals(setOf(1, 2), navigableSet.headSet(2, true))
        assertEquals(setOf(2, 3), navigableSet.tailSet(2, true))
        assertEquals(setOf(1, 2), navigableSet.subSet(1, true, 2, true))
        assertEquals(NonEmptyNavigableSet.unsafeOf(navigableSetSource), navigableSet.asJava())

        assertNull(navigableMap.comparator())
        assertEquals("a", navigableMap.firstKey)
        assertEquals("c", navigableMap.lastKey)
        assertNull(navigableMap.lowerEntry("a"))
        assertNull(navigableMap.lowerKey("a"))
        assertEquals("b", navigableMap.floorEntry("b")!!.key)
        assertEquals("b", navigableMap.floorKey("b"))
        assertEquals("b", navigableMap.ceilingEntry("b")!!.key)
        assertEquals("b", navigableMap.ceilingKey("b"))
        assertEquals("c", navigableMap.higherEntry("b")!!.key)
        assertEquals("c", navigableMap.higherKey("b"))
        assertEquals("a", navigableMap.lowerEntry("b")!!.key)
        assertNull(navigableMap.floorEntry("0"))
        assertNull(navigableMap.ceilingEntry("z"))
        assertNull(navigableMap.higherEntry("c"))
        assertEquals("a", navigableMap.firstEntry().key)
        assertEquals("c", navigableMap.lastEntry().key)
        assertEquals(mapOf("c" to 3, "b" to 2, "a" to 1), navigableMap.descendingMap())
        assertEquals(setOf("a", "b", "c"), navigableMap.navigableKeySet())
        assertEquals(setOf("c", "b", "a"), navigableMap.descendingKeySet())
        assertEquals(mapOf("a" to 1, "b" to 2), navigableMap.headMap("b", true))
        assertEquals(mapOf("b" to 2, "c" to 3), navigableMap.tailMap("b", true))
        assertEquals(mapOf("a" to 1, "b" to 2), navigableMap.subMap("a", true, "b", true))
        assertEquals(NonEmptyNavigableMap.unsafeOf(navigableMapSource), navigableMap.asJava())
    }

    @Test
    fun safeDirectCollectionFactoriesCoverAllWrappers() {
        assertEquals(listOf(1, 2), listOf(1, 2).toNonEmptyList().getOrThrow().toList())
        assertEquals(linkedSetOf(1, 2), linkedSetOf(1, 2).toNonEmptySet().getOrThrow().toSet())
        assertEquals(mapOf("a" to 1), linkedMapOf("a" to 1).toNonEmptyMap().getOrThrow().entries.associate { it.toPair() })
        assertEquals("non-empty-map-empty", emptyMap<String, Int>().toNonEmptyMap().errorOrNull()!!.code)
        assertEquals(listOf(1, 2), listOf(1, 2).asIterable().toNonEmptyIterable().getOrThrow().toList())
        assertEquals(listOf(1, 2), ArrayDeque(listOf(1, 2)).toNonEmptyQueue().getOrThrow().toList())
        assertEquals(listOf(1, 2), ArrayDeque(listOf(1, 2)).toNonEmptyDeque().getOrThrow().toList())
        assertEquals(setOf(1, 2), TreeSet(listOf(1, 2)).toNonEmptySortedSet().getOrThrow().toSet())
        assertEquals(
            mapOf("a" to 1),
            TreeMap<String, Int>().apply { put("a", 1) }.toNonEmptySortedMap().getOrThrow().entries.associate { it.toPair() }
        )
        assertEquals(setOf(1, 2), TreeSet(listOf(1, 2)).toNonEmptyNavigableSet().getOrThrow().toSet())
        assertEquals(
            mapOf("a" to 1),
            TreeMap<String, Int>().apply { put("a", 1) }.toNonEmptyNavigableMap().getOrThrow().entries.associate { it.toPair() }
        )
    }

    @Test
    fun sequenceFactoriesCoverBothBranches() {
        val nullSeq: Sequence<Int>? = null
        val nullPairs: Sequence<Pair<String, Int>>? = null
        val reverse = Comparator.reverseOrder<Int>()
        val reverseKeys = Comparator.reverseOrder<String>()

        assertEquals("non-empty-list-empty", nullSeq.toNonEmptyList().errorOrNull()!!.code)
        assertFailsWith<io.github.junggikim.refined.core.RefinementException> { nullSeq.toNonEmptyListOrThrow() }
        assertEquals(listOf(1, 2, 3), sequenceOf(1, 2, 3).toNonEmptyList().getOrThrow())
        assertEquals(listOf(1, 2, 3), sequenceOf(1, 2, 3).toNonEmptyListOrThrow())

        assertEquals("non-empty-set-empty", nullSeq.toNonEmptySet().errorOrNull()!!.code)
        assertFailsWith<io.github.junggikim.refined.core.RefinementException> { nullSeq.toNonEmptySetOrThrow() }
        assertEquals(linkedSetOf(1, 2, 3), sequenceOf(1, 2, 3).toNonEmptySet().getOrThrow().toSet())
        assertEquals(linkedSetOf(1, 2, 3), sequenceOf(1, 2, 3).toNonEmptySetOrThrow().toSet())

        assertEquals("non-empty-iterable-empty", nullSeq.toNonEmptyIterable().errorOrNull()!!.code)
        assertFailsWith<io.github.junggikim.refined.core.RefinementException> { nullSeq.toNonEmptyIterableOrThrow() }
        assertEquals(listOf(1, 2, 3), sequenceOf(1, 2, 3).toNonEmptyIterable().getOrThrow())
        assertEquals(listOf(1, 2, 3), sequenceOf(1, 2, 3).toNonEmptyIterableOrThrow())

        assertEquals("non-empty-queue-empty", nullSeq.toNonEmptyQueue().errorOrNull()!!.code)
        assertFailsWith<io.github.junggikim.refined.core.RefinementException> { nullSeq.toNonEmptyQueueOrThrow() }
        assertEquals(listOf(1, 2, 3), sequenceOf(1, 2, 3).toNonEmptyQueue().getOrThrow())
        assertEquals(listOf(1, 2, 3), sequenceOf(1, 2, 3).toNonEmptyQueueOrThrow())

        assertEquals("non-empty-deque-empty", nullSeq.toNonEmptyDeque().errorOrNull()!!.code)
        assertFailsWith<io.github.junggikim.refined.core.RefinementException> { nullSeq.toNonEmptyDequeOrThrow() }
        assertEquals(listOf(1, 2, 3), sequenceOf(1, 2, 3).toNonEmptyDeque().getOrThrow())
        assertEquals(listOf(1, 2, 3), sequenceOf(1, 2, 3).toNonEmptyDequeOrThrow())

        assertEquals("non-empty-sorted-set-empty", nullSeq.toNonEmptySortedSet<Int>().errorOrNull()!!.code)
        assertEquals("non-empty-sorted-set-empty", nullSeq.toNonEmptySortedSet(reverse).errorOrNull()!!.code)
        assertFailsWith<io.github.junggikim.refined.core.RefinementException> { nullSeq.toNonEmptySortedSetOrThrow<Int>() }
        assertEquals(setOf(1, 2, 3), sequenceOf(3, 1, 2).toNonEmptySortedSet<Int>().getOrThrow().toSet())
        assertEquals(setOf(1, 2, 3), sequenceOf(3, 1, 2).toNonEmptySortedSetOrThrow<Int>().toSet())
        assertEquals(listOf(3, 2, 1), sequenceOf(1, 2, 3).toNonEmptySortedSet(reverse).getOrThrow().toList())
        assertEquals(listOf(3, 2, 1), sequenceOf(1, 2, 3).toNonEmptySortedSetOrThrow(reverse).toList())

        assertEquals("non-empty-sorted-map-empty", nullPairs.toNonEmptySortedMap<String, Int>().errorOrNull()!!.code)
        assertEquals("non-empty-sorted-map-empty", nullPairs.toNonEmptySortedMap(reverseKeys).errorOrNull()!!.code)
        assertFailsWith<io.github.junggikim.refined.core.RefinementException> { nullPairs.toNonEmptySortedMapOrThrow<String, Int>() }
        assertEquals(
            mapOf("a" to 1, "b" to 2),
            sequenceOf("b" to 2, "a" to 1).toNonEmptySortedMap<String, Int>().getOrThrow()
        )
        assertEquals(mapOf("a" to 1, "b" to 2), sequenceOf("b" to 2, "a" to 1).toNonEmptySortedMapOrThrow<String, Int>())
        assertEquals(listOf("b", "a"), sequenceOf("a" to 1, "b" to 2).toNonEmptySortedMap(reverseKeys).getOrThrow().keys.toList())
        assertEquals(listOf("b", "a"), sequenceOf("a" to 1, "b" to 2).toNonEmptySortedMapOrThrow(reverseKeys).keys.toList())

        assertEquals("non-empty-navigable-set-empty", nullSeq.toNonEmptyNavigableSet<Int>().errorOrNull()!!.code)
        assertEquals("non-empty-navigable-set-empty", nullSeq.toNonEmptyNavigableSet(reverse).errorOrNull()!!.code)
        assertFailsWith<io.github.junggikim.refined.core.RefinementException> { nullSeq.toNonEmptyNavigableSetOrThrow<Int>() }
        assertEquals(setOf(1, 2, 3), sequenceOf(3, 1, 2).toNonEmptyNavigableSet<Int>().getOrThrow().toSet())
        assertEquals(setOf(1, 2, 3), sequenceOf(3, 1, 2).toNonEmptyNavigableSetOrThrow<Int>().toSet())
        assertEquals(listOf(3, 2, 1), sequenceOf(1, 2, 3).toNonEmptyNavigableSet(reverse).getOrThrow().toList())
        assertEquals(listOf(3, 2, 1), sequenceOf(1, 2, 3).toNonEmptyNavigableSetOrThrow(reverse).toList())

        assertEquals("non-empty-navigable-map-empty", nullPairs.toNonEmptyNavigableMap<String, Int>().errorOrNull()!!.code)
        assertEquals("non-empty-navigable-map-empty", nullPairs.toNonEmptyNavigableMap(reverseKeys).errorOrNull()!!.code)
        assertFailsWith<io.github.junggikim.refined.core.RefinementException> { nullPairs.toNonEmptyNavigableMapOrThrow<String, Int>() }
        assertEquals(
            mapOf("a" to 1, "b" to 2),
            sequenceOf("b" to 2, "a" to 1).toNonEmptyNavigableMap<String, Int>().getOrThrow()
        )
        assertEquals(mapOf("a" to 1, "b" to 2), sequenceOf("b" to 2, "a" to 1).toNonEmptyNavigableMapOrThrow<String, Int>())
        assertEquals(listOf("b", "a"), sequenceOf("a" to 1, "b" to 2).toNonEmptyNavigableMap(reverseKeys).getOrThrow().keys.toList())
        assertEquals(listOf("b", "a"), sequenceOf("a" to 1, "b" to 2).toNonEmptyNavigableMapOrThrow(reverseKeys).keys.toList())
    }

    @Test
    fun javaAdaptersAreExposedForEveryCollectionWrapper() {
        val javaList = NonEmptyList.unsafeOf(listOf(1, 2)).asKotlin().asJava()
        assertTrue(javaList == listOf(1, 2))
        val javaSet: NonEmptySet<Int> = NonEmptySet.unsafeOf(LinkedHashSet(listOf(1, 2)))
        val javaSetRoundTrip: NonEmptySet<Int> = javaSet.asKotlin().asJava()
        assertTrue(javaSetRoundTrip == linkedSetOf(1, 2))

        val javaMap = NonEmptyMap.unsafeOf(linkedMapOf("a" to 1)).asKotlin().asJava()
        assertTrue(javaMap == mapOf("a" to 1))

        val javaIterable = NonEmptyIterable.unsafeOf(listOf(1, 2)).asKotlin().asJava()
        assertTrue(javaIterable == listOf(1, 2))

        val javaQueue = NonEmptyQueue.unsafeOf(ArrayDeque(listOf(1, 2))).asKotlin().asJava()
        assertTrue(javaQueue == listOf(1, 2))

        val javaDeque = NonEmptyDeque.unsafeOf(ArrayDeque(listOf(1, 2))).asKotlin().asJava()
        assertTrue(javaDeque == listOf(1, 2))

        val javaSortedSet = NonEmptySortedSet.unsafeOf(TreeSet(listOf(1, 2))).asKotlin().asJava()
        assertTrue(javaSortedSet == setOf(1, 2))

        val javaSortedMap = NonEmptySortedMap.unsafeOf(TreeMap<String, Int>().apply { put("a", 1) }).asKotlin().asJava()
        assertTrue(javaSortedMap == mapOf("a" to 1))

        val javaNavigableSet = NonEmptyNavigableSet.unsafeOf(TreeSet(listOf(1, 2))).asKotlin().asJava()
        assertTrue(javaNavigableSet == setOf(1, 2))

        val javaNavigableMap = NonEmptyNavigableMap.unsafeOf(TreeMap<String, Int>().apply { put("a", 1) }).asKotlin().asJava()
        assertTrue(javaNavigableMap == mapOf("a" to 1))
    }
}
