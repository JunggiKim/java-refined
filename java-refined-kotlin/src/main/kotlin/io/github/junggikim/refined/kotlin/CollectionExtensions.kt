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
import io.github.junggikim.refined.validation.Validation
import io.github.junggikim.refined.violation.Violation
import java.util.ArrayDeque
import java.util.Comparator
import java.util.Deque
import java.util.LinkedHashMap
import java.util.LinkedHashSet
import java.util.NavigableMap
import java.util.NavigableSet
import java.util.Queue
import java.util.SortedMap
import java.util.SortedSet
import java.util.TreeMap
import java.util.TreeSet

fun <T> List<T>?.toNonEmptyList(): Validation<Violation, KNonEmptyList<T>> =
    toKNonEmptyList(NonEmptyList.of(this))

fun <T> List<T>?.toNonEmptyListOrThrow(): KNonEmptyList<T> =
    NonEmptyList.unsafeOf(this).asKotlin()

fun <T> Set<T>?.toNonEmptySet(): Validation<Violation, KNonEmptySet<T>> =
    toKNonEmptySet(NonEmptySet.of(this))

fun <T> Set<T>?.toNonEmptySetOrThrow(): KNonEmptySet<T> =
    NonEmptySet.unsafeOf(this).asKotlin()

fun <K, V> Map<K, V>?.toNonEmptyMap(): Validation<Violation, KNonEmptyMap<K, V>> =
    toKNonEmptyMap(NonEmptyMap.of(this))

fun <K, V> Map<K, V>?.toNonEmptyMapOrThrow(): KNonEmptyMap<K, V> =
    NonEmptyMap.unsafeOf(this).asKotlin()

fun <T> Iterable<T>?.toNonEmptyIterable(): Validation<Violation, KNonEmptyIterable<T>> =
    toKNonEmptyIterable(NonEmptyIterable.of(this))

fun <T> Iterable<T>?.toNonEmptyIterableOrThrow(): KNonEmptyIterable<T> =
    NonEmptyIterable.unsafeOf(this).asKotlin()

fun <T> Queue<T>?.toNonEmptyQueue(): Validation<Violation, KNonEmptyQueue<T>> =
    toKNonEmptyQueue(NonEmptyQueue.of(this))

fun <T> Queue<T>?.toNonEmptyQueueOrThrow(): KNonEmptyQueue<T> =
    NonEmptyQueue.unsafeOf(this).asKotlin()

fun <T> Deque<T>?.toNonEmptyDeque(): Validation<Violation, KNonEmptyDeque<T>> =
    toKNonEmptyDeque(NonEmptyDeque.of(this))

fun <T> Deque<T>?.toNonEmptyDequeOrThrow(): KNonEmptyDeque<T> =
    NonEmptyDeque.unsafeOf(this).asKotlin()

fun <T> SortedSet<T>?.toNonEmptySortedSet(): Validation<Violation, KNonEmptySortedSet<T>> =
    toKNonEmptySortedSet(NonEmptySortedSet.of(this))

fun <T> SortedSet<T>?.toNonEmptySortedSetOrThrow(): KNonEmptySortedSet<T> =
    NonEmptySortedSet.unsafeOf(this).asKotlin()

fun <K, V> SortedMap<K, V>?.toNonEmptySortedMap(): Validation<Violation, KNonEmptySortedMap<K, V>> =
    toKNonEmptySortedMap(NonEmptySortedMap.of(this))

fun <K, V> SortedMap<K, V>?.toNonEmptySortedMapOrThrow(): KNonEmptySortedMap<K, V> =
    NonEmptySortedMap.unsafeOf(this).asKotlin()

fun <T> NavigableSet<T>?.toNonEmptyNavigableSet(): Validation<Violation, KNonEmptyNavigableSet<T>> =
    toKNonEmptyNavigableSet(NonEmptyNavigableSet.of(this))

fun <T> NavigableSet<T>?.toNonEmptyNavigableSetOrThrow(): KNonEmptyNavigableSet<T> =
    NonEmptyNavigableSet.unsafeOf(this).asKotlin()

fun <K, V> NavigableMap<K, V>?.toNonEmptyNavigableMap(): Validation<Violation, KNonEmptyNavigableMap<K, V>> =
    toKNonEmptyNavigableMap(NonEmptyNavigableMap.of(this))

fun <K, V> NavigableMap<K, V>?.toNonEmptyNavigableMapOrThrow(): KNonEmptyNavigableMap<K, V> =
    NonEmptyNavigableMap.unsafeOf(this).asKotlin()

fun <T> Sequence<T>?.toNonEmptyList(): Validation<Violation, KNonEmptyList<T>> =
    if (this == null) toKNonEmptyList(NonEmptyList.of(null as List<T>?))
    else toKNonEmptyList(NonEmptyList.of(toList()))

fun <T> Sequence<T>?.toNonEmptyListOrThrow(): KNonEmptyList<T> =
    toNonEmptyList().getOrThrow()

fun <T> Sequence<T>?.toNonEmptySet(): Validation<Violation, KNonEmptySet<T>> =
    if (this == null) toKNonEmptySet(NonEmptySet.of(null as Set<T>?))
    else toKNonEmptySet(NonEmptySet.of(toCollection(LinkedHashSet<T>())))

fun <T> Sequence<T>?.toNonEmptySetOrThrow(): KNonEmptySet<T> =
    toNonEmptySet().getOrThrow()

fun <T> Sequence<T>?.toNonEmptyIterable(): Validation<Violation, KNonEmptyIterable<T>> =
    if (this == null) toKNonEmptyIterable(NonEmptyIterable.of(null as Iterable<T>?))
    else toKNonEmptyIterable(NonEmptyIterable.of(asIterable()))

fun <T> Sequence<T>?.toNonEmptyIterableOrThrow(): KNonEmptyIterable<T> =
    toNonEmptyIterable().getOrThrow()

fun <T> Sequence<T>?.toNonEmptyQueue(): Validation<Violation, KNonEmptyQueue<T>> =
    if (this == null) toKNonEmptyQueue(NonEmptyQueue.of(null as Queue<T>?))
    else toKNonEmptyQueue(NonEmptyQueue.of(toCollection(ArrayDeque<T>())))

fun <T> Sequence<T>?.toNonEmptyQueueOrThrow(): KNonEmptyQueue<T> =
    toNonEmptyQueue().getOrThrow()

fun <T> Sequence<T>?.toNonEmptyDeque(): Validation<Violation, KNonEmptyDeque<T>> =
    if (this == null) toKNonEmptyDeque(NonEmptyDeque.of(null as Deque<T>?))
    else toKNonEmptyDeque(NonEmptyDeque.of(toCollection(ArrayDeque<T>())))

fun <T> Sequence<T>?.toNonEmptyDequeOrThrow(): KNonEmptyDeque<T> =
    toNonEmptyDeque().getOrThrow()

fun <T : Comparable<T>> Sequence<T>?.toNonEmptySortedSet(): Validation<Violation, KNonEmptySortedSet<T>> =
    if (this == null) toKNonEmptySortedSet(NonEmptySortedSet.of(null as SortedSet<T>?))
    else toKNonEmptySortedSet(NonEmptySortedSet.of(toCollection(TreeSet<T>())))

fun <T> Sequence<T>?.toNonEmptySortedSet(
    comparator: Comparator<in T>
): Validation<Violation, KNonEmptySortedSet<T>> =
    if (this == null) toKNonEmptySortedSet(NonEmptySortedSet.of(null as SortedSet<T>?))
    else toKNonEmptySortedSet(NonEmptySortedSet.of(toCollection(TreeSet<T>(comparator))))

fun <T : Comparable<T>> Sequence<T>?.toNonEmptySortedSetOrThrow(): KNonEmptySortedSet<T> =
    toNonEmptySortedSet().getOrThrow()

fun <T> Sequence<T>?.toNonEmptySortedSetOrThrow(
    comparator: Comparator<in T>
): KNonEmptySortedSet<T> =
    toNonEmptySortedSet(comparator).getOrThrow()

fun <K : Comparable<K>, V> Sequence<Pair<K, V>>?.toNonEmptySortedMap(): Validation<Violation, KNonEmptySortedMap<K, V>> =
    if (this == null) toKNonEmptySortedMap(NonEmptySortedMap.of(null as SortedMap<K, V>?))
    else toKNonEmptySortedMap(NonEmptySortedMap.of(toTreeMap()))

fun <K, V> Sequence<Pair<K, V>>?.toNonEmptySortedMap(
    comparator: Comparator<in K>
): Validation<Violation, KNonEmptySortedMap<K, V>> =
    if (this == null) toKNonEmptySortedMap(NonEmptySortedMap.of(null as SortedMap<K, V>?))
    else toKNonEmptySortedMap(NonEmptySortedMap.of(toTreeMap(comparator)))

fun <K : Comparable<K>, V> Sequence<Pair<K, V>>?.toNonEmptySortedMapOrThrow(): KNonEmptySortedMap<K, V> =
    toNonEmptySortedMap<K, V>().getOrThrow()

fun <K, V> Sequence<Pair<K, V>>?.toNonEmptySortedMapOrThrow(
    comparator: Comparator<in K>
): KNonEmptySortedMap<K, V> =
    toNonEmptySortedMap(comparator).getOrThrow()

fun <T : Comparable<T>> Sequence<T>?.toNonEmptyNavigableSet(): Validation<Violation, KNonEmptyNavigableSet<T>> =
    if (this == null) toKNonEmptyNavigableSet(NonEmptyNavigableSet.of(null as NavigableSet<T>?))
    else toKNonEmptyNavigableSet(NonEmptyNavigableSet.of(toCollection(TreeSet<T>())))

fun <T> Sequence<T>?.toNonEmptyNavigableSet(
    comparator: Comparator<in T>
): Validation<Violation, KNonEmptyNavigableSet<T>> =
    if (this == null) toKNonEmptyNavigableSet(NonEmptyNavigableSet.of(null as NavigableSet<T>?))
    else toKNonEmptyNavigableSet(NonEmptyNavigableSet.of(toCollection(TreeSet<T>(comparator))))

fun <T : Comparable<T>> Sequence<T>?.toNonEmptyNavigableSetOrThrow(): KNonEmptyNavigableSet<T> =
    toNonEmptyNavigableSet().getOrThrow()

fun <T> Sequence<T>?.toNonEmptyNavigableSetOrThrow(
    comparator: Comparator<in T>
): KNonEmptyNavigableSet<T> =
    toNonEmptyNavigableSet(comparator).getOrThrow()

fun <K : Comparable<K>, V> Sequence<Pair<K, V>>?.toNonEmptyNavigableMap(): Validation<Violation, KNonEmptyNavigableMap<K, V>> =
    if (this == null) toKNonEmptyNavigableMap(NonEmptyNavigableMap.of(null as NavigableMap<K, V>?))
    else toKNonEmptyNavigableMap(NonEmptyNavigableMap.of(toTreeMap()))

fun <K, V> Sequence<Pair<K, V>>?.toNonEmptyNavigableMap(
    comparator: Comparator<in K>
): Validation<Violation, KNonEmptyNavigableMap<K, V>> =
    if (this == null) toKNonEmptyNavigableMap(NonEmptyNavigableMap.of(null as NavigableMap<K, V>?))
    else toKNonEmptyNavigableMap(NonEmptyNavigableMap.of(toTreeMap(comparator)))

fun <K : Comparable<K>, V> Sequence<Pair<K, V>>?.toNonEmptyNavigableMapOrThrow(): KNonEmptyNavigableMap<K, V> =
    toNonEmptyNavigableMap<K, V>().getOrThrow()

fun <K, V> Sequence<Pair<K, V>>?.toNonEmptyNavigableMapOrThrow(
    comparator: Comparator<in K>
): KNonEmptyNavigableMap<K, V> =
    toNonEmptyNavigableMap(comparator).getOrThrow()

private fun <K : Comparable<K>, V> Sequence<Pair<K, V>>.toTreeMap(): TreeMap<K, V> =
    toTreeMap(null)

private fun <K, V> Sequence<Pair<K, V>>.toTreeMap(comparator: Comparator<in K>?): TreeMap<K, V> {
    val result = if (comparator == null) TreeMap<K, V>() else TreeMap<K, V>(comparator)
    for ((key, value) in this) {
        result[key] = value
    }
    return result
}

private fun <T> toKNonEmptyList(validation: Validation<Violation, NonEmptyList<T>>): Validation<Violation, KNonEmptyList<T>> =
    if (validation.isValid) Validation.valid(validation.get().asKotlin()) else Validation.invalid(validation.error)

private fun <T> toKNonEmptySet(validation: Validation<Violation, NonEmptySet<T>>): Validation<Violation, KNonEmptySet<T>> =
    if (validation.isValid) Validation.valid(validation.get().asKotlin()) else Validation.invalid(validation.error)

private fun <K, V> toKNonEmptyMap(validation: Validation<Violation, NonEmptyMap<K, V>>): Validation<Violation, KNonEmptyMap<K, V>> =
    if (validation.isValid) Validation.valid(validation.get().asKotlin()) else Validation.invalid(validation.error)

private fun <T> toKNonEmptyIterable(
    validation: Validation<Violation, NonEmptyIterable<T>>
): Validation<Violation, KNonEmptyIterable<T>> =
    if (validation.isValid) Validation.valid(validation.get().asKotlin()) else Validation.invalid(validation.error)

private fun <T> toKNonEmptyQueue(validation: Validation<Violation, NonEmptyQueue<T>>): Validation<Violation, KNonEmptyQueue<T>> =
    if (validation.isValid) Validation.valid(validation.get().asKotlin()) else Validation.invalid(validation.error)

private fun <T> toKNonEmptyDeque(validation: Validation<Violation, NonEmptyDeque<T>>): Validation<Violation, KNonEmptyDeque<T>> =
    if (validation.isValid) Validation.valid(validation.get().asKotlin()) else Validation.invalid(validation.error)

private fun <T> toKNonEmptySortedSet(
    validation: Validation<Violation, NonEmptySortedSet<T>>
): Validation<Violation, KNonEmptySortedSet<T>> =
    if (validation.isValid) Validation.valid(validation.get().asKotlin()) else Validation.invalid(validation.error)

private fun <K, V> toKNonEmptySortedMap(
    validation: Validation<Violation, NonEmptySortedMap<K, V>>
): Validation<Violation, KNonEmptySortedMap<K, V>> =
    if (validation.isValid) Validation.valid(validation.get().asKotlin()) else Validation.invalid(validation.error)

private fun <T> toKNonEmptyNavigableSet(
    validation: Validation<Violation, NonEmptyNavigableSet<T>>
): Validation<Violation, KNonEmptyNavigableSet<T>> =
    if (validation.isValid) Validation.valid(validation.get().asKotlin()) else Validation.invalid(validation.error)

private fun <K, V> toKNonEmptyNavigableMap(
    validation: Validation<Violation, NonEmptyNavigableMap<K, V>>
): Validation<Violation, KNonEmptyNavigableMap<K, V>> =
    if (validation.isValid) Validation.valid(validation.get().asKotlin()) else Validation.invalid(validation.error)
