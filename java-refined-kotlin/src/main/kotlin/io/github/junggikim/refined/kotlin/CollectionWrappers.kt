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
import java.util.AbstractMap
import java.util.ArrayList
import java.util.Comparator
import java.util.LinkedHashMap
import kotlin.jvm.JvmName

class KNonEmptyList<T> internal constructor(
    private val delegate: NonEmptyList<T>
) : List<T> by delegate {
    val head: T
        get() = delegate.head()

    val tail: List<T>
        get() = delegate.tail().toList()

    fun asJava(): NonEmptyList<T> = delegate
}

class KNonEmptySet<T> internal constructor(
    private val delegate: NonEmptySet<T>
) : Set<T> by delegate {
    fun asJava(): NonEmptySet<T> = delegate
}

class KNonEmptyMap<K, V> internal constructor(
    private val delegate: NonEmptyMap<K, V>
) : Map<K, V> by delegate {
    fun asJava(): NonEmptyMap<K, V> = delegate
}

class KNonEmptyIterable<T> internal constructor(
    private val delegate: NonEmptyIterable<T>
) : List<T> by delegate {
    val head: T
        get() = delegate.head()

    val tail: List<T>
        get() = delegate.tail().toList()

    fun asJava(): NonEmptyIterable<T> = delegate
}

class KNonEmptyQueue<T> internal constructor(
    private val delegate: NonEmptyQueue<T>
) : List<T> by delegate {
    fun peek(): T = delegate.peek()

    fun element(): T = delegate.element()

    @get:JvmName("lastValue")
    val last: T
        get() = delegate.last()

    fun asJava(): NonEmptyQueue<T> = delegate
}

class KNonEmptyDeque<T> internal constructor(
    private val delegate: NonEmptyDeque<T>
) : List<T> by delegate {
    @get:JvmName("firstValue")
    val first: T
        get() = delegate.first()

    @get:JvmName("lastValue")
    val last: T
        get() = delegate.last()

    fun peek(): T = delegate.peek()

    fun element(): T = delegate.element()

    fun descending(): List<T> {
        val iterator = delegate.descendingIterator()
        val values = ArrayList<T>()
        while (iterator.hasNext()) {
            values.add(iterator.next())
        }
        return values
    }

    fun asJava(): NonEmptyDeque<T> = delegate
}

class KNonEmptySortedSet<T> internal constructor(
    private val delegate: NonEmptySortedSet<T>
) : Set<T> by delegate {
    fun comparator(): Comparator<in T>? = delegate.comparator()

    val first: T
        get() = delegate.first()

    val last: T
        get() = delegate.last()

    fun headSet(toElement: T): Set<T> = delegate.headSet(toElement).toSet()

    fun tailSet(fromElement: T): Set<T> = delegate.tailSet(fromElement).toSet()

    fun subSet(fromElement: T, toElement: T): Set<T> = delegate.subSet(fromElement, toElement).toSet()

    fun asJava(): NonEmptySortedSet<T> = delegate
}

class KNonEmptySortedMap<K, V> internal constructor(
    private val delegate: NonEmptySortedMap<K, V>
) : Map<K, V> by delegate {
    fun comparator(): Comparator<in K>? = delegate.comparator()

    val firstKey: K
        get() = delegate.firstKey()

    val lastKey: K
        get() = delegate.lastKey()

    fun headMap(toKey: K): Map<K, V> = delegate.headMap(toKey).toMap(LinkedHashMap())

    fun tailMap(fromKey: K): Map<K, V> = delegate.tailMap(fromKey).toMap(LinkedHashMap())

    fun subMap(fromKey: K, toKey: K): Map<K, V> = delegate.subMap(fromKey, toKey).toMap(LinkedHashMap())

    fun asJava(): NonEmptySortedMap<K, V> = delegate
}

class KNonEmptyNavigableSet<T> internal constructor(
    private val delegate: NonEmptyNavigableSet<T>
) : Set<T> by delegate {
    fun comparator(): Comparator<in T>? = delegate.comparator()

    val first: T
        get() = delegate.first()

    val last: T
        get() = delegate.last()

    fun lower(value: T): T? = delegate.lower(value)

    fun floor(value: T): T? = delegate.floor(value)

    fun ceiling(value: T): T? = delegate.ceiling(value)

    fun higher(value: T): T? = delegate.higher(value)

    fun descendingSet(): Set<T> = delegate.descendingSet().toSet()

    fun headSet(toElement: T, inclusive: Boolean): Set<T> =
        delegate.headSet(toElement, inclusive).toSet()

    fun tailSet(fromElement: T, inclusive: Boolean): Set<T> =
        delegate.tailSet(fromElement, inclusive).toSet()

    fun subSet(fromElement: T, fromInclusive: Boolean, toElement: T, toInclusive: Boolean): Set<T> =
        delegate.subSet(fromElement, fromInclusive, toElement, toInclusive).toSet()

    fun asJava(): NonEmptyNavigableSet<T> = delegate
}

class KNonEmptyNavigableMap<K, V> internal constructor(
    private val delegate: NonEmptyNavigableMap<K, V>
) : Map<K, V> by delegate {
    fun comparator(): Comparator<in K>? = delegate.comparator()

    val firstKey: K
        get() = delegate.firstKey()

    val lastKey: K
        get() = delegate.lastKey()

    fun lowerEntry(key: K): Map.Entry<K, V>? {
        val entry = delegate.lowerEntry(key)
        return if (entry == null) null else immutableEntry(entry)
    }

    fun lowerKey(key: K): K? = delegate.lowerKey(key)

    fun floorEntry(key: K): Map.Entry<K, V>? {
        val entry = delegate.floorEntry(key)
        return if (entry == null) null else immutableEntry(entry)
    }

    fun floorKey(key: K): K? = delegate.floorKey(key)

    fun ceilingEntry(key: K): Map.Entry<K, V>? {
        val entry = delegate.ceilingEntry(key)
        return if (entry == null) null else immutableEntry(entry)
    }

    fun ceilingKey(key: K): K? = delegate.ceilingKey(key)

    fun higherEntry(key: K): Map.Entry<K, V>? {
        val entry = delegate.higherEntry(key)
        return if (entry == null) null else immutableEntry(entry)
    }

    fun higherKey(key: K): K? = delegate.higherKey(key)

    fun firstEntry(): Map.Entry<K, V> = immutableEntry(delegate.firstEntry())

    fun lastEntry(): Map.Entry<K, V> = immutableEntry(delegate.lastEntry())

    fun descendingMap(): Map<K, V> = delegate.descendingMap().toMap(LinkedHashMap())

    fun navigableKeySet(): Set<K> = delegate.navigableKeySet().toSet()

    fun descendingKeySet(): Set<K> = delegate.descendingKeySet().toSet()

    fun headMap(toKey: K, inclusive: Boolean): Map<K, V> =
        delegate.headMap(toKey, inclusive).toMap(LinkedHashMap())

    fun tailMap(fromKey: K, inclusive: Boolean): Map<K, V> =
        delegate.tailMap(fromKey, inclusive).toMap(LinkedHashMap())

    fun subMap(fromKey: K, fromInclusive: Boolean, toKey: K, toInclusive: Boolean): Map<K, V> =
        delegate.subMap(fromKey, fromInclusive, toKey, toInclusive).toMap(LinkedHashMap())

    fun asJava(): NonEmptyNavigableMap<K, V> = delegate
}

fun <T> NonEmptyList<T>.asKotlin(): KNonEmptyList<T> = KNonEmptyList(this)

fun <T> NonEmptySet<T>.asKotlin(): KNonEmptySet<T> = KNonEmptySet(this)

fun <K, V> NonEmptyMap<K, V>.asKotlin(): KNonEmptyMap<K, V> = KNonEmptyMap(this)

fun <T> NonEmptyIterable<T>.asKotlin(): KNonEmptyIterable<T> = KNonEmptyIterable(this)

fun <T> NonEmptyQueue<T>.asKotlin(): KNonEmptyQueue<T> = KNonEmptyQueue(this)

fun <T> NonEmptyDeque<T>.asKotlin(): KNonEmptyDeque<T> = KNonEmptyDeque(this)

fun <T> NonEmptySortedSet<T>.asKotlin(): KNonEmptySortedSet<T> = KNonEmptySortedSet(this)

fun <K, V> NonEmptySortedMap<K, V>.asKotlin(): KNonEmptySortedMap<K, V> = KNonEmptySortedMap(this)

fun <T> NonEmptyNavigableSet<T>.asKotlin(): KNonEmptyNavigableSet<T> = KNonEmptyNavigableSet(this)

fun <K, V> NonEmptyNavigableMap<K, V>.asKotlin(): KNonEmptyNavigableMap<K, V> = KNonEmptyNavigableMap(this)

private fun <K, V> immutableEntry(entry: Map.Entry<K, V>): Map.Entry<K, V> =
    AbstractMap.SimpleImmutableEntry(entry)
