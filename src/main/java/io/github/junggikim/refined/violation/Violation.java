package io.github.junggikim.refined.violation;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Structured validation failure containing a machine-friendly code and human-readable message.
 */
public final class Violation implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String code;
    private final String message;
    private final Map<String, Object> metadata;

    /**
     * Creates a violation with immutable metadata.
     *
     * @param code stable machine-readable code
     * @param message human-readable explanation
     * @param metadata optional metadata copied into an immutable map
     */
    public Violation(@NotNull String code, @NotNull String message, @Nullable Map<String, Object> metadata) {
        this.code = Objects.requireNonNull(code, "code");
        this.message = Objects.requireNonNull(message, "message");
        this.metadata = copyMetadata(metadata);
    }

    /**
     * Creates a violation without metadata.
     *
     * @param code stable machine-readable code
     * @param message human-readable explanation
     * @return violation without metadata
     */
    @NotNull
    public static Violation of(@NotNull String code, @NotNull String message) {
        return new Violation(code, message, Collections.<String, Object>emptyMap());
    }

    /**
     * Returns a new violation with one more metadata entry.
     *
     * @param key metadata key
     * @param value metadata value
     * @return new violation instance
     */
    @NotNull
    public Violation withMetadata(@NotNull String key, @NotNull Object value) {
        LinkedHashMap<String, Object> next = new LinkedHashMap<>(metadata);
        next.put(key, value);
        return new Violation(code, message, next);
    }

    /**
     * Returns the stable machine-readable violation code.
     *
     * @return violation code
     */
    @NotNull
    public String code() {
        return code;
    }

    /**
     * Returns the human-readable failure message.
     *
     * @return violation message
     */
    @NotNull
    public String message() {
        return message;
    }

    /**
     * Returns immutable violation metadata.
     *
     * @return immutable metadata map
     */
    @NotNull
    public Map<String, Object> metadata() {
        return Collections.unmodifiableMap(new LinkedHashMap<String, Object>(metadata));
    }

    /**
     * Kotlin-friendly bean getter bridge for {@link #code()}.
     *
     * @return violation code
     */
    @NotNull
    public String getCode() {
        return code();
    }

    /**
     * Kotlin-friendly bean getter bridge for {@link #message()}.
     *
     * @return violation message
     */
    @NotNull
    public String getMessage() {
        return message();
    }

    /**
     * Kotlin-friendly bean getter bridge for {@link #metadata()}.
     *
     * @return immutable metadata map
     */
    @NotNull
    public Map<String, Object> getMetadata() {
        return metadata();
    }

    @NotNull
    private static Map<String, Object> copyMetadata(@Nullable Map<String, Object> source) {
        LinkedHashMap<String, Object> copy = new LinkedHashMap<>();
        if (source != null) {
            for (Map.Entry<String, Object> entry : source.entrySet()) {
                copy.put(Objects.requireNonNull(entry.getKey(), "metadata key"), Objects.requireNonNull(entry.getValue(), "metadata value"));
            }
        }
        return Collections.unmodifiableMap(copy);
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Violation)) {
            return false;
        }
        Violation violation = (Violation) other;
        return code.equals(violation.code)
            && message.equals(violation.message)
            && metadata.equals(violation.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, metadata);
    }

    @Override
    @NotNull
    public String toString() {
        return "Violation[code=" + code + ", message=" + message + ", metadata=" + metadata + "]";
    }
}
