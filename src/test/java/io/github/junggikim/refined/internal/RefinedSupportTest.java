package io.github.junggikim.refined.internal;

import static io.github.junggikim.refined.support.TestCollections.listOf;
import static io.github.junggikim.refined.support.TestCollections.mapOf;
import static io.github.junggikim.refined.support.TestCollections.snapshot;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;

class RefinedSupportTest {

    @Test
    void copyHelpersCreateImmutableSnapshotsAcrossCollectionShapes() {
        Iterable<Integer> iterable = () -> listOf(1, 2, 3).iterator();
        LinkedHashSet<Integer> linkedSet = new LinkedHashSet<Integer>(listOf(1, 2));
        LinkedHashMap<Integer, String> linkedMap = new LinkedHashMap<>();
        linkedMap.put(1, "one");
        linkedMap.put(2, "two");

        SortedSet<Integer> naturalSortedSet = new TreeSet<Integer>(listOf(2, 1));
        SortedSet<Integer> reverseSortedSet = new TreeSet<>(Collections.reverseOrder());
        reverseSortedSet.addAll(listOf(1, 2));

        SortedMap<Integer, String> naturalSortedMap = new TreeMap<Integer, String>(mapOf(2, "two", 1, "one"));
        SortedMap<Integer, String> reverseSortedMap = new TreeMap<>(Collections.reverseOrder());
        reverseSortedMap.put(1, "one");
        reverseSortedMap.put(2, "two");

        NavigableSet<Integer> naturalNavigableSet = new TreeSet<Integer>(listOf(2, 1));
        NavigableSet<Integer> reverseNavigableSet = new TreeSet<>(Collections.reverseOrder());
        reverseNavigableSet.addAll(listOf(1, 2));

        NavigableMap<Integer, String> naturalNavigableMap = new TreeMap<Integer, String>(mapOf(2, "two", 1, "one"));
        NavigableMap<Integer, String> reverseNavigableMap = new TreeMap<>(Collections.reverseOrder());
        reverseNavigableMap.put(1, "one");
        reverseNavigableMap.put(2, "two");

        assertEquals(listOf(1, 2, 3), RefinedSupport.copyIterableToList(iterable));
        assertEquals(listOf(1, 2), RefinedSupport.copyQueueToList(new java.util.ArrayDeque<Integer>(listOf(1, 2))));
        assertEquals(listOf(1, 2), snapshot(RefinedSupport.copyLinkedSet(linkedSet)));
        assertEquals(listOf(1, 2), snapshot(RefinedSupport.copyLinkedMap(linkedMap).keySet()));

        assertEquals(listOf(1, 2), snapshot(RefinedSupport.copySortedSet(naturalSortedSet)));
        assertEquals(listOf(2, 1), snapshot(RefinedSupport.copySortedSet(reverseSortedSet)));
        assertEquals(listOf(1, 2), snapshot(RefinedSupport.copySortedMap(naturalSortedMap).keySet()));
        assertEquals(listOf(2, 1), snapshot(RefinedSupport.copySortedMap(reverseSortedMap).keySet()));

        assertEquals(listOf(1, 2), snapshot(RefinedSupport.copyNavigableSet(naturalNavigableSet)));
        assertEquals(listOf(2, 1), snapshot(RefinedSupport.copyNavigableSet(reverseNavigableSet)));
        assertEquals(listOf(1, 2), snapshot(RefinedSupport.copyNavigableMap(naturalNavigableMap).keySet()));
        assertEquals(listOf(2, 1), snapshot(RefinedSupport.copyNavigableMap(reverseNavigableMap).keySet()));

        assertThrows(UnsupportedOperationException.class, () -> RefinedSupport.copyLinkedSet(linkedSet).add(3));
        assertThrows(UnsupportedOperationException.class, () -> RefinedSupport.copyLinkedMap(linkedMap).put(3, "three"));
        assertThrows(UnsupportedOperationException.class, () -> RefinedSupport.copySortedSet(naturalSortedSet).add(3));
        assertThrows(UnsupportedOperationException.class, () -> RefinedSupport.copySortedMap(naturalSortedMap).put(3, "three"));
        assertThrows(UnsupportedOperationException.class, () -> RefinedSupport.copyNavigableSet(naturalNavigableSet).add(3));
        assertThrows(UnsupportedOperationException.class, () -> RefinedSupport.copyNavigableMap(naturalNavigableMap).put(3, "three"));
    }

    @Test
    void ipv6ConstraintRejectsIpv4MappedAddressWithColon() {
        assertEquals("ipv6-string", RefinedSupport.ipv6String().validate("::ffff:192.168.0.1").getError().code());
    }

    @Test
    void hexStringConstraintRejectsEmptyAndNonHexContent() {
        assertEquals("hex-string", RefinedSupport.hexString().validate("").getError().code());
        assertEquals("hex-string", RefinedSupport.hexString().validate("xyz").getError().code());
    }

    @Test
    void blankDetectionTreatsUnicodeSpacesAsBlank() {
        assertEquals(true, RefinedSupport.isBlank(null));
        assertEquals(true, RefinedSupport.isBlank("\u00A0"));
        assertEquals(true, RefinedSupport.isBlank("\u2007"));
        assertEquals(true, RefinedSupport.isBlank("\u202F"));
        assertEquals(false, RefinedSupport.isBlank("a"));
    }

    @Test
    void trimmedHelperHandlesNullAndTrailingUnicodeWhitespace() {
        assertEquals(false, RefinedSupport.isTrimmed(null));
        assertEquals(true, RefinedSupport.isTrimmed(""));
        assertEquals(true, RefinedSupport.isTrimmed("abc"));
        assertEquals(false, RefinedSupport.isTrimmed("abc "));
        assertEquals(false, RefinedSupport.isTrimmed("abc\u00A0"));
    }

    @Test
    void xmlConstraintRejectsInvalidXmlWithoutWritingToStandardError() {
        ByteArrayOutputStream errBuffer = new ByteArrayOutputStream();
        PrintStream capturedErr;
        try {
            capturedErr = new PrintStream(errBuffer, true, StandardCharsets.UTF_8.name());
        } catch (java.io.UnsupportedEncodingException exception) {
            throw new AssertionError(exception);
        }
        PrintStream originalErr = System.err;
        try {
            System.setErr(capturedErr);
            assertEquals("xml-string", RefinedSupport.xmlString().validate("<root>").getError().code());
        } finally {
            System.setErr(originalErr);
            capturedErr.close();
        }
        assertEquals("", new String(errBuffer.toByteArray(), StandardCharsets.UTF_8).trim());
    }

    @Test
    void nonEmptySnapshotHelpersPreserveSpecificFailureCodesAndMetadata() {
        LinkedHashMap<String, Integer> nullKeyMap = new LinkedHashMap<String, Integer>();
        nullKeyMap.put(null, 1);

        LinkedHashMap<String, Integer> nullValueMap = new LinkedHashMap<String, Integer>();
        nullValueMap.put("a", null);

        LinkedHashSet<Integer> setWithNull = new LinkedHashSet<Integer>();
        setWithNull.add(1);
        setWithNull.add(null);

        assertEquals("non-empty-list-null-element", RefinedSupport.nonEmptyListSnapshot(listOf(1, null)).getError().code());
        assertEquals(mapOf("containerKind", "list", "cause", "null-element"), RefinedSupport.nonEmptyListSnapshot(listOf(1, null)).getError().metadata());
        assertEquals("non-empty-set-null-element", RefinedSupport.nonEmptySetSnapshot(setWithNull).getError().code());
        assertEquals("non-empty-map-null-key", RefinedSupport.nonEmptyMapSnapshot(nullKeyMap).getError().code());
        assertEquals("non-empty-map-null-value", RefinedSupport.nonEmptyMapSnapshot(nullValueMap).getError().code());
        assertEquals(mapOf("containerKind", "iterable", "cause", "null-element"), RefinedSupport.nonEmptyIterableSnapshot(iterableOf(1, null)).getError().metadata());
        assertEquals(mapOf("containerKind", "queue", "cause", "empty"), RefinedSupport.nonEmptyQueueSnapshot(new java.util.ArrayDeque<Integer>()).getError().metadata());
        assertEquals(mapOf("containerKind", "deque", "cause", "empty"), RefinedSupport.nonEmptyDequeSnapshot(new java.util.ArrayDeque<Integer>()).getError().metadata());
    }

    @Test
    void sortedAndNavigableSnapshotHelpersMapComparatorFailuresToInvalidCodes() {
        assertEquals("non-empty-sorted-set-invalid-element", RefinedSupport.nonEmptySortedSetSnapshot(failingSortedSet()).getError().code());
        assertEquals(
            mapOf("containerKind", "sorted-set", "cause", "invalid-element"),
            RefinedSupport.nonEmptySortedSetSnapshot(failingSortedSet()).getError().metadata()
        );
        assertEquals("non-empty-navigable-set-invalid-element", RefinedSupport.nonEmptyNavigableSetSnapshot(failingNavigableSet()).getError().code());
        assertEquals("non-empty-sorted-map-invalid-entry", RefinedSupport.nonEmptySortedMapSnapshot(failingSortedMap()).getError().code());
        assertEquals("non-empty-navigable-map-invalid-entry", RefinedSupport.nonEmptyNavigableMapSnapshot(failingNavigableMap()).getError().code());
    }

    @Test
    void snapshotHelpersCoverIteratorFailuresAndOrderedNullContent() {
        assertEquals("non-empty-iterable-invalid-element", RefinedSupport.nonEmptyIterableSnapshot(failingIterable()).getError().code());
        assertEquals("non-empty-set-invalid-element", RefinedSupport.nonEmptySetSnapshot(failingSet()).getError().code());
        assertEquals("non-empty-map-invalid-entry", RefinedSupport.nonEmptyMapSnapshot(failingMap()).getError().code());
        assertEquals("non-empty-sorted-set-null-element", RefinedSupport.nonEmptySortedSetSnapshot(nullElementSortedSet()).getError().code());
        assertEquals("non-empty-navigable-set-null-element", RefinedSupport.nonEmptyNavigableSetSnapshot(nullElementNavigableSet()).getError().code());
        assertEquals("non-empty-sorted-map-null-key", RefinedSupport.nonEmptySortedMapSnapshot(nullKeySortedMap()).getError().code());
        assertEquals("non-empty-sorted-map-null-value", RefinedSupport.nonEmptySortedMapSnapshot(nullValueSortedMap()).getError().code());
        assertEquals("non-empty-navigable-map-null-key", RefinedSupport.nonEmptyNavigableMapSnapshot(nullKeyNavigableMap()).getError().code());
        assertEquals("non-empty-navigable-map-null-value", RefinedSupport.nonEmptyNavigableMapSnapshot(nullValueNavigableMap()).getError().code());
    }

    private static Iterable<Integer> iterableOf(Integer... values) {
        return () -> listOf(values).iterator();
    }

    private static Iterable<Integer> failingIterable() {
        return new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {
                    @Override
                    public boolean hasNext() {
                        return true;
                    }

                    @Override
                    public Integer next() {
                        throw new IllegalArgumentException("boom");
                    }
                };
            }
        };
    }

    private static Set<Integer> failingSet() {
        return new AbstractSet<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {
                    @Override
                    public boolean hasNext() {
                        return true;
                    }

                    @Override
                    public Integer next() {
                        throw new IllegalArgumentException("boom");
                    }
                };
            }

            @Override
            public int size() {
                return 1;
            }
        };
    }

    private static Map<String, Integer> failingMap() {
        return new AbstractMap<String, Integer>() {
            @Override
            public Set<Map.Entry<String, Integer>> entrySet() {
                return new AbstractSet<Map.Entry<String, Integer>>() {
                    @Override
                    public Iterator<Map.Entry<String, Integer>> iterator() {
                        return new Iterator<Map.Entry<String, Integer>>() {
                            @Override
                            public boolean hasNext() {
                                return true;
                            }

                            @Override
                            public Map.Entry<String, Integer> next() {
                                throw new IllegalArgumentException("boom");
                            }
                        };
                    }

                    @Override
                    public int size() {
                        return 1;
                    }
                };
            }
        };
    }

    private static SortedSet<Integer> failingSortedSet() {
        return new SimpleSortedSet(listOf(1, 2), failingComparator());
    }

    private static NavigableSet<Integer> failingNavigableSet() {
        return new SimpleNavigableSet(listOf(1, 2), failingComparator());
    }

    private static SortedMap<Integer, String> failingSortedMap() {
        return new SimpleSortedMap(entries(1, "one", 2, "two"), failingComparator());
    }

    private static NavigableMap<Integer, String> failingNavigableMap() {
        return new SimpleNavigableMap(entries(1, "one", 2, "two"), failingComparator());
    }

    private static SortedSet<Integer> nullElementSortedSet() {
        return new SimpleSortedSet(listOf(1, null), null);
    }

    private static NavigableSet<Integer> nullElementNavigableSet() {
        return new SimpleNavigableSet(listOf(1, null), null);
    }

    private static SortedMap<Integer, String> nullKeySortedMap() {
        return new SimpleSortedMap(entries(null, "one"), null);
    }

    private static SortedMap<Integer, String> nullValueSortedMap() {
        return new SimpleSortedMap(entries(1, null), null);
    }

    private static NavigableMap<Integer, String> nullKeyNavigableMap() {
        return new SimpleNavigableMap(entries(null, "one"), null);
    }

    private static NavigableMap<Integer, String> nullValueNavigableMap() {
        return new SimpleNavigableMap(entries(1, null), null);
    }

    private static Comparator<Integer> failingComparator() {
        return new Comparator<Integer>() {
            @Override
            public int compare(Integer left, Integer right) {
                throw new IllegalArgumentException("boom");
            }
        };
    }

    private static Set<Map.Entry<Integer, String>> entries(Integer key, String value) {
        LinkedHashSet<Map.Entry<Integer, String>> entries = new LinkedHashSet<Map.Entry<Integer, String>>();
        entries.add(new AbstractMap.SimpleImmutableEntry<Integer, String>(key, value));
        return Collections.unmodifiableSet(entries);
    }

    private static Set<Map.Entry<Integer, String>> entries(Integer keyOne, String valueOne, Integer keyTwo, String valueTwo) {
        LinkedHashSet<Map.Entry<Integer, String>> entries = new LinkedHashSet<Map.Entry<Integer, String>>();
        entries.add(new AbstractMap.SimpleImmutableEntry<Integer, String>(keyOne, valueOne));
        entries.add(new AbstractMap.SimpleImmutableEntry<Integer, String>(keyTwo, valueTwo));
        return Collections.unmodifiableSet(entries);
    }

    private static class SimpleSortedSet extends AbstractSet<Integer> implements SortedSet<Integer> {
        private final List<Integer> values;
        private final Comparator<Integer> comparator;

        private SimpleSortedSet(List<Integer> values, Comparator<Integer> comparator) {
            this.values = values;
            this.comparator = comparator;
        }

        @Override
        public Comparator<? super Integer> comparator() {
            return comparator;
        }

        @Override
        public SortedSet<Integer> subSet(Integer fromElement, Integer toElement) {
            return this;
        }

        @Override
        public SortedSet<Integer> headSet(Integer toElement) {
            return this;
        }

        @Override
        public SortedSet<Integer> tailSet(Integer fromElement) {
            return this;
        }

        @Override
        public Integer first() {
            return values.get(0);
        }

        @Override
        public Integer last() {
            return values.get(values.size() - 1);
        }

        @Override
        public Iterator<Integer> iterator() {
            return values.iterator();
        }

        @Override
        public int size() {
            return values.size();
        }
    }

    private static final class SimpleNavigableSet extends SimpleSortedSet implements NavigableSet<Integer> {
        private SimpleNavigableSet(List<Integer> values, Comparator<Integer> comparator) {
            super(values, comparator);
        }

        @Override
        public Integer lower(Integer e) {
            return null;
        }

        @Override
        public Integer floor(Integer e) {
            return null;
        }

        @Override
        public Integer ceiling(Integer e) {
            return null;
        }

        @Override
        public Integer higher(Integer e) {
            return null;
        }

        @Override
        public Integer pollFirst() {
            return null;
        }

        @Override
        public Integer pollLast() {
            return null;
        }

        @Override
        public NavigableSet<Integer> descendingSet() {
            return this;
        }

        @Override
        public Iterator<Integer> descendingIterator() {
            return listOf(2, 1).iterator();
        }

        @Override
        public NavigableSet<Integer> subSet(Integer fromElement, boolean fromInclusive, Integer toElement, boolean toInclusive) {
            return this;
        }

        @Override
        public NavigableSet<Integer> headSet(Integer toElement, boolean inclusive) {
            return this;
        }

        @Override
        public NavigableSet<Integer> tailSet(Integer fromElement, boolean inclusive) {
            return this;
        }
    }

    private static class SimpleSortedMap extends AbstractMap<Integer, String> implements SortedMap<Integer, String> {
        private final Set<Map.Entry<Integer, String>> entries;
        private final Comparator<Integer> comparator;

        private SimpleSortedMap(Set<Map.Entry<Integer, String>> entries, Comparator<Integer> comparator) {
            this.entries = entries;
            this.comparator = comparator;
        }

        @Override
        public Comparator<? super Integer> comparator() {
            return comparator;
        }

        @Override
        public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
            return this;
        }

        @Override
        public SortedMap<Integer, String> headMap(Integer toKey) {
            return this;
        }

        @Override
        public SortedMap<Integer, String> tailMap(Integer fromKey) {
            return this;
        }

        @Override
        public Integer firstKey() {
            return entries.iterator().next().getKey();
        }

        @Override
        public Integer lastKey() {
            Integer lastKey = null;
            for (Map.Entry<Integer, String> entry : entries) {
                lastKey = entry.getKey();
            }
            return lastKey;
        }

        @Override
        public Set<Map.Entry<Integer, String>> entrySet() {
            return entries;
        }
    }

    private static final class SimpleNavigableMap extends SimpleSortedMap implements NavigableMap<Integer, String> {
        private SimpleNavigableMap(Set<Map.Entry<Integer, String>> entries, Comparator<Integer> comparator) {
            super(entries, comparator);
        }

        @Override
        public Map.Entry<Integer, String> lowerEntry(Integer key) {
            return null;
        }

        @Override
        public Integer lowerKey(Integer key) {
            return null;
        }

        @Override
        public Map.Entry<Integer, String> floorEntry(Integer key) {
            return null;
        }

        @Override
        public Integer floorKey(Integer key) {
            return null;
        }

        @Override
        public Map.Entry<Integer, String> ceilingEntry(Integer key) {
            return null;
        }

        @Override
        public Integer ceilingKey(Integer key) {
            return null;
        }

        @Override
        public Map.Entry<Integer, String> higherEntry(Integer key) {
            return null;
        }

        @Override
        public Integer higherKey(Integer key) {
            return null;
        }

        @Override
        public Map.Entry<Integer, String> firstEntry() {
            return null;
        }

        @Override
        public Map.Entry<Integer, String> lastEntry() {
            return null;
        }

        @Override
        public Map.Entry<Integer, String> pollFirstEntry() {
            return null;
        }

        @Override
        public Map.Entry<Integer, String> pollLastEntry() {
            return null;
        }

        @Override
        public NavigableMap<Integer, String> descendingMap() {
            return this;
        }

        @Override
        public NavigableSet<Integer> navigableKeySet() {
            return new TreeSet<Integer>(listOf(1, 2));
        }

        @Override
        public NavigableSet<Integer> descendingKeySet() {
            TreeSet<Integer> reverse = new TreeSet<Integer>(Collections.reverseOrder());
            reverse.addAll(listOf(1, 2));
            return reverse;
        }

        @Override
        public NavigableMap<Integer, String> subMap(Integer fromKey, boolean fromInclusive, Integer toKey, boolean toInclusive) {
            return this;
        }

        @Override
        public NavigableMap<Integer, String> headMap(Integer toKey, boolean inclusive) {
            return this;
        }

        @Override
        public NavigableMap<Integer, String> tailMap(Integer fromKey, boolean inclusive) {
            return this;
        }
    }
}
