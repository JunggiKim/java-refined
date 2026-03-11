package io.github.junggikim.refined.control;

import static io.github.junggikim.refined.support.TestCollections.listOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.junggikim.refined.validation.Validated;
import java.io.IOException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class ControlTypesTest {

    @Nested
    class OptionTest {
        @Test
        void someAndNoneSupportMappingFilteringAndDefaults() {
            Option<Integer> some = Option.some(1);
            Option<Integer> none = Option.none();

            assertTrue(some.isDefined());
            assertFalse(some.isEmpty());
            assertTrue(none.isEmpty());
            assertEquals(2, some.map(it -> it + 1).get());
            assertTrue(some.map(it -> null).isEmpty());
            assertTrue(some.flatMap(it -> Option.some(it + 2)).isDefined());
            assertTrue(some.filter(it -> it == 1).isDefined());
            assertTrue(some.filter(it -> it > 10).isEmpty());
            assertTrue(none.map(it -> it + 1).isEmpty());
            assertTrue(none.flatMap(it -> Option.some(it + 1)).isEmpty());
            assertSame(none, none.filter(it -> true));
            assertEquals(1, some.getOrElse(9));
            assertEquals(1, some.getOrElseGet(() -> 10));
            assertEquals(9, none.getOrElse(9));
            assertEquals(10, none.getOrElseGet(() -> 10));
            assertTrue(Option.ofNullable(null).isEmpty());
            assertEquals("None", none.toString());
            assertThrows(java.util.NoSuchElementException.class, none::get);
        }

        @Test
        void someImplementsEqualityHashCodeToStringAndNullGuards() {
            Option<Integer> some = Option.some(1);

            assertTrue(some.equals(some));
            assertEquals(some, Option.some(1));
            assertEquals(some.hashCode(), Option.some(1).hashCode());
            assertNotEquals(some, Option.some(2));
            assertNotEquals(some, Option.none());
            assertNotEquals(some, "some");
            assertEquals("Some[value=1]", some.toString());
            assertThrows(NullPointerException.class, () -> Option.some(null));
            assertThrows(NullPointerException.class, () -> some.flatMap(value -> null));
        }

        @RepeatedTest(3)
        void noneIsSingleton() {
            assertSame(Option.none(), Option.none());
        }
    }

    @Nested
    class EitherTest {
        @Test
        void eitherSupportsRightAndLeftOperations() {
            Either<String, Integer> right = Either.right(1);
            Either<String, Integer> left = Either.left("x");
            Integer mapped = right.map(it -> it + 1).fold(l -> -1, r -> r);
            Integer flatMapped = right.flatMap(it -> Either.right(it + 2)).fold(l -> -1, r -> r);
            Integer mapLeftOnRight = right.mapLeft(it -> it + "!").fold(l -> -1, r -> r);

            assertTrue(right.isRight());
            assertTrue(left.isLeft());
            assertFalse(right.isLeft());
            assertEquals(2, mapped);
            assertEquals("x!", left.mapLeft(it -> it + "!").fold(l -> l, r -> "bad"));
            assertEquals(3, flatMapped);
            assertEquals("L", right.swap().fold(l -> "L", r -> "R"));
            assertEquals("R", left.swap().fold(l -> "L", r -> "R"));
            assertEquals("x", left.map(it -> it + 1).fold(l -> l, r -> "bad"));
            assertEquals("x", left.flatMap(it -> Either.right(it + 1)).fold(l -> l, r -> "bad"));
            assertEquals(1, mapLeftOnRight);
        }

        @Test
        void eitherImplementsEqualityHashCodeToStringAndNullGuards() {
            Either<String, Integer> left = Either.left("x");
            Either<String, Integer> right = Either.right(1);

            assertTrue(left.equals(left));
            assertEquals(left, Either.left("x"));
            assertEquals(left.hashCode(), Either.left("x").hashCode());
            assertNotEquals(left, Either.left("y"));
            assertNotEquals(left, right);
            assertNotEquals(left, "left");
            assertEquals("Left[value=x]", left.toString());

            assertTrue(right.equals(right));
            assertEquals(right, Either.right(1));
            assertEquals(right.hashCode(), Either.right(1).hashCode());
            assertNotEquals(right, Either.right(2));
            assertNotEquals(right, "right");
            assertEquals("Right[value=1]", right.toString());

            assertThrows(NullPointerException.class, () -> Either.left(null));
            assertThrows(NullPointerException.class, () -> Either.right(null));
            assertThrows(NullPointerException.class, () -> right.flatMap(value -> null));
        }
    }

    @Nested
    class TryTest {
        @Test
        void trySupportsSuccessFailureMapFlatMapAndRecover() {
            Try<Integer> success = Try.of(() -> 1);
            Try<Integer> failure = Try.of(() -> {
                throw new IOException("boom");
            });

            assertTrue(success.isSuccess());
            assertFalse(success.isFailure());
            assertTrue(failure.isFailure());
            assertEquals(2, success.map(it -> it + 1).get());
            assertEquals(3, success.flatMap(it -> Try.success(it + 2)).get());
            assertEquals(99, failure.recover(error -> 99).get());
            assertSame(success, success.recover(error -> 99));
            assertThrows(RuntimeException.class, failure::get);
            assertEquals("boom", failure.getFailure().getMessage());
            assertThrows(IllegalStateException.class, success::getFailure);
            assertEquals("boom", failure.map(it -> it + 1).getFailure().getMessage());
            assertEquals("boom", failure.flatMap(it -> Try.success(it + 1)).getFailure().getMessage());
        }

        @Test
        void failureGetRethrowsRuntimeExceptionAsIs() {
            RuntimeException boom = new RuntimeException("boom");
            Try<Integer> failure = Try.failure(boom);

            RuntimeException thrown = assertThrows(RuntimeException.class, failure::get);
            assertSame(boom, thrown);
        }

        @Test
        void failureGetRethrowsErrorAsIs() {
            AssertionError boom = new AssertionError("boom");
            Try<Integer> failure = Try.failure(boom);

            AssertionError thrown = assertThrows(AssertionError.class, failure::get);
            assertSame(boom, thrown);
        }

        @Test
        void tryOfDoesNotCaptureErrors() {
            AssertionError boom = new AssertionError("boom");

            AssertionError thrown = assertThrows(AssertionError.class, () -> Try.of(() -> {
                throw boom;
            }));
            assertSame(boom, thrown);
        }

        @Test
        void tryImplementsEqualityHashCodeToStringAndNullGuards() {
            IOException boom = new IOException("boom");
            Try<Integer> success = Try.success(1);
            Try<Integer> failure = Try.failure(boom);

            assertTrue(success.equals(success));
            assertEquals(success, Try.success(1));
            assertEquals(success.hashCode(), Try.success(1).hashCode());
            assertNotEquals(success, Try.success(2));
            assertNotEquals(success, failure);
            assertNotEquals(success, "success");
            assertEquals("Success[value=1]", success.toString());

            assertTrue(failure.equals(failure));
            assertEquals(failure, Try.failure(boom));
            assertEquals(failure.hashCode(), Try.failure(boom).hashCode());
            assertNotEquals(failure, Try.failure(new IOException("boom")));
            assertNotEquals(failure, "failure");
            assertEquals("Failure[throwable=java.io.IOException: boom]", failure.toString());

            assertThrows(NullPointerException.class, () -> Try.success(null));
            assertThrows(NullPointerException.class, () -> Try.failure(null));
            assertThrows(NullPointerException.class, () -> success.flatMap(value -> null));
            assertTrue(Try.of(() -> null).isFailure());
        }
    }

    @Nested
    class IorTest {
        @Test
        void iorSupportsLeftRightAndBoth() {
            assertEquals("L", Ior.<String, Integer>left("x").fold(l -> "L", r -> "R", (l, r) -> "B"));
            assertEquals("R", Ior.<String, Integer>right(1).fold(l -> "L", r -> "R", (l, r) -> "B"));
            assertEquals("B", Ior.<String, Integer>both("x", 1).fold(l -> "L", r -> "R", (l, r) -> "B"));
        }

        @Test
        void iorImplementsEqualityHashCodeToStringAndNullGuards() {
            Ior<String, Integer> left = Ior.left("x");
            Ior<String, Integer> right = Ior.right(1);
            Ior<String, Integer> both = Ior.both("x", 1);

            assertTrue(left.equals(left));
            assertEquals(left, Ior.left("x"));
            assertEquals(left.hashCode(), Ior.left("x").hashCode());
            assertNotEquals(left, Ior.left("y"));
            assertNotEquals(left, right);
            assertNotEquals(left, "left");
            assertEquals("Left[value=x]", left.toString());

            assertTrue(right.equals(right));
            assertEquals(right, Ior.right(1));
            assertEquals(right.hashCode(), Ior.right(1).hashCode());
            assertNotEquals(right, Ior.right(2));
            assertNotEquals(right, "right");
            assertEquals("Right[value=1]", right.toString());

            assertTrue(both.equals(both));
            assertEquals(both, Ior.both("x", 1));
            assertEquals(both.hashCode(), Ior.both("x", 1).hashCode());
            assertNotEquals(both, Ior.both("y", 1));
            assertNotEquals(both, Ior.both("x", 2));
            assertNotEquals(both, "both");
            assertEquals("Both[left=x, right=1]", both.toString());

            assertThrows(NullPointerException.class, () -> Ior.left(null));
            assertThrows(NullPointerException.class, () -> Ior.right(null));
            assertThrows(NullPointerException.class, () -> Ior.both(null, 1));
            assertThrows(NullPointerException.class, () -> Ior.both("x", null));
        }
    }

    @Nested
    class ValidatedControlTest {
        @Test
        void validatedAccumulatesErrors() {
            Validated<String, Integer> left = Validated.invalid(listOf("left"));
            Validated<String, Integer> right = Validated.invalid(listOf("right"));

            assertEquals(listOf("left", "right"), left.zip(right, Integer::sum).getErrors());
            assertEquals(3, Validated.<String, Integer>valid(1).zip(Validated.valid(2), Integer::sum).get());
        }
    }
}
