package io.github.junggikim.refined.internal;

import io.github.junggikim.refined.core.Constraint;
import io.github.junggikim.refined.core.RefinementException;
import io.github.junggikim.refined.validation.Validation;
import io.github.junggikim.refined.violation.Violation;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Comparator;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public final class RefinedSupport {
    private static final BigInteger BIG_INTEGER_TWO = BigInteger.valueOf(2L);
    private static final String DISALLOW_DOCTYPE_DECL = "http://apache.org/xml/features/disallow-doctype-decl";
    private static final String EXTERNAL_GENERAL_ENTITIES = "http://xml.org/sax/features/external-general-entities";
    private static final String EXTERNAL_PARAMETER_ENTITIES = "http://xml.org/sax/features/external-parameter-entities";
    private static final Pattern EMAIL_SHAPE_PATTERN = Pattern.compile("^[A-Za-z0-9!#$%&'*+/=?^_`{|}~.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern SLUG_PATTERN = Pattern.compile("^[a-z0-9]+(?:-[a-z0-9]+)*$");
    private static final Pattern IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}$");
    private static final Pattern HEX_PATTERN = Pattern.compile("^[0-9A-Fa-f]+$");
    private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("^#(?:[0-9A-Fa-f]{3}|[0-9A-Fa-f]{6}|[0-9A-Fa-f]{8})$");
    private static final Pattern ULID_PATTERN = Pattern.compile("^[0-9A-HJKMNP-TV-Z]{26}$");
    private static final Pattern MAC_COLON = Pattern.compile("^[0-9A-Fa-f]{2}(:[0-9A-Fa-f]{2}){5}$");
    private static final Pattern MAC_DASH = Pattern.compile("^[0-9A-Fa-f]{2}(-[0-9A-Fa-f]{2}){5}$");
    private static final Pattern MAC_DOT = Pattern.compile("^[0-9A-Fa-f]{4}\\.[0-9A-Fa-f]{4}\\.[0-9A-Fa-f]{4}$");
    private static final Pattern SEMVER_PATTERN = Pattern.compile(
        "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)"
        + "(?:-((?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*)"
        + "(?:\\.(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?"
        + "(?:\\+([0-9a-zA-Z-]+(?:\\.[0-9a-zA-Z-]+)*))?$");
    private static final Pattern CIDR_V4_PATTERN = Pattern.compile(
        "^(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)"
        + "(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}"
        + "/(3[0-2]|[12]?\\d)$");
    private static final Pattern BASE64_URL_PART = Pattern.compile("^[A-Za-z0-9_-]*$");

    private RefinedSupport() {
    }

    @FunctionalInterface
    private interface ThrowingStringValidator {

        void validate(String value) throws Exception;
    }

    private static Violation collectionViolation(String code, String message, String containerKind, String cause) {
        return Violation.of(code, message)
            .withMetadata("containerKind", containerKind)
            .withMetadata("cause", cause);
    }

    private static Violation emptyCollectionViolation(String baseCode, String containerKind) {
        return collectionViolation(baseCode + "-empty", "value must not be empty", containerKind, "empty");
    }

    private static Violation nullElementViolation(String baseCode, String containerKind) {
        return collectionViolation(baseCode + "-null-element", "value must not contain null elements", containerKind, "null-element");
    }

    private static Violation nullKeyViolation(String baseCode, String containerKind) {
        return collectionViolation(baseCode + "-null-key", "value must not contain null keys", containerKind, "null-key");
    }

    private static Violation nullValueViolation(String baseCode, String containerKind) {
        return collectionViolation(baseCode + "-null-value", "value must not contain null values", containerKind, "null-value");
    }

    private static Violation invalidElementViolation(String baseCode, String containerKind) {
        return collectionViolation(baseCode + "-invalid-element", "value contains invalid elements", containerKind, "invalid-element");
    }

    private static Violation invalidEntryViolation(String baseCode, String containerKind) {
        return collectionViolation(baseCode + "-invalid-entry", "value contains invalid entries", containerKind, "invalid-entry");
    }

    public static <T, R> Validation<Violation, R> refine(T value, Constraint<T> constraint, Function<T, R> factory) {
        return constraint.validate(value).map(factory);
    }

    public static <T, R> R unsafeRefine(T value, Constraint<T> constraint, Function<T, R> factory) {
        Validation<Violation, R> result = refine(value, constraint, factory);
        if (result.isValid()) {
            return result.get();
        }
        throw new RefinementException(result.getError());
    }

    public static <T> Constraint<T> nonNull(String code, String message) {
        return value -> value == null ? Validation.invalid(Violation.of(code, message)) : Validation.valid(value);
    }

    public static <T> Constraint<T> require(String code, String message, Predicate<T> predicate) {
        return value -> value != null && predicate.test(value)
            ? Validation.valid(value)
            : Validation.invalid(Violation.of(code, message));
    }

    private static Constraint<String> parsedString(String code, String message, ThrowingStringValidator validator) {
        return value -> {
            if (value == null) {
                return Validation.invalid(Violation.of(code, message));
            }
            try {
                validator.validate(value);
                return Validation.valid(value);
            } catch (Exception exception) {
                return Validation.invalid(Violation.of(code, message));
            }
        };
    }

    private static DocumentBuilderFactory secureXmlFactory() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setFeature(DISALLOW_DOCTYPE_DECL, true);
        factory.setFeature(EXTERNAL_GENERAL_ENTITIES, false);
        factory.setFeature(EXTERNAL_PARAMETER_ENTITIES, false);
        factory.setXIncludeAware(false);
        factory.setExpandEntityReferences(false);
        return factory;
    }

    private static <T> Validation<Violation, List<T>> validateLinearSnapshot(
        Iterable<? extends T> value,
        String baseCode,
        String containerKind
    ) {
        if (value == null) {
            return Validation.invalid(emptyCollectionViolation(baseCode, containerKind));
        }
        ArrayList<T> copy = new ArrayList<T>();
        try {
            for (T element : value) {
                if (element == null) {
                    return Validation.invalid(nullElementViolation(baseCode, containerKind));
                }
                copy.add(element);
            }
        } catch (RuntimeException exception) {
            return Validation.invalid(invalidElementViolation(baseCode, containerKind));
        }
        if (copy.isEmpty()) {
            return Validation.invalid(emptyCollectionViolation(baseCode, containerKind));
        }
        return Validation.valid(Collections.unmodifiableList(copy));
    }

    private static <T> Validation<Violation, List<T>> validateLinearStreamSnapshot(
        Stream<T> value,
        String baseCode,
        String containerKind
    ) {
        if (value == null) {
            return Validation.invalid(emptyCollectionViolation(baseCode, containerKind));
        }
        ArrayList<T> copy = new ArrayList<T>();
        try {
            Iterator<T> iterator = value.iterator();
            while (iterator.hasNext()) {
                T element = iterator.next();
                if (element == null) {
                    return Validation.invalid(nullElementViolation(baseCode, containerKind));
                }
                copy.add(element);
            }
        } catch (RuntimeException exception) {
            return Validation.invalid(invalidElementViolation(baseCode, containerKind));
        }
        if (copy.isEmpty()) {
            return Validation.invalid(emptyCollectionViolation(baseCode, containerKind));
        }
        return Validation.valid(Collections.unmodifiableList(copy));
    }

    private static <T> Validation<Violation, Set<T>> validateSetSnapshot(
        Collection<? extends T> value,
        String baseCode,
        String containerKind
    ) {
        if (value == null || value.isEmpty()) {
            return Validation.invalid(emptyCollectionViolation(baseCode, containerKind));
        }
        LinkedHashSet<T> copy = new LinkedHashSet<T>(Math.max(1, value.size()));
        try {
            for (T element : value) {
                if (element == null) {
                    return Validation.invalid(nullElementViolation(baseCode, containerKind));
                }
                copy.add(element);
            }
        } catch (RuntimeException exception) {
            return Validation.invalid(invalidElementViolation(baseCode, containerKind));
        }
        return Validation.valid(Collections.unmodifiableSet(copy));
    }

    private static <K, V> Validation<Violation, Map<K, V>> validateMapSnapshot(
        Map<? extends K, ? extends V> value,
        String baseCode,
        String containerKind
    ) {
        if (value == null || value.isEmpty()) {
            return Validation.invalid(emptyCollectionViolation(baseCode, containerKind));
        }
        LinkedHashMap<K, V> copy = new LinkedHashMap<K, V>(Math.max(1, value.size()));
        try {
            for (Map.Entry<? extends K, ? extends V> entry : value.entrySet()) {
                if (entry.getKey() == null) {
                    return Validation.invalid(nullKeyViolation(baseCode, containerKind));
                }
                if (entry.getValue() == null) {
                    return Validation.invalid(nullValueViolation(baseCode, containerKind));
                }
                copy.put(entry.getKey(), entry.getValue());
            }
        } catch (RuntimeException exception) {
            return Validation.invalid(invalidEntryViolation(baseCode, containerKind));
        }
        return Validation.valid(Collections.unmodifiableMap(copy));
    }

    private static <K, V> Validation<Violation, Map<K, V>> validateMapEntryStreamSnapshot(
        Stream<Map.Entry<K, V>> value,
        String baseCode,
        String containerKind
    ) {
        if (value == null) {
            return Validation.invalid(emptyCollectionViolation(baseCode, containerKind));
        }
        LinkedHashMap<K, V> copy = new LinkedHashMap<K, V>();
        try {
            Iterator<Map.Entry<K, V>> iterator = value.iterator();
            while (iterator.hasNext()) {
                Map.Entry<K, V> entry = iterator.next();
                if (entry == null) {
                    return Validation.invalid(invalidEntryViolation(baseCode, containerKind));
                }
                if (entry.getKey() == null) {
                    return Validation.invalid(nullKeyViolation(baseCode, containerKind));
                }
                if (entry.getValue() == null) {
                    return Validation.invalid(nullValueViolation(baseCode, containerKind));
                }
                copy.put(entry.getKey(), entry.getValue());
            }
        } catch (RuntimeException exception) {
            return Validation.invalid(invalidEntryViolation(baseCode, containerKind));
        }
        if (copy.isEmpty()) {
            return Validation.invalid(emptyCollectionViolation(baseCode, containerKind));
        }
        return Validation.valid(Collections.unmodifiableMap(copy));
    }

    private static <T> Validation<Violation, SortedSet<T>> validateSortedSetSnapshot(
        SortedSet<T> value,
        String baseCode,
        String containerKind
    ) {
        if (value == null || value.isEmpty()) {
            return Validation.invalid(emptyCollectionViolation(baseCode, containerKind));
        }
        TreeSet<T> copy = value.comparator() == null ? new TreeSet<T>() : new TreeSet<T>(value.comparator());
        try {
            for (T element : value) {
                if (element == null) {
                    return Validation.invalid(nullElementViolation(baseCode, containerKind));
                }
                copy.add(element);
            }
        } catch (RuntimeException exception) {
            return Validation.invalid(invalidElementViolation(baseCode, containerKind));
        }
        return Validation.valid(Collections.unmodifiableSortedSet(copy));
    }

    private static <T> Validation<Violation, SortedSet<T>> validateSortedSetStreamSnapshot(
        Stream<T> value,
        Comparator<? super T> comparator,
        String baseCode,
        String containerKind
    ) {
        if (value == null) {
            return Validation.invalid(emptyCollectionViolation(baseCode, containerKind));
        }
        TreeSet<T> copy = comparator == null ? new TreeSet<T>() : new TreeSet<T>(comparator);
        try {
            Iterator<T> iterator = value.iterator();
            while (iterator.hasNext()) {
                T element = iterator.next();
                if (element == null) {
                    return Validation.invalid(nullElementViolation(baseCode, containerKind));
                }
                copy.add(element);
            }
        } catch (RuntimeException exception) {
            return Validation.invalid(invalidElementViolation(baseCode, containerKind));
        }
        if (copy.isEmpty()) {
            return Validation.invalid(emptyCollectionViolation(baseCode, containerKind));
        }
        return Validation.valid(Collections.unmodifiableSortedSet(copy));
    }

    private static <K, V> Validation<Violation, SortedMap<K, V>> validateSortedMapSnapshot(
        SortedMap<K, V> value,
        String baseCode,
        String containerKind
    ) {
        if (value == null || value.isEmpty()) {
            return Validation.invalid(emptyCollectionViolation(baseCode, containerKind));
        }
        TreeMap<K, V> copy = value.comparator() == null ? new TreeMap<K, V>() : new TreeMap<K, V>(value.comparator());
        try {
            for (Map.Entry<K, V> entry : value.entrySet()) {
                if (entry.getKey() == null) {
                    return Validation.invalid(nullKeyViolation(baseCode, containerKind));
                }
                if (entry.getValue() == null) {
                    return Validation.invalid(nullValueViolation(baseCode, containerKind));
                }
                copy.put(entry.getKey(), entry.getValue());
            }
        } catch (RuntimeException exception) {
            return Validation.invalid(invalidEntryViolation(baseCode, containerKind));
        }
        return Validation.valid(Collections.unmodifiableSortedMap(copy));
    }

    private static <K, V> Validation<Violation, SortedMap<K, V>> validateSortedMapEntryStreamSnapshot(
        Stream<Map.Entry<K, V>> value,
        Comparator<? super K> comparator,
        String baseCode,
        String containerKind
    ) {
        if (value == null) {
            return Validation.invalid(emptyCollectionViolation(baseCode, containerKind));
        }
        TreeMap<K, V> copy = comparator == null ? new TreeMap<K, V>() : new TreeMap<K, V>(comparator);
        try {
            Iterator<Map.Entry<K, V>> iterator = value.iterator();
            while (iterator.hasNext()) {
                Map.Entry<K, V> entry = iterator.next();
                if (entry == null) {
                    return Validation.invalid(invalidEntryViolation(baseCode, containerKind));
                }
                if (entry.getKey() == null) {
                    return Validation.invalid(nullKeyViolation(baseCode, containerKind));
                }
                if (entry.getValue() == null) {
                    return Validation.invalid(nullValueViolation(baseCode, containerKind));
                }
                copy.put(entry.getKey(), entry.getValue());
            }
        } catch (RuntimeException exception) {
            return Validation.invalid(invalidEntryViolation(baseCode, containerKind));
        }
        if (copy.isEmpty()) {
            return Validation.invalid(emptyCollectionViolation(baseCode, containerKind));
        }
        return Validation.valid(Collections.unmodifiableSortedMap(copy));
    }

    private static <T> Validation<Violation, NavigableSet<T>> validateNavigableSetSnapshot(
        NavigableSet<T> value,
        String baseCode,
        String containerKind
    ) {
        if (value == null || value.isEmpty()) {
            return Validation.invalid(emptyCollectionViolation(baseCode, containerKind));
        }
        TreeSet<T> copy = value.comparator() == null ? new TreeSet<T>() : new TreeSet<T>(value.comparator());
        try {
            for (T element : value) {
                if (element == null) {
                    return Validation.invalid(nullElementViolation(baseCode, containerKind));
                }
                copy.add(element);
            }
        } catch (RuntimeException exception) {
            return Validation.invalid(invalidElementViolation(baseCode, containerKind));
        }
        return Validation.valid(Collections.unmodifiableNavigableSet(copy));
    }

    private static <T> Validation<Violation, NavigableSet<T>> validateNavigableSetStreamSnapshot(
        Stream<T> value,
        Comparator<? super T> comparator,
        String baseCode,
        String containerKind
    ) {
        if (value == null) {
            return Validation.invalid(emptyCollectionViolation(baseCode, containerKind));
        }
        TreeSet<T> copy = comparator == null ? new TreeSet<T>() : new TreeSet<T>(comparator);
        try {
            Iterator<T> iterator = value.iterator();
            while (iterator.hasNext()) {
                T element = iterator.next();
                if (element == null) {
                    return Validation.invalid(nullElementViolation(baseCode, containerKind));
                }
                copy.add(element);
            }
        } catch (RuntimeException exception) {
            return Validation.invalid(invalidElementViolation(baseCode, containerKind));
        }
        if (copy.isEmpty()) {
            return Validation.invalid(emptyCollectionViolation(baseCode, containerKind));
        }
        return Validation.valid(Collections.unmodifiableNavigableSet(copy));
    }

    private static <K, V> Validation<Violation, NavigableMap<K, V>> validateNavigableMapSnapshot(
        NavigableMap<K, V> value,
        String baseCode,
        String containerKind
    ) {
        if (value == null || value.isEmpty()) {
            return Validation.invalid(emptyCollectionViolation(baseCode, containerKind));
        }
        TreeMap<K, V> copy = value.comparator() == null ? new TreeMap<K, V>() : new TreeMap<K, V>(value.comparator());
        try {
            for (Map.Entry<K, V> entry : value.entrySet()) {
                if (entry.getKey() == null) {
                    return Validation.invalid(nullKeyViolation(baseCode, containerKind));
                }
                if (entry.getValue() == null) {
                    return Validation.invalid(nullValueViolation(baseCode, containerKind));
                }
                copy.put(entry.getKey(), entry.getValue());
            }
        } catch (RuntimeException exception) {
            return Validation.invalid(invalidEntryViolation(baseCode, containerKind));
        }
        return Validation.valid(Collections.unmodifiableNavigableMap(copy));
    }

    private static <K, V> Validation<Violation, NavigableMap<K, V>> validateNavigableMapEntryStreamSnapshot(
        Stream<Map.Entry<K, V>> value,
        Comparator<? super K> comparator,
        String baseCode,
        String containerKind
    ) {
        if (value == null) {
            return Validation.invalid(emptyCollectionViolation(baseCode, containerKind));
        }
        TreeMap<K, V> copy = comparator == null ? new TreeMap<K, V>() : new TreeMap<K, V>(comparator);
        try {
            Iterator<Map.Entry<K, V>> iterator = value.iterator();
            while (iterator.hasNext()) {
                Map.Entry<K, V> entry = iterator.next();
                if (entry == null) {
                    return Validation.invalid(invalidEntryViolation(baseCode, containerKind));
                }
                if (entry.getKey() == null) {
                    return Validation.invalid(nullKeyViolation(baseCode, containerKind));
                }
                if (entry.getValue() == null) {
                    return Validation.invalid(nullValueViolation(baseCode, containerKind));
                }
                copy.put(entry.getKey(), entry.getValue());
            }
        } catch (RuntimeException exception) {
            return Validation.invalid(invalidEntryViolation(baseCode, containerKind));
        }
        if (copy.isEmpty()) {
            return Validation.invalid(emptyCollectionViolation(baseCode, containerKind));
        }
        return Validation.valid(Collections.unmodifiableNavigableMap(copy));
    }

    public static <T extends Comparable<T>> void validateIntervalBounds(T minimum, T maximum, boolean allowEqual, String description) {
        Objects.requireNonNull(minimum, "minimum");
        Objects.requireNonNull(maximum, "maximum");
        int comparison = minimum.compareTo(maximum);
        if (comparison > 0 || (!allowEqual && comparison == 0)) {
            throw new IllegalArgumentException("invalid " + description + " bounds");
        }
    }

    private static boolean isUnicodeBlank(int codePoint) {
        return Character.isWhitespace(codePoint) || Character.isSpaceChar(codePoint);
    }

    private static int trimmedStartIndex(String value) {
        int index = 0;
        while (index < value.length()) {
            int codePoint = value.codePointAt(index);
            if (!isUnicodeBlank(codePoint)) {
                return index;
            }
            index += Character.charCount(codePoint);
        }
        return value.length();
    }

    private static int trimmedEndIndex(String value, int startIndex) {
        int index = value.length();
        while (index > startIndex) {
            int codePoint = value.codePointBefore(index);
            if (!isUnicodeBlank(codePoint)) {
                return index;
            }
            index -= Character.charCount(codePoint);
        }
        return startIndex;
    }

    public static boolean isTrimmed(String value) {
        if (value == null) {
            return false;
        }
        int startIndex = trimmedStartIndex(value);
        return startIndex == 0 && trimmedEndIndex(value, startIndex) == value.length();
    }

    private static boolean isWellFormedEmail(String value) {
        if (!EMAIL_SHAPE_PATTERN.matcher(value).matches()) {
            return false;
        }
        int separatorIndex = value.indexOf('@');
        String localPart = value.substring(0, separatorIndex);
        String domainPart = value.substring(separatorIndex + 1);
        if (localPart.startsWith(".") || localPart.endsWith(".") || localPart.contains("..")) {
            return false;
        }
        if (domainPart.startsWith(".") || domainPart.endsWith(".") || domainPart.contains("..")) {
            return false;
        }
        String[] labels = domainPart.split("\\.");
        if (labels.length < 2) {
            return false;
        }
        for (String label : labels) {
            if (label.startsWith("-") || label.endsWith("-")) {
                return false;
            }
        }
        return true;
    }

    public static <T extends Comparable<T>> Constraint<T> greaterThan(T minimum, String code, String message) {
        return require(code, message, value -> value.compareTo(minimum) > 0);
    }

    public static <T extends Comparable<T>> Constraint<T> greaterOrEqual(T minimum, String code, String message) {
        return require(code, message, value -> value.compareTo(minimum) >= 0);
    }

    public static <T extends Comparable<T>> Constraint<T> lessThan(T maximum, String code, String message) {
        return require(code, message, value -> value.compareTo(maximum) < 0);
    }

    public static <T extends Comparable<T>> Constraint<T> lessOrEqual(T maximum, String code, String message) {
        return require(code, message, value -> value.compareTo(maximum) <= 0);
    }

    public static <T extends Comparable<T>> Constraint<T> nonZero(T zero, String code, String message) {
        return require(code, message, value -> value.compareTo(zero) != 0);
    }

    public static <T extends Comparable<T>> Constraint<T> openInterval(T minimum, T maximum, String code, String message) {
        validateIntervalBounds(minimum, maximum, false, "open interval");
        return require(code, message, value -> value.compareTo(minimum) > 0 && value.compareTo(maximum) < 0);
    }

    public static <T extends Comparable<T>> Constraint<T> closedInterval(T minimum, T maximum, String code, String message) {
        validateIntervalBounds(minimum, maximum, true, "closed interval");
        return require(code, message, value -> value.compareTo(minimum) >= 0 && value.compareTo(maximum) <= 0);
    }

    public static <T extends Comparable<T>> Constraint<T> openClosedInterval(T minimum, T maximum, String code, String message) {
        validateIntervalBounds(minimum, maximum, false, "open-closed interval");
        return require(code, message, value -> value.compareTo(minimum) > 0 && value.compareTo(maximum) <= 0);
    }

    public static <T extends Comparable<T>> Constraint<T> closedOpenInterval(T minimum, T maximum, String code, String message) {
        validateIntervalBounds(minimum, maximum, false, "closed-open interval");
        return require(code, message, value -> value.compareTo(minimum) >= 0 && value.compareTo(maximum) < 0);
    }

    public static Constraint<Integer> positiveInt() {
        return greaterThan(0, "positive-int", "value must be greater than 0");
    }

    public static Constraint<Integer> nonNegativeInt() {
        return greaterOrEqual(0, "non-negative-int", "value must be greater than or equal to 0");
    }

    public static Constraint<Integer> negativeInt() {
        return lessThan(0, "negative-int", "value must be less than 0");
    }

    public static Constraint<Integer> nonPositiveInt() {
        return lessOrEqual(0, "non-positive-int", "value must be less than or equal to 0");
    }

    public static Constraint<Integer> naturalInt() {
        return greaterOrEqual(0, "natural-int", "value must be greater than or equal to 0");
    }

    public static Constraint<Integer> nonZeroInt() {
        return nonZero(0, "non-zero-int", "value must not be 0");
    }

    public static Constraint<Integer> evenInt() {
        return require("even-int", "value must be even", value -> value % 2 == 0);
    }

    public static Constraint<Integer> oddInt() {
        return require("odd-int", "value must be odd", value -> value % 2 != 0);
    }

    public static Constraint<Long> positiveLong() {
        return greaterThan(0L, "positive-long", "value must be greater than 0");
    }

    public static Constraint<Long> negativeLong() {
        return lessThan(0L, "negative-long", "value must be less than 0");
    }

    public static Constraint<Long> nonNegativeLong() {
        return greaterOrEqual(0L, "non-negative-long", "value must be greater than or equal to 0");
    }

    public static Constraint<Long> nonPositiveLong() {
        return lessOrEqual(0L, "non-positive-long", "value must be less than or equal to 0");
    }

    public static Constraint<Long> nonZeroLong() {
        return nonZero(0L, "non-zero-long", "value must not be 0");
    }

    public static Constraint<Long> naturalLong() {
        return greaterOrEqual(0L, "natural-long", "value must be greater than or equal to 0");
    }

    public static Constraint<Long> evenLong() {
        return require("even-long", "value must be even", value -> value % 2L == 0L);
    }

    public static Constraint<Long> oddLong() {
        return require("odd-long", "value must be odd", value -> value % 2L != 0L);
    }

    public static Constraint<BigInteger> positiveBigInteger() {
        return greaterThan(BigInteger.ZERO, "positive-big-integer", "value must be greater than 0");
    }

    public static Constraint<BigInteger> negativeBigInteger() {
        return lessThan(BigInteger.ZERO, "negative-big-integer", "value must be less than 0");
    }

    public static Constraint<BigInteger> nonNegativeBigInteger() {
        return greaterOrEqual(BigInteger.ZERO, "non-negative-big-integer", "value must be greater than or equal to 0");
    }

    public static Constraint<BigInteger> nonPositiveBigInteger() {
        return lessOrEqual(BigInteger.ZERO, "non-positive-big-integer", "value must be less than or equal to 0");
    }

    public static Constraint<BigInteger> nonZeroBigInteger() {
        return nonZero(BigInteger.ZERO, "non-zero-big-integer", "value must not be 0");
    }

    public static Constraint<BigInteger> naturalBigInteger() {
        return greaterOrEqual(BigInteger.ZERO, "natural-big-integer", "value must be greater than or equal to 0");
    }

    public static Constraint<BigInteger> evenBigInteger() {
        return require("even-big-integer", "value must be even", value -> value.mod(BIG_INTEGER_TWO).equals(BigInteger.ZERO));
    }

    public static Constraint<BigInteger> oddBigInteger() {
        return require("odd-big-integer", "value must be odd", value -> !value.mod(BIG_INTEGER_TWO).equals(BigInteger.ZERO));
    }

    public static Constraint<BigDecimal> positiveBigDecimal() {
        return greaterThan(BigDecimal.ZERO, "positive-big-decimal", "value must be greater than 0");
    }

    public static Constraint<BigDecimal> negativeBigDecimal() {
        return lessThan(BigDecimal.ZERO, "negative-big-decimal", "value must be less than 0");
    }

    public static Constraint<BigDecimal> nonNegativeBigDecimal() {
        return greaterOrEqual(BigDecimal.ZERO, "non-negative-big-decimal", "value must be greater than or equal to 0");
    }

    public static Constraint<BigDecimal> nonPositiveBigDecimal() {
        return lessOrEqual(BigDecimal.ZERO, "non-positive-big-decimal", "value must be less than or equal to 0");
    }

    public static Constraint<BigDecimal> nonZeroBigDecimal() {
        return nonZero(BigDecimal.ZERO, "non-zero-big-decimal", "value must not be 0");
    }

    public static Constraint<Byte> positiveByte() {
        return greaterThan((byte) 0, "positive-byte", "value must be greater than 0");
    }

    public static Constraint<Byte> negativeByte() {
        return lessThan((byte) 0, "negative-byte", "value must be less than 0");
    }

    public static Constraint<Byte> nonNegativeByte() {
        return greaterOrEqual((byte) 0, "non-negative-byte", "value must be greater than or equal to 0");
    }

    public static Constraint<Byte> nonPositiveByte() {
        return lessOrEqual((byte) 0, "non-positive-byte", "value must be less than or equal to 0");
    }

    public static Constraint<Byte> nonZeroByte() {
        return nonZero((byte) 0, "non-zero-byte", "value must not be 0");
    }

    public static Constraint<Byte> naturalByte() {
        return greaterOrEqual((byte) 0, "natural-byte", "value must be greater than or equal to 0");
    }

    public static Constraint<Short> positiveShort() {
        return greaterThan((short) 0, "positive-short", "value must be greater than 0");
    }

    public static Constraint<Short> negativeShort() {
        return lessThan((short) 0, "negative-short", "value must be less than 0");
    }

    public static Constraint<Short> nonNegativeShort() {
        return greaterOrEqual((short) 0, "non-negative-short", "value must be greater than or equal to 0");
    }

    public static Constraint<Short> nonPositiveShort() {
        return lessOrEqual((short) 0, "non-positive-short", "value must be less than or equal to 0");
    }

    public static Constraint<Short> nonZeroShort() {
        return nonZero((short) 0, "non-zero-short", "value must not be 0");
    }

    public static Constraint<Short> naturalShort() {
        return greaterOrEqual((short) 0, "natural-short", "value must be greater than or equal to 0");
    }

    public static Constraint<Float> positiveFloat() {
        return require("positive-float", "value must be a finite float greater than 0", value -> Float.isFinite(value) && value > 0.0f);
    }

    public static Constraint<Float> negativeFloat() {
        return require("negative-float", "value must be a finite float less than 0", value -> Float.isFinite(value) && value < 0.0f);
    }

    public static Constraint<Float> nonNegativeFloat() {
        return require("non-negative-float", "value must be a finite float greater than or equal to 0", value -> Float.isFinite(value) && value >= 0.0f);
    }

    public static Constraint<Float> nonPositiveFloat() {
        return require("non-positive-float", "value must be a finite float less than or equal to 0", value -> Float.isFinite(value) && value <= 0.0f);
    }

    public static Constraint<Float> nonZeroFloat() {
        return require("non-zero-float", "value must be a finite float that is not 0", value -> Float.isFinite(value) && value != 0.0f);
    }

    public static Constraint<Float> finiteFloat() {
        return require("finite-float", "value must be finite", Float::isFinite);
    }

    public static Constraint<Float> nonNaNFloat() {
        return require("non-nan-float", "value must not be NaN", value -> !Float.isNaN(value));
    }

    public static Constraint<Double> positiveDouble() {
        return require("positive-double", "value must be a finite double greater than 0", value -> Double.isFinite(value) && value > 0.0d);
    }

    public static Constraint<Double> negativeDouble() {
        return require("negative-double", "value must be a finite double less than 0", value -> Double.isFinite(value) && value < 0.0d);
    }

    public static Constraint<Double> nonNegativeDouble() {
        return require("non-negative-double", "value must be a finite double greater than or equal to 0", value -> Double.isFinite(value) && value >= 0.0d);
    }

    public static Constraint<Double> nonPositiveDouble() {
        return require("non-positive-double", "value must be a finite double less than or equal to 0", value -> Double.isFinite(value) && value <= 0.0d);
    }

    public static Constraint<Double> nonZeroDouble() {
        return require("non-zero-double", "value must be a finite double that is not 0", value -> Double.isFinite(value) && value != 0.0d);
    }

    public static Constraint<Double> finiteDouble() {
        return require("finite-double", "value must be finite", Double::isFinite);
    }

    public static Constraint<Double> nonNaNDouble() {
        return require("non-nan-double", "value must not be NaN", value -> !Double.isNaN(value));
    }

    public static Constraint<Double> zeroToOneDouble() {
        return require("zero-to-one-double", "value must be a finite double between 0.0 and 1.0",
            value -> Double.isFinite(value) && value >= 0.0d && value <= 1.0d);
    }

    public static Constraint<Float> zeroToOneFloat() {
        return require("zero-to-one-float", "value must be a finite float between 0.0 and 1.0",
            value -> Float.isFinite(value) && value >= 0.0f && value <= 1.0f);
    }

    public static Constraint<String> nonEmptyString() {
        return require("non-empty-string", "value must not be empty", value -> !value.isEmpty());
    }

    public static Constraint<String> nonBlankString() {
        return require("non-blank-string", "value must not be blank", value -> !isBlank(value));
    }

    public static Constraint<String> trimmedString() {
        return require("trimmed-string", "value must already be trimmed", RefinedSupport::isTrimmed);
    }

    public static Constraint<String> emailString() {
        return require("email-string", "value must be a valid email", RefinedSupport::isWellFormedEmail);
    }

    public static Constraint<String> asciiString() {
        return require("ascii-string", "value must contain only ASCII characters", value -> value.chars().allMatch(character -> character <= 0x7F));
    }

    public static Constraint<String> alphabeticString() {
        return require("alphabetic-string", "value must contain only alphabetic characters", value -> !value.isEmpty() && value.codePoints().allMatch(Character::isLetter));
    }

    public static Constraint<String> numericString() {
        return require("numeric-string", "value must contain only numeric characters", value -> !value.isEmpty() && value.codePoints().allMatch(Character::isDigit));
    }

    public static Constraint<String> alphanumericString() {
        return require(
            "alphanumeric-string",
            "value must contain only alphanumeric characters",
            value -> !value.isEmpty() && value.codePoints().allMatch(Character::isLetterOrDigit)
        );
    }

    public static Constraint<String> slugString() {
        return require("slug-string", "value must be a valid slug", value -> SLUG_PATTERN.matcher(value).matches());
    }

    public static Constraint<String> lowerCaseString() {
        return require("lower-case-string", "value must be lower-case", value -> value.equals(value.toLowerCase(Locale.ROOT)));
    }

    public static Constraint<String> upperCaseString() {
        return require("upper-case-string", "value must be upper-case", value -> value.equals(value.toUpperCase(Locale.ROOT)));
    }

    public static Constraint<String> uuidString() {
        return parsedString("uuid-string", "value must be a valid UUID", UUID::fromString);
    }

    public static Constraint<String> uriString() {
        return parsedString("uri-string", "value must be a valid URI", URI::new);
    }

    public static Constraint<String> regexString() {
        return parsedString("regex-string", "value must be a valid regular expression", Pattern::compile);
    }

    public static Constraint<String> urlString() {
        return parsedString("url-string", "value must be a valid URL", URL::new);
    }

    public static Constraint<String> ipv4String() {
        return require("ipv4-string", "value must be a valid IPv4 address", value -> IPV4_PATTERN.matcher(value).matches());
    }

    public static Constraint<String> ipv6String() {
        return parsedString("ipv6-string", "value must be a valid IPv6 address", value -> {
            if (!value.contains(":")) {
                throw new IllegalArgumentException("not ipv6");
            }
            if (!(InetAddress.getByName(value) instanceof Inet6Address)) {
                throw new IllegalArgumentException("not ipv6");
            }
        });
    }

    public static Constraint<String> hexString() {
        return require("hex-string", "value must be a valid hexadecimal string", value -> !value.isEmpty() && HEX_PATTERN.matcher(value).matches());
    }

    public static Constraint<String> hexColorString() {
        return require("hex-color-string", "value must be a valid hex color", value -> HEX_COLOR_PATTERN.matcher(value).matches());
    }

    public static Constraint<String> xmlString() {
        return parsedString("xml-string", "value must be valid XML", value -> {
            DocumentBuilder builder = secureXmlFactory().newDocumentBuilder();
            builder.setErrorHandler(new DefaultHandler() {
                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    throw exception;
                }
            });
            builder.parse(new InputSource(new StringReader(value)));
        });
    }

    public static Constraint<String> xpathString() {
        return parsedString("xpath-string", "value must be a valid XPath expression", value -> XPathFactory.newInstance().newXPath().compile(value));
    }

    public static Constraint<String> validByteString() {
        return parsedString("valid-byte-string", "value must be parseable as a byte", Byte::parseByte);
    }

    public static Constraint<String> validShortString() {
        return parsedString("valid-short-string", "value must be parseable as a short", Short::parseShort);
    }

    public static Constraint<String> validIntString() {
        return parsedString("valid-int-string", "value must be parseable as an int", Integer::parseInt);
    }

    public static Constraint<String> validLongString() {
        return parsedString("valid-long-string", "value must be parseable as a long", Long::parseLong);
    }

    public static Constraint<String> validFloatString() {
        return parsedString("valid-float-string", "value must be parseable as a float", Float::parseFloat);
    }

    public static Constraint<String> validDoubleString() {
        return parsedString("valid-double-string", "value must be parseable as a double", Double::parseDouble);
    }

    public static Constraint<String> validBigIntegerString() {
        return parsedString("valid-big-integer-string", "value must be parseable as a BigInteger", BigInteger::new);
    }

    public static Constraint<String> validBigDecimalString() {
        return parsedString("valid-big-decimal-string", "value must be parseable as a BigDecimal", BigDecimal::new);
    }

    public static Constraint<String> base64String() {
        return parsedString("base64-string", "value must be valid Base64", value -> Base64.getDecoder().decode(value));
    }

    public static Constraint<String> base64UrlString() {
        return parsedString("base64-url-string", "value must be valid Base64 URL", value -> Base64.getUrlDecoder().decode(value));
    }

    public static Constraint<String> ulidString() {
        return require("ulid-string", "value must be a valid ULID",
            value -> ULID_PATTERN.matcher(value.toUpperCase(Locale.ROOT)).matches());
    }

    public static Constraint<String> jsonString() {
        return require("json-string", "value must be valid JSON", RefinedSupport::isValidJson);
    }

    public static Constraint<String> cidrV4String() {
        return require("cidr-v4-string", "value must be a valid CIDR v4 notation",
            value -> CIDR_V4_PATTERN.matcher(value).matches());
    }

    public static Constraint<String> cidrV6String() {
        return parsedString("cidr-v6-string", "value must be a valid CIDR v6 notation", value -> {
            int lastSlash = value.lastIndexOf('/');
            if (lastSlash < 0) {
                throw new IllegalArgumentException("missing prefix length");
            }
            String ipPart = value.substring(0, lastSlash);
            int prefix = Integer.parseInt(value.substring(lastSlash + 1));
            if (prefix < 0 || prefix > 128) {
                throw new IllegalArgumentException("prefix out of range");
            }
            if (!ipPart.contains(":")) {
                throw new IllegalArgumentException("not IPv6");
            }
            InetAddress.getByName(ipPart);
        });
    }

    public static Constraint<String> macAddressString() {
        return require("mac-address-string", "value must be a valid MAC address",
            value -> MAC_COLON.matcher(value).matches()
                || MAC_DASH.matcher(value).matches()
                || MAC_DOT.matcher(value).matches());
    }

    public static Constraint<String> semVerString() {
        return require("semver-string", "value must be a valid semantic version",
            value -> SEMVER_PATTERN.matcher(value).matches());
    }

    public static Constraint<String> creditCardString() {
        return require("credit-card-string", "value must be a valid credit card number",
            RefinedSupport::isValidCreditCard);
    }

    public static Constraint<String> isbnString() {
        return require("isbn-string", "value must be a valid ISBN",
            RefinedSupport::isValidIsbn);
    }

    public static Constraint<String> hostnameString() {
        return require("hostname-string", "value must be a valid hostname",
            RefinedSupport::isValidHostname);
    }

    public static Constraint<String> jwtString() {
        return require("jwt-string", "value must be a valid JWT", RefinedSupport::isValidJwt);
    }

    public static Constraint<String> iso8601DateString() {
        return parsedString("iso8601-date-string", "value must be a valid ISO 8601 date",
            value -> LocalDate.parse(value));
    }

    public static Constraint<String> iso8601TimeString() {
        return parsedString("iso8601-time-string", "value must be a valid ISO 8601 time",
            value -> LocalTime.parse(value));
    }

    public static Constraint<String> iso8601DateTimeString() {
        return parsedString("iso8601-datetime-string", "value must be a valid ISO 8601 date-time",
            value -> DateTimeFormatter.ISO_DATE_TIME.parse(value));
    }

    public static Constraint<String> iso8601DurationString() {
        return parsedString("iso8601-duration-string", "value must be a valid ISO 8601 duration", Duration::parse);
    }

    public static Constraint<String> iso8601PeriodString() {
        return parsedString("iso8601-period-string", "value must be a valid ISO 8601 period", Period::parse);
    }

    public static Constraint<String> timeZoneIdString() {
        return parsedString("time-zone-id-string", "value must be a valid time zone id", ZoneId::of);
    }

    public static Constraint<Character> digitChar() {
        return require("digit-char", "value must be a digit", Character::isDigit);
    }

    public static Constraint<Character> letterChar() {
        return require("letter-char", "value must be a letter", Character::isLetter);
    }

    public static Constraint<Character> letterOrDigitChar() {
        return require("letter-or-digit-char", "value must be a letter or digit", Character::isLetterOrDigit);
    }

    public static Constraint<Character> lowerCaseChar() {
        return require("lower-case-char", "value must be lower-case", Character::isLowerCase);
    }

    public static Constraint<Character> upperCaseChar() {
        return require("upper-case-char", "value must be upper-case", Character::isUpperCase);
    }

    public static Constraint<Character> whitespaceChar() {
        return require("whitespace-char", "value must be whitespace", Character::isWhitespace);
    }

    public static Constraint<Character> specialChar() {
        return require("special-char", "value must be a special character",
            value -> !Character.isLetter(value) && !Character.isDigit(value)
                && !Character.isWhitespace(value) && !Character.isSpaceChar(value));
    }

    public static Constraint<Boolean> trueValue() {
        return require("true-value", "value must be true", Boolean.TRUE::equals);
    }

    public static Constraint<Boolean> falseValue() {
        return require("false-value", "value must be false", Boolean.FALSE::equals);
    }

    public static Constraint<List<Boolean>> andBooleanList() {
        return require("and", "all boolean values must be true", values -> values.stream().allMatch(Boolean.TRUE::equals));
    }

    public static Constraint<List<Boolean>> orBooleanList() {
        return require("or", "at least one boolean value must be true", values -> values.stream().anyMatch(Boolean.TRUE::equals));
    }

    public static Constraint<List<Boolean>> xorBooleanList() {
        return require("xor", "an odd number of boolean values must be true", values -> values.stream().filter(Boolean.TRUE::equals).count() % 2L == 1L);
    }

    public static Constraint<List<Boolean>> nandBooleanList() {
        return require("nand", "not all boolean values may be true", values -> values.stream().anyMatch(Boolean.FALSE::equals));
    }

    public static Constraint<List<Boolean>> norBooleanList() {
        return require("nor", "no boolean value may be true", values -> values.stream().noneMatch(Boolean.TRUE::equals));
    }

    public static Constraint<List<Boolean>> oneOfBooleanList() {
        return require("one-of", "exactly one boolean value must be true", values -> values.stream().filter(Boolean.TRUE::equals).count() == 1L);
    }

    public static <T> Constraint<Collection<T>> nonEmptyCollection(String code, String message) {
        return value -> value != null && !value.isEmpty()
            ? Validation.valid(value)
            : Validation.invalid(Violation.of(code, message));
    }

    /**
     * Validates and snapshots a non-empty list while preserving failure causes.
     *
     * @param value source list
     * @param <T> element type
     * @return invalid result for empty/null/invalid content, otherwise immutable snapshot
     */
    public static <T> Validation<Violation, List<T>> nonEmptyListSnapshot(List<T> value) {
        return validateLinearSnapshot(value, "non-empty-list", "list");
    }

    public static <T> Validation<Violation, List<T>> nonEmptyListStreamSnapshot(Stream<T> value) {
        return validateLinearStreamSnapshot(value, "non-empty-list", "list");
    }

    /**
     * Validates and snapshots a non-empty set while preserving failure causes.
     *
     * @param value source set
     * @param <T> element type
     * @return invalid result for empty/null/invalid content, otherwise immutable snapshot
     */
    public static <T> Validation<Violation, Set<T>> nonEmptySetSnapshot(Set<T> value) {
        return validateSetSnapshot(value, "non-empty-set", "set");
    }

    public static <T> Validation<Violation, Set<T>> nonEmptySetStreamSnapshot(Stream<T> value) {
        Validation<Violation, List<T>> linear = validateLinearStreamSnapshot(value, "non-empty-set", "set");
        if (linear.isInvalid()) {
            return Validation.invalid(linear.getError());
        }
        return validateSetSnapshot(new LinkedHashSet<T>(linear.get()), "non-empty-set", "set");
    }

    /**
     * Validates and snapshots a non-empty map while preserving failure causes.
     *
     * @param value source map
     * @param <K> key type
     * @param <V> value type
     * @return invalid result for empty/null/invalid content, otherwise immutable snapshot
     */
    public static <K, V> Validation<Violation, Map<K, V>> nonEmptyMapSnapshot(Map<K, V> value) {
        return validateMapSnapshot(value, "non-empty-map", "map");
    }

    public static <K, V> Validation<Violation, Map<K, V>> nonEmptyMapEntryStreamSnapshot(
        Stream<Map.Entry<K, V>> value
    ) {
        return validateMapEntryStreamSnapshot(value, "non-empty-map", "map");
    }

    /**
     * Validates and snapshots a non-empty queue while preserving failure causes.
     *
     * @param value source queue
     * @param <T> element type
     * @return invalid result for empty/null/invalid content, otherwise immutable snapshot
     */
    public static <T> Validation<Violation, List<T>> nonEmptyQueueSnapshot(Queue<T> value) {
        return validateLinearSnapshot(value, "non-empty-queue", "queue");
    }

    public static <T> Validation<Violation, List<T>> nonEmptyQueueStreamSnapshot(Stream<T> value) {
        return validateLinearStreamSnapshot(value, "non-empty-queue", "queue");
    }

    /**
     * Validates and snapshots a non-empty deque while preserving failure causes.
     *
     * @param value source deque
     * @param <T> element type
     * @return invalid result for empty/null/invalid content, otherwise immutable snapshot
     */
    public static <T> Validation<Violation, List<T>> nonEmptyDequeSnapshot(Deque<T> value) {
        return validateLinearSnapshot(value, "non-empty-deque", "deque");
    }

    public static <T> Validation<Violation, List<T>> nonEmptyDequeStreamSnapshot(Stream<T> value) {
        return validateLinearStreamSnapshot(value, "non-empty-deque", "deque");
    }

    /**
     * Validates and snapshots a non-empty iterable while preserving failure causes.
     *
     * @param value source iterable
     * @param <T> element type
     * @return invalid result for empty/null/invalid content, otherwise immutable snapshot
     */
    public static <T> Validation<Violation, List<T>> nonEmptyIterableSnapshot(Iterable<T> value) {
        return validateLinearSnapshot(value, "non-empty-iterable", "iterable");
    }

    public static <T> Validation<Violation, List<T>> nonEmptyIterableStreamSnapshot(Stream<T> value) {
        return validateLinearStreamSnapshot(value, "non-empty-iterable", "iterable");
    }

    /**
     * Validates and snapshots a non-empty sorted set while preserving failure causes.
     *
     * @param value source sorted set
     * @param <T> element type
     * @return invalid result for empty/null/invalid content, otherwise immutable snapshot
     */
    public static <T> Validation<Violation, SortedSet<T>> nonEmptySortedSetSnapshot(SortedSet<T> value) {
        return validateSortedSetSnapshot(value, "non-empty-sorted-set", "sorted-set");
    }

    public static <T extends Comparable<? super T>> Validation<Violation, SortedSet<T>> nonEmptySortedSetStreamSnapshot(
        Stream<T> value
    ) {
        return validateSortedSetStreamSnapshot(value, null, "non-empty-sorted-set", "sorted-set");
    }

    public static <T> Validation<Violation, SortedSet<T>> nonEmptySortedSetStreamSnapshot(
        Stream<T> value,
        Comparator<? super T> comparator
    ) {
        return validateSortedSetStreamSnapshot(value, comparator, "non-empty-sorted-set", "sorted-set");
    }

    /**
     * Validates and snapshots a non-empty sorted map while preserving failure causes.
     *
     * @param value source sorted map
     * @param <K> key type
     * @param <V> value type
     * @return invalid result for empty/null/invalid content, otherwise immutable snapshot
     */
    public static <K, V> Validation<Violation, SortedMap<K, V>> nonEmptySortedMapSnapshot(SortedMap<K, V> value) {
        return validateSortedMapSnapshot(value, "non-empty-sorted-map", "sorted-map");
    }

    public static <K extends Comparable<? super K>, V> Validation<Violation, SortedMap<K, V>> nonEmptySortedMapEntryStreamSnapshot(
        Stream<Map.Entry<K, V>> value
    ) {
        return validateSortedMapEntryStreamSnapshot(value, null, "non-empty-sorted-map", "sorted-map");
    }

    public static <K, V> Validation<Violation, SortedMap<K, V>> nonEmptySortedMapEntryStreamSnapshot(
        Stream<Map.Entry<K, V>> value,
        Comparator<? super K> comparator
    ) {
        return validateSortedMapEntryStreamSnapshot(value, comparator, "non-empty-sorted-map", "sorted-map");
    }

    /**
     * Validates and snapshots a non-empty navigable set while preserving failure causes.
     *
     * @param value source navigable set
     * @param <T> element type
     * @return invalid result for empty/null/invalid content, otherwise immutable snapshot
     */
    public static <T> Validation<Violation, NavigableSet<T>> nonEmptyNavigableSetSnapshot(NavigableSet<T> value) {
        return validateNavigableSetSnapshot(value, "non-empty-navigable-set", "navigable-set");
    }

    public static <T extends Comparable<? super T>> Validation<Violation, NavigableSet<T>> nonEmptyNavigableSetStreamSnapshot(
        Stream<T> value
    ) {
        return validateNavigableSetStreamSnapshot(value, null, "non-empty-navigable-set", "navigable-set");
    }

    public static <T> Validation<Violation, NavigableSet<T>> nonEmptyNavigableSetStreamSnapshot(
        Stream<T> value,
        Comparator<? super T> comparator
    ) {
        return validateNavigableSetStreamSnapshot(value, comparator, "non-empty-navigable-set", "navigable-set");
    }

    /**
     * Validates and snapshots a non-empty navigable map while preserving failure causes.
     *
     * @param value source navigable map
     * @param <K> key type
     * @param <V> value type
     * @return invalid result for empty/null/invalid content, otherwise immutable snapshot
     */
    public static <K, V> Validation<Violation, NavigableMap<K, V>> nonEmptyNavigableMapSnapshot(NavigableMap<K, V> value) {
        return validateNavigableMapSnapshot(value, "non-empty-navigable-map", "navigable-map");
    }

    public static <K extends Comparable<? super K>, V> Validation<Violation, NavigableMap<K, V>> nonEmptyNavigableMapEntryStreamSnapshot(
        Stream<Map.Entry<K, V>> value
    ) {
        return validateNavigableMapEntryStreamSnapshot(value, null, "non-empty-navigable-map", "navigable-map");
    }

    public static <K, V> Validation<Violation, NavigableMap<K, V>> nonEmptyNavigableMapEntryStreamSnapshot(
        Stream<Map.Entry<K, V>> value,
        Comparator<? super K> comparator
    ) {
        return validateNavigableMapEntryStreamSnapshot(value, comparator, "non-empty-navigable-map", "navigable-map");
    }

    public static <T> List<T> copyIterableToList(Iterable<T> value) {
        ArrayList<T> copy = new ArrayList<>();
        for (T element : value) {
            copy.add(Objects.requireNonNull(element, "element"));
        }
        return Collections.unmodifiableList(copy);
    }

    public static <T> List<T> copyQueueToList(Queue<T> value) {
        return copyToUnmodifiableList(value);
    }

    public static <T> SortedSet<T> copySortedSet(SortedSet<T> value) {
        TreeSet<T> copy = value.comparator() == null ? new TreeSet<>() : new TreeSet<>(value.comparator());
        for (T element : value) {
            copy.add(Objects.requireNonNull(element, "element"));
        }
        return Collections.unmodifiableSortedSet(copy);
    }

    public static <K, V> SortedMap<K, V> copySortedMap(SortedMap<K, V> value) {
        TreeMap<K, V> copy = value.comparator() == null ? new TreeMap<>() : new TreeMap<>(value.comparator());
        for (Map.Entry<K, V> entry : value.entrySet()) {
            copy.put(Objects.requireNonNull(entry.getKey(), "key"), Objects.requireNonNull(entry.getValue(), "value"));
        }
        return Collections.unmodifiableSortedMap(copy);
    }

    public static <T> NavigableSet<T> copyNavigableSet(NavigableSet<T> value) {
        TreeSet<T> copy = value.comparator() == null ? new TreeSet<>() : new TreeSet<>(value.comparator());
        for (T element : value) {
            copy.add(Objects.requireNonNull(element, "element"));
        }
        return Collections.unmodifiableNavigableSet(copy);
    }

    public static <K, V> NavigableMap<K, V> copyNavigableMap(NavigableMap<K, V> value) {
        TreeMap<K, V> copy = value.comparator() == null ? new TreeMap<>() : new TreeMap<>(value.comparator());
        for (Map.Entry<K, V> entry : value.entrySet()) {
            copy.put(Objects.requireNonNull(entry.getKey(), "key"), Objects.requireNonNull(entry.getValue(), "value"));
        }
        return Collections.unmodifiableNavigableMap(copy);
    }

    public static <T> Set<T> copyLinkedSet(Set<T> value) {
        return copyToUnmodifiableSet(value);
    }

    public static <K, V> Map<K, V> copyLinkedMap(Map<K, V> value) {
        return copyToUnmodifiableMap(value);
    }

    private static boolean isValidHostname(String value) {
        if (value.isEmpty() || value.length() > 253) {
            return false;
        }
        String[] labels = value.split("\\.", -1);
        for (String label : labels) {
            int len = label.length();
            if (len == 0 || len > 63) {
                return false;
            }
            if (label.charAt(0) == '-' || label.charAt(len - 1) == '-') {
                return false;
            }
            for (int i = 0; i < len; i++) {
                char ch = label.charAt(i);
                if (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')
                    || (ch >= '0' && ch <= '9') || ch == '-')) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValidJwt(String value) {
        int first = value.indexOf('.');
        if (first < 1) {
            return false;
        }
        int second = value.indexOf('.', first + 1);
        if (second < 0 || second == first + 1) {
            return false;
        }
        if (value.indexOf('.', second + 1) >= 0) {
            return false;
        }
        String header = value.substring(0, first);
        String payload = value.substring(first + 1, second);
        String signature = value.substring(second + 1);
        return BASE64_URL_PART.matcher(header).matches()
            && BASE64_URL_PART.matcher(payload).matches()
            && BASE64_URL_PART.matcher(signature).matches();
    }

    private static boolean isValidCreditCard(String value) {
        String digits = value.replace(" ", "").replace("-", "");
        int len = digits.length();
        if (len < 13 || len > 19) {
            return false;
        }
        int sum = 0;
        boolean alternate = false;
        for (int i = len - 1; i >= 0; i--) {
            char ch = digits.charAt(i);
            if (ch < '0' || ch > '9') {
                return false;
            }
            int n = ch - '0';
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return sum % 10 == 0;
    }

    private static boolean isValidIsbn(String value) {
        String clean = value.replace("-", "").replace(" ", "");
        int len = clean.length();
        if (len == 10) {
            return isValidIsbn10(clean);
        }
        if (len == 13) {
            return isValidIsbn13(clean);
        }
        return false;
    }

    private static boolean isValidIsbn10(String isbn) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            char ch = isbn.charAt(i);
            if (ch < '0' || ch > '9') {
                return false;
            }
            sum += (ch - '0') * (10 - i);
        }
        char last = isbn.charAt(9);
        if (last == 'X' || last == 'x') {
            sum += 10;
        } else if (last >= '0' && last <= '9') {
            sum += (last - '0');
        } else {
            return false;
        }
        return sum % 11 == 0;
    }

    private static boolean isValidIsbn13(String isbn) {
        int sum = 0;
        for (int i = 0; i < 13; i++) {
            char ch = isbn.charAt(i);
            if (ch < '0' || ch > '9') {
                return false;
            }
            sum += (ch - '0') * (i % 2 == 0 ? 1 : 3);
        }
        return sum % 10 == 0;
    }

    private static boolean isValidJson(String value) {
        if (value.isEmpty()) {
            return false;
        }
        int[] pos = {0};
        skipWhitespace(value, pos);
        if (pos[0] >= value.length()) {
            return false;
        }
        if (!parseJsonValue(value, pos)) {
            return false;
        }
        skipWhitespace(value, pos);
        return pos[0] == value.length();
    }

    private static boolean parseJsonValue(String json, int[] pos) {
        if (pos[0] >= json.length()) {
            return false;
        }
        char ch = json.charAt(pos[0]);
        if (ch == '"') {
            return parseJsonString(json, pos);
        }
        if (ch == '{') {
            return parseJsonObject(json, pos);
        }
        if (ch == '[') {
            return parseJsonArray(json, pos);
        }
        if (ch == 't') {
            return parseJsonLiteral(json, pos, "true");
        }
        if (ch == 'f') {
            return parseJsonLiteral(json, pos, "false");
        }
        if (ch == 'n') {
            return parseJsonLiteral(json, pos, "null");
        }
        if (ch == '-' || (ch >= '0' && ch <= '9')) {
            return parseJsonNumber(json, pos);
        }
        return false;
    }

    private static boolean parseJsonObject(String json, int[] pos) {
        pos[0]++;
        skipWhitespace(json, pos);
        if (pos[0] >= json.length()) {
            return false;
        }
        if (json.charAt(pos[0]) == '}') {
            pos[0]++;
            return true;
        }
        while (true) {
            skipWhitespace(json, pos);
            if (pos[0] >= json.length() || json.charAt(pos[0]) != '"') {
                return false;
            }
            if (!parseJsonString(json, pos)) {
                return false;
            }
            skipWhitespace(json, pos);
            if (pos[0] >= json.length() || json.charAt(pos[0]) != ':') {
                return false;
            }
            pos[0]++;
            skipWhitespace(json, pos);
            if (!parseJsonValue(json, pos)) {
                return false;
            }
            skipWhitespace(json, pos);
            if (pos[0] >= json.length()) {
                return false;
            }
            if (json.charAt(pos[0]) == '}') {
                pos[0]++;
                return true;
            }
            if (json.charAt(pos[0]) != ',') {
                return false;
            }
            pos[0]++;
        }
    }

    private static boolean parseJsonArray(String json, int[] pos) {
        pos[0]++;
        skipWhitespace(json, pos);
        if (pos[0] >= json.length()) {
            return false;
        }
        if (json.charAt(pos[0]) == ']') {
            pos[0]++;
            return true;
        }
        while (true) {
            skipWhitespace(json, pos);
            if (!parseJsonValue(json, pos)) {
                return false;
            }
            skipWhitespace(json, pos);
            if (pos[0] >= json.length()) {
                return false;
            }
            if (json.charAt(pos[0]) == ']') {
                pos[0]++;
                return true;
            }
            if (json.charAt(pos[0]) != ',') {
                return false;
            }
            pos[0]++;
        }
    }

    private static boolean parseJsonString(String json, int[] pos) {
        pos[0]++;
        while (pos[0] < json.length()) {
            char ch = json.charAt(pos[0]);
            if (ch == '"') {
                pos[0]++;
                return true;
            }
            if (ch == '\\') {
                pos[0]++;
                if (pos[0] >= json.length()) {
                    return false;
                }
                char esc = json.charAt(pos[0]);
                if (esc == 'u') {
                    if (pos[0] + 4 >= json.length()) {
                        return false;
                    }
                    for (int i = 1; i <= 4; i++) {
                        char hex = json.charAt(pos[0] + i);
                        if (!((hex >= '0' && hex <= '9') || (hex >= 'a' && hex <= 'f') || (hex >= 'A' && hex <= 'F'))) {
                            return false;
                        }
                    }
                    pos[0] += 4;
                } else if (esc != '"' && esc != '\\' && esc != '/' && esc != 'b'
                    && esc != 'f' && esc != 'n' && esc != 'r' && esc != 't') {
                    return false;
                }
            } else if (ch < 0x20) {
                return false;
            }
            pos[0]++;
        }
        return false;
    }

    private static boolean parseJsonNumber(String json, int[] pos) {
        if (json.charAt(pos[0]) == '-') {
            pos[0]++;
        }
        if (pos[0] >= json.length()) {
            return false;
        }
        if (json.charAt(pos[0]) == '0') {
            pos[0]++;
        } else if (json.charAt(pos[0]) >= '1' && json.charAt(pos[0]) <= '9') {
            pos[0]++;
            while (pos[0] < json.length() && json.charAt(pos[0]) >= '0' && json.charAt(pos[0]) <= '9') {
                pos[0]++;
            }
        } else {
            return false;
        }
        if (pos[0] < json.length() && json.charAt(pos[0]) == '.') {
            pos[0]++;
            if (pos[0] >= json.length() || json.charAt(pos[0]) < '0' || json.charAt(pos[0]) > '9') {
                return false;
            }
            while (pos[0] < json.length() && json.charAt(pos[0]) >= '0' && json.charAt(pos[0]) <= '9') {
                pos[0]++;
            }
        }
        if (pos[0] < json.length() && (json.charAt(pos[0]) == 'e' || json.charAt(pos[0]) == 'E')) {
            pos[0]++;
            if (pos[0] < json.length() && (json.charAt(pos[0]) == '+' || json.charAt(pos[0]) == '-')) {
                pos[0]++;
            }
            if (pos[0] >= json.length() || json.charAt(pos[0]) < '0' || json.charAt(pos[0]) > '9') {
                return false;
            }
            while (pos[0] < json.length() && json.charAt(pos[0]) >= '0' && json.charAt(pos[0]) <= '9') {
                pos[0]++;
            }
        }
        return true;
    }

    private static boolean parseJsonLiteral(String json, int[] pos, String literal) {
        if (pos[0] + literal.length() > json.length()) {
            return false;
        }
        for (int i = 0; i < literal.length(); i++) {
            if (json.charAt(pos[0] + i) != literal.charAt(i)) {
                return false;
            }
        }
        pos[0] += literal.length();
        return true;
    }

    private static void skipWhitespace(String json, int[] pos) {
        while (pos[0] < json.length()) {
            char ch = json.charAt(pos[0]);
            if (ch != ' ' && ch != '\t' && ch != '\n' && ch != '\r') {
                break;
            }
            pos[0]++;
        }
    }

    public static boolean isBlank(String value) {
        if (value == null) {
            return true;
        }
        return trimmedStartIndex(value) == value.length();
    }

    public static <T> List<T> copyToUnmodifiableList(Collection<? extends T> value) {
        ArrayList<T> copy = new ArrayList<T>(value.size());
        for (T element : value) {
            copy.add(Objects.requireNonNull(element, "element"));
        }
        return Collections.unmodifiableList(copy);
    }

    public static <T> Set<T> copyToUnmodifiableSet(Collection<? extends T> value) {
        LinkedHashSet<T> copy = new LinkedHashSet<T>(Math.max(1, value.size()));
        for (T element : value) {
            copy.add(Objects.requireNonNull(element, "element"));
        }
        return Collections.unmodifiableSet(copy);
    }

    public static <K, V> Map<K, V> copyToUnmodifiableMap(Map<? extends K, ? extends V> value) {
        LinkedHashMap<K, V> copy = new LinkedHashMap<K, V>(Math.max(1, value.size()));
        for (Map.Entry<? extends K, ? extends V> entry : value.entrySet()) {
            copy.put(
                Objects.requireNonNull(entry.getKey(), "key"),
                Objects.requireNonNull(entry.getValue(), "value")
            );
        }
        return Collections.unmodifiableMap(copy);
    }

    public static <T> List<T> copyListSlice(List<T> value, int fromIndex, int toIndex) {
        return copyToUnmodifiableList(value.subList(fromIndex, toIndex));
    }
}
