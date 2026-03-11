package io.github.junggikim.refined.refined;

import static io.github.junggikim.refined.support.TestCollections.listOf;
import static io.github.junggikim.refined.support.TestCollections.mapOf;
import static io.github.junggikim.refined.support.TestCollections.setOf;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.internal.RefinedSupport;
import io.github.junggikim.refined.refined.collection.NonEmptyList;
import io.github.junggikim.refined.refined.collection.NonEmptyMap;
import io.github.junggikim.refined.refined.collection.NonEmptySet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class CollectionRefinedTest {

    @Test
    void nonEmptyListRejectsEmptyAndProvidesHeadTail() {
        assertEquals("non-empty-list-empty", NonEmptyList.of(listOf()).getError().code());
        assertEquals("non-empty-list-empty", NonEmptyList.<Integer>of(null).getError().code());
        assertEquals(mapOf("containerKind", "list", "cause", "empty"), NonEmptyList.of(listOf()).getError().metadata());
        assertThrows(RefinementException.class, () -> NonEmptyList.unsafeOf(listOf()));

        NonEmptyList<Integer> list = NonEmptyList.unsafeOf(listOf(1, 2, 3));
        assertEquals(1, list.head());
        assertEquals(listOf(2, 3), list.tail());
        assertEquals(listOf(1, 2, 3), list.value());
    }

    @RepeatedTest(3)
    void nonEmptyListUsesDefensiveCopy() {
        List<Integer> source = new ArrayList<Integer>(listOf(1, 2));
        NonEmptyList<Integer> list = NonEmptyList.unsafeOf(source);
        source.add(3);

        assertEquals(listOf(1, 2), list.value());
        assertThrows(UnsupportedOperationException.class, () -> list.value().add(99));
    }

    @Test
    void nonEmptySetAndMapRejectEmptyAndAreImmutable() {
        assertEquals("non-empty-set-empty", NonEmptySet.of(setOf()).getError().code());
        assertEquals("non-empty-map-empty", NonEmptyMap.of(mapOf()).getError().code());
        assertEquals("non-empty-set-empty", NonEmptySet.<Integer>of(null).getError().code());
        assertEquals("non-empty-map-empty", NonEmptyMap.<String, Integer>of(null).getError().code());
        assertEquals(mapOf("containerKind", "set", "cause", "empty"), NonEmptySet.of(setOf()).getError().metadata());
        assertEquals(mapOf("containerKind", "map", "cause", "empty"), NonEmptyMap.of(mapOf()).getError().metadata());
        assertThrows(RefinementException.class, () -> NonEmptySet.unsafeOf(setOf()));
        assertThrows(RefinementException.class, () -> NonEmptyMap.unsafeOf(mapOf()));

        NonEmptySet<Integer> set = NonEmptySet.unsafeOf(new LinkedHashSet<Integer>(listOf(1, 2)));
        NonEmptyMap<String, Integer> map = NonEmptyMap.unsafeOf(new LinkedHashMap<String, Integer>(mapOf("a", 1)));

        assertTrue(set.value().containsAll(setOf(1, 2)));
        assertEquals(mapOf("a", 1), map.value());
        assertThrows(UnsupportedOperationException.class, () -> set.value().add(3));
        assertThrows(UnsupportedOperationException.class, () -> map.value().put("b", 2));
    }

    @Test
    void supportUtilityNonEmptyCollectionCoversCustomMessage() {
        assertEquals(listOf(1), RefinedSupport.nonEmptyCollection("collection", "collection must not be empty")
            .validate(listOf(1))
            .get());
        assertEquals(
            "collection",
            RefinedSupport.<Integer>nonEmptyCollection("collection", "collection must not be empty")
                .validate(null)
                .getError()
                .code()
        );
        assertEquals(
            "collection must not be empty",
            RefinedSupport.nonEmptyCollection("collection", "collection must not be empty")
                .validate(listOf())
                .getError()
                .message()
        );
    }

    @Test
    void ofDelegatesForCollectionTypes() {
        assertEquals(listOf(1), NonEmptyList.of(listOf(1)).get().value());
        assertEquals(setOf(1), NonEmptySet.of(setOf(1)).get().value());
        assertEquals(mapOf("a", 1), NonEmptyMap.of(mapOf("a", 1)).get().value());
    }

    @Test
    void nonEmptyCollectionOfReturnsInvalidInsteadOfThrowingForNullMembers() {
        List<Integer> listWithNull = new ArrayList<Integer>();
        listWithNull.add(1);
        listWithNull.add(null);

        LinkedHashSet<Integer> setWithNull = new LinkedHashSet<Integer>();
        setWithNull.add(1);
        setWithNull.add(null);

        LinkedHashMap<String, Integer> mapWithNullValue = new LinkedHashMap<String, Integer>();
        mapWithNullValue.put("a", null);

        LinkedHashMap<String, Integer> mapWithNullKey = new LinkedHashMap<String, Integer>();
        mapWithNullKey.put(null, 1);

        assertDoesNotThrow(() -> NonEmptyList.of(listWithNull));
        assertDoesNotThrow(() -> NonEmptySet.of(setWithNull));
        assertDoesNotThrow(() -> NonEmptyMap.of(mapWithNullValue));
        assertDoesNotThrow(() -> NonEmptyMap.of(mapWithNullKey));
        assertEquals("non-empty-list-null-element", NonEmptyList.of(listWithNull).getError().code());
        assertEquals("non-empty-set-null-element", NonEmptySet.of(setWithNull).getError().code());
        assertEquals("non-empty-map-null-value", NonEmptyMap.of(mapWithNullValue).getError().code());
        assertEquals("non-empty-map-null-key", NonEmptyMap.of(mapWithNullKey).getError().code());
        assertEquals(mapOf("containerKind", "list", "cause", "null-element"), NonEmptyList.of(listWithNull).getError().metadata());
        assertEquals(mapOf("containerKind", "set", "cause", "null-element"), NonEmptySet.of(setWithNull).getError().metadata());
        assertEquals(mapOf("containerKind", "map", "cause", "null-value"), NonEmptyMap.of(mapWithNullValue).getError().metadata());
        assertEquals(mapOf("containerKind", "map", "cause", "null-key"), NonEmptyMap.of(mapWithNullKey).getError().metadata());
    }
}
