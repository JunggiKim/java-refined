package io.github.junggikim.refined.internal;

import static io.github.junggikim.refined.support.TestCollections.listOf;
import static io.github.junggikim.refined.support.TestCollections.mapOf;
import static io.github.junggikim.refined.support.TestCollections.snapshot;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import java.math.BigDecimal;
import java.math.BigInteger;
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

    @Test
    void stringConstraintFactoriesReturnFunctionalConstraints() {
        assertTrue(RefinedSupport.nonEmptyString().validate("a").isValid());
        assertTrue(RefinedSupport.nonBlankString().validate("a").isValid());
        assertTrue(RefinedSupport.trimmedString().validate("a").isValid());
        assertTrue(RefinedSupport.emailString().validate("a@b.com").isValid());
        assertTrue(RefinedSupport.asciiString().validate("abc").isValid());
        assertTrue(RefinedSupport.alphabeticString().validate("abc").isValid());
        assertTrue(RefinedSupport.numericString().validate("123").isValid());
        assertTrue(RefinedSupport.alphanumericString().validate("abc123").isValid());
        assertTrue(RefinedSupport.slugString().validate("my-slug").isValid());
        assertTrue(RefinedSupport.lowerCaseString().validate("abc").isValid());
        assertTrue(RefinedSupport.upperCaseString().validate("ABC").isValid());
        assertTrue(RefinedSupport.uuidString().validate("550e8400-e29b-41d4-a716-446655440000").isValid());
        assertTrue(RefinedSupport.uriString().validate("https://example.com").isValid());
        assertTrue(RefinedSupport.urlString().validate("https://example.com").isValid());
        assertTrue(RefinedSupport.ipv4String().validate("127.0.0.1").isValid());
        assertTrue(RefinedSupport.hostnameString().validate("example.com").isValid());
        assertTrue(RefinedSupport.jsonString().validate("{}").isValid());
        assertTrue(RefinedSupport.base64String().validate("aGVsbG8=").isValid());
        assertTrue(RefinedSupport.base64UrlString().validate("aGVsbG8").isValid());
        assertTrue(RefinedSupport.hexColorString().validate("#FF0000").isValid());
        assertTrue(RefinedSupport.macAddressString().validate("00:11:22:33:44:55").isValid());
        assertTrue(RefinedSupport.semVerString().validate("1.0.0").isValid());
        assertTrue(RefinedSupport.iso8601DateString().validate("2026-01-01").isValid());
        assertTrue(RefinedSupport.iso8601TimeString().validate("12:00:00").isValid());
        assertTrue(RefinedSupport.iso8601DateTimeString().validate("2026-01-01T12:00:00Z").isValid());
        assertTrue(RefinedSupport.iso8601DurationString().validate("PT1H").isValid());
        assertTrue(RefinedSupport.iso8601PeriodString().validate("P1Y").isValid());
        assertTrue(RefinedSupport.creditCardString().validate("4111111111111111").isValid());
        assertTrue(RefinedSupport.isbnString().validate("9783161484100").isValid());
        assertTrue(RefinedSupport.jwtString().validate("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIn0.abc").isValid());
        assertTrue(RefinedSupport.cidrV4String().validate("192.168.0.0/24").isValid());
        assertTrue(RefinedSupport.cidrV6String().validate("::1/128").isValid());
        assertTrue(RefinedSupport.regexString().validate("[a-z]+").isValid());
        assertTrue(RefinedSupport.timeZoneIdString().validate("UTC").isValid());
        assertTrue(RefinedSupport.ulidString().validate("01ARZ3NDEKTSV4RRFFQ69G5FAV").isValid());
        assertTrue(RefinedSupport.xpathString().validate("/root/child").isValid());
        assertTrue(RefinedSupport.validIntString().validate("42").isValid());
        assertTrue(RefinedSupport.validLongString().validate("42").isValid());
        assertTrue(RefinedSupport.validShortString().validate("42").isValid());
        assertTrue(RefinedSupport.validByteString().validate("42").isValid());
        assertTrue(RefinedSupport.validDoubleString().validate("3.14").isValid());
        assertTrue(RefinedSupport.validFloatString().validate("3.14").isValid());
        assertTrue(RefinedSupport.validBigDecimalString().validate("3.14").isValid());
        assertTrue(RefinedSupport.validBigIntegerString().validate("42").isValid());
    }

    @Test
    void numericConstraintFactoriesReturnFunctionalConstraints() {
        assertTrue(RefinedSupport.positiveInt().validate(1).isValid());
        assertTrue(RefinedSupport.nonNegativeInt().validate(0).isValid());
        assertTrue(RefinedSupport.negativeInt().validate(-1).isValid());
        assertTrue(RefinedSupport.nonPositiveInt().validate(0).isValid());
        assertTrue(RefinedSupport.naturalInt().validate(1).isValid());
        assertTrue(RefinedSupport.nonZeroInt().validate(1).isValid());
        assertTrue(RefinedSupport.evenInt().validate(2).isValid());
        assertTrue(RefinedSupport.oddInt().validate(1).isValid());
        assertTrue(RefinedSupport.positiveLong().validate(1L).isValid());
        assertTrue(RefinedSupport.nonNegativeLong().validate(0L).isValid());
        assertTrue(RefinedSupport.negativeLong().validate(-1L).isValid());
        assertTrue(RefinedSupport.nonPositiveLong().validate(0L).isValid());
        assertTrue(RefinedSupport.naturalLong().validate(1L).isValid());
        assertTrue(RefinedSupport.nonZeroLong().validate(1L).isValid());
        assertTrue(RefinedSupport.evenLong().validate(2L).isValid());
        assertTrue(RefinedSupport.oddLong().validate(1L).isValid());
        assertTrue(RefinedSupport.positiveShort().validate((short) 1).isValid());
        assertTrue(RefinedSupport.nonNegativeShort().validate((short) 0).isValid());
        assertTrue(RefinedSupport.negativeShort().validate((short) -1).isValid());
        assertTrue(RefinedSupport.nonPositiveShort().validate((short) 0).isValid());
        assertTrue(RefinedSupport.naturalShort().validate((short) 1).isValid());
        assertTrue(RefinedSupport.nonZeroShort().validate((short) 1).isValid());
        assertTrue(RefinedSupport.positiveByte().validate((byte) 1).isValid());
        assertTrue(RefinedSupport.nonNegativeByte().validate((byte) 0).isValid());
        assertTrue(RefinedSupport.negativeByte().validate((byte) -1).isValid());
        assertTrue(RefinedSupport.nonPositiveByte().validate((byte) 0).isValid());
        assertTrue(RefinedSupport.naturalByte().validate((byte) 1).isValid());
        assertTrue(RefinedSupport.nonZeroByte().validate((byte) 1).isValid());
        assertTrue(RefinedSupport.positiveDouble().validate(1.0).isValid());
        assertTrue(RefinedSupport.nonNegativeDouble().validate(0.0).isValid());
        assertTrue(RefinedSupport.negativeDouble().validate(-1.0).isValid());
        assertTrue(RefinedSupport.nonPositiveDouble().validate(0.0).isValid());
        assertTrue(RefinedSupport.nonZeroDouble().validate(1.0).isValid());
        assertTrue(RefinedSupport.zeroToOneDouble().validate(0.5).isValid());
        assertTrue(RefinedSupport.positiveFloat().validate(1.0f).isValid());
        assertTrue(RefinedSupport.nonNegativeFloat().validate(0.0f).isValid());
        assertTrue(RefinedSupport.negativeFloat().validate(-1.0f).isValid());
        assertTrue(RefinedSupport.nonPositiveFloat().validate(0.0f).isValid());
        assertTrue(RefinedSupport.nonZeroFloat().validate(1.0f).isValid());
        assertTrue(RefinedSupport.zeroToOneFloat().validate(0.5f).isValid());
        assertTrue(RefinedSupport.positiveBigDecimal().validate(BigDecimal.ONE).isValid());
        assertTrue(RefinedSupport.nonNegativeBigDecimal().validate(BigDecimal.ZERO).isValid());
        assertTrue(RefinedSupport.negativeBigDecimal().validate(BigDecimal.ONE.negate()).isValid());
        assertTrue(RefinedSupport.nonPositiveBigDecimal().validate(BigDecimal.ZERO).isValid());
        assertTrue(RefinedSupport.nonZeroBigDecimal().validate(BigDecimal.ONE).isValid());
        assertTrue(RefinedSupport.positiveBigInteger().validate(BigInteger.ONE).isValid());
        assertTrue(RefinedSupport.nonNegativeBigInteger().validate(BigInteger.ZERO).isValid());
        assertTrue(RefinedSupport.negativeBigInteger().validate(BigInteger.ONE.negate()).isValid());
        assertTrue(RefinedSupport.nonPositiveBigInteger().validate(BigInteger.ZERO).isValid());
        assertTrue(RefinedSupport.naturalBigInteger().validate(BigInteger.ONE).isValid());
        assertTrue(RefinedSupport.nonZeroBigInteger().validate(BigInteger.ONE).isValid());
        assertTrue(RefinedSupport.evenBigInteger().validate(BigInteger.valueOf(2)).isValid());
        assertTrue(RefinedSupport.oddBigInteger().validate(BigInteger.ONE).isValid());
        assertTrue(RefinedSupport.finiteDouble().validate(1.0).isValid());
        assertTrue(RefinedSupport.finiteFloat().validate(1.0f).isValid());
        assertTrue(RefinedSupport.nonNaNDouble().validate(1.0).isValid());
        assertTrue(RefinedSupport.nonNaNFloat().validate(1.0f).isValid());
    }

    @Test
    void characterAndBooleanConstraintFactoriesReturnFunctionalConstraints() {
        assertTrue(RefinedSupport.digitChar().validate('1').isValid());
        assertTrue(RefinedSupport.letterChar().validate('a').isValid());
        assertTrue(RefinedSupport.letterOrDigitChar().validate('a').isValid());
        assertTrue(RefinedSupport.lowerCaseChar().validate('a').isValid());
        assertTrue(RefinedSupport.upperCaseChar().validate('A').isValid());
        assertTrue(RefinedSupport.whitespaceChar().validate(' ').isValid());
        assertTrue(RefinedSupport.specialChar().validate('!').isValid());
        assertTrue(RefinedSupport.trueValue().validate(true).isValid());
        assertTrue(RefinedSupport.falseValue().validate(false).isValid());
        assertTrue(RefinedSupport.andBooleanList().validate(listOf(true, true)).isValid());
        assertTrue(RefinedSupport.orBooleanList().validate(listOf(false, true)).isValid());
        assertTrue(RefinedSupport.nandBooleanList().validate(listOf(false, true)).isValid());
        assertTrue(RefinedSupport.norBooleanList().validate(listOf(false, false)).isValid());
        assertTrue(RefinedSupport.xorBooleanList().validate(listOf(true, false)).isValid());
        assertTrue(RefinedSupport.oneOfBooleanList().validate(listOf(true, false, false)).isValid());
    }

    @Test
    void parameterizedConstraintFactoriesReturnFunctionalConstraints() {
        assertTrue(RefinedSupport.greaterOrEqual(0, "c", "m").validate(1).isValid());
        assertTrue(RefinedSupport.lessThan(10, "c", "m").validate(5).isValid());
        assertTrue(RefinedSupport.lessOrEqual(10, "c", "m").validate(10).isValid());
        assertTrue(RefinedSupport.nonZero(0, "c", "m").validate(1).isValid());
    }

    @Test
    void stringConstraintsRejectEmptyStringWhenRequired() {
        assertEquals("alphabetic-string", RefinedSupport.alphabeticString().validate("").getError().code());
        assertEquals("alphanumeric-string", RefinedSupport.alphanumericString().validate("").getError().code());
    }

    @Test
    void xmlStringRejectsDoctypeDeclarations() {
        String xmlWithDoctype = "<?xml version=\"1.0\"?><!DOCTYPE foo [<!ENTITY xxe \"xxe\">]><root>&xxe;</root>";
        assertEquals("xml-string", RefinedSupport.xmlString().validate(xmlWithDoctype).getError().code());
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
