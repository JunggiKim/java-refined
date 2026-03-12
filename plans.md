# Predicate 정적 팩토리 메서드 추가 — 구현 계획

> 다른 컴퓨터에서 이 파일만 보고 바로 구현 진행이 가능하도록 모든 세부사항을 포함합니다.

---

## Context

현재 71개의 Predicate 클래스는 모두 `new Constructor()` 방식으로만 생성 가능합니다.

**문제점:**
- 파라미터가 없는 30개 클래스는 매번 새 인스턴스를 불필요하게 생성
- 파라미터가 있는 41개 클래스는 `new GreaterThan<>(10)` 같이 `<>` 다이아몬드 타입 추론이 불편

**목표:** 기존 생성자를 그대로 유지하면서(하위 호환), `instance()`/`of()` 정적 팩토리 메서드를 추가합니다.

**변경 범위:**
- 소스 파일 수정: 71개
- 새 테스트 파일: 1개 (`StaticFactoryMethodTest.java`)
- 기존 파일 삭제/변경: 없음 (build.gradle.kts, config 파일 등 변경 없음)

---

## 프로젝트 빌드 환경 (참고)

| 항목 | 값 |
|------|-----|
| Java target | 1.8 (source/target + `--release 8` on JDK 9+) |
| JUnit | 5.12.0 (`junit-bom`) |
| JaCoCo | 0.8.12 — **6개 메트릭 모두 100%** (INSTRUCTION, BRANCH, LINE, COMPLEXITY, METHOD, CLASS) |
| Checkstyle | 10.21.1 — 규칙: UnusedImports, AvoidStarImport, NeedBraces, EmptyBlock, WhitespaceAround, OneStatementPerLine |
| SpotBugs | 6.4.8 — Effort.MAX, Confidence.MEDIUM |
| PMD | 7.10.0 |
| testJava8 | Gradle toolchain으로 Java 8 런타임에서 테스트 실행 |

---

## 전체 파일 목록 (71개)

### Group A: 파라미터 없음 — `instance()` 싱글턴 (30개)

#### Group A-1: 비제네릭 (27개)

##### Boolean (8개)

| # | 클래스 | 파일 경로 | Constraint\<T\> | delegate |
|---|--------|-----------|-----------------|----------|
| 1 | `And` | `src/main/java/io/github/junggikim/refined/predicate/booleanvalue/And.java` | `List<Boolean>` | `RefinedSupport.andBooleanList()` |
| 2 | `Or` | `src/main/java/io/github/junggikim/refined/predicate/booleanvalue/Or.java` | `List<Boolean>` | `RefinedSupport.orBooleanList()` |
| 3 | `Xor` | `src/main/java/io/github/junggikim/refined/predicate/booleanvalue/Xor.java` | `List<Boolean>` | `RefinedSupport.xorBooleanList()` |
| 4 | `Nand` | `src/main/java/io/github/junggikim/refined/predicate/booleanvalue/Nand.java` | `List<Boolean>` | `RefinedSupport.nandBooleanList()` |
| 5 | `Nor` | `src/main/java/io/github/junggikim/refined/predicate/booleanvalue/Nor.java` | `List<Boolean>` | `RefinedSupport.norBooleanList()` |
| 6 | `OneOf` | `src/main/java/io/github/junggikim/refined/predicate/booleanvalue/OneOf.java` | `List<Boolean>` | `RefinedSupport.oneOfBooleanList()` |
| 7 | `TrueValue` | `src/main/java/io/github/junggikim/refined/predicate/booleanvalue/TrueValue.java` | `Boolean` | `RefinedSupport.trueValue()` |
| 8 | `FalseValue` | `src/main/java/io/github/junggikim/refined/predicate/booleanvalue/FalseValue.java` | `Boolean` | `RefinedSupport.falseValue()` |

##### Character (7개)

| # | 클래스 | 파일 경로 | delegate |
|---|--------|-----------|----------|
| 9 | `IsDigitChar` | `src/main/java/io/github/junggikim/refined/predicate/character/IsDigitChar.java` | `RefinedSupport.digitChar()` |
| 10 | `IsLetterChar` | `src/main/java/io/github/junggikim/refined/predicate/character/IsLetterChar.java` | `RefinedSupport.letterChar()` |
| 11 | `IsLetterOrDigitChar` | `src/main/java/io/github/junggikim/refined/predicate/character/IsLetterOrDigitChar.java` | `RefinedSupport.letterOrDigitChar()` |
| 12 | `IsLowerCaseChar` | `src/main/java/io/github/junggikim/refined/predicate/character/IsLowerCaseChar.java` | `RefinedSupport.lowerCaseChar()` |
| 13 | `IsUpperCaseChar` | `src/main/java/io/github/junggikim/refined/predicate/character/IsUpperCaseChar.java` | `RefinedSupport.upperCaseChar()` |
| 14 | `IsWhitespaceChar` | `src/main/java/io/github/junggikim/refined/predicate/character/IsWhitespaceChar.java` | `RefinedSupport.whitespaceChar()` |
| 15 | `IsSpecialChar` | `src/main/java/io/github/junggikim/refined/predicate/character/IsSpecialChar.java` | `RefinedSupport.specialChar()` |

##### Numeric — Parity (6개)

| # | 클래스 | 파일 경로 | Constraint\<T\> | delegate |
|---|--------|-----------|-----------------|----------|
| 16 | `EvenInt` | `src/main/java/io/github/junggikim/refined/predicate/numeric/EvenInt.java` | `Integer` | `RefinedSupport.evenInt()` |
| 17 | `OddInt` | `src/main/java/io/github/junggikim/refined/predicate/numeric/OddInt.java` | `Integer` | `RefinedSupport.oddInt()` |
| 18 | `EvenLong` | `src/main/java/io/github/junggikim/refined/predicate/numeric/EvenLong.java` | `Long` | `RefinedSupport.evenLong()` |
| 19 | `OddLong` | `src/main/java/io/github/junggikim/refined/predicate/numeric/OddLong.java` | `Long` | `RefinedSupport.oddLong()` |
| 20 | `EvenBigInteger` | `src/main/java/io/github/junggikim/refined/predicate/numeric/EvenBigInteger.java` | `BigInteger` | `RefinedSupport.evenBigInteger()` |
| 21 | `OddBigInteger` | `src/main/java/io/github/junggikim/refined/predicate/numeric/OddBigInteger.java` | `BigInteger` | `RefinedSupport.oddBigInteger()` |

##### Numeric — Special (4개)

| # | 클래스 | 파일 경로 | Constraint\<T\> | delegate |
|---|--------|-----------|-----------------|----------|
| 22 | `FiniteFloatPredicate` | `src/main/java/io/github/junggikim/refined/predicate/numeric/FiniteFloatPredicate.java` | `Float` | `RefinedSupport.finiteFloat()` |
| 23 | `FiniteDoublePredicate` | `src/main/java/io/github/junggikim/refined/predicate/numeric/FiniteDoublePredicate.java` | `Double` | `RefinedSupport.finiteDouble()` |
| 24 | `NonNaNFloatPredicate` | `src/main/java/io/github/junggikim/refined/predicate/numeric/NonNaNFloatPredicate.java` | `Float` | `RefinedSupport.nonNaNFloat()` |
| 25 | `NonNaNDoublePredicate` | `src/main/java/io/github/junggikim/refined/predicate/numeric/NonNaNDoublePredicate.java` | `Double` | `RefinedSupport.nonNaNDouble()` |

##### String (2개)

| # | 클래스 | 파일 경로 | delegate |
|---|--------|-----------|----------|
| 26 | `NotEmpty` | `src/main/java/io/github/junggikim/refined/predicate/string/NotEmpty.java` | `RefinedSupport.require("not-empty", ...)` |
| 27 | `NotBlank` | `src/main/java/io/github/junggikim/refined/predicate/string/NotBlank.java` | `RefinedSupport.require("not-blank", ...)` |

#### Group A-2: 제네릭 (3개) — unchecked cast 필요

| # | 클래스 | 파일 경로 | 클래스 선언 |
|---|--------|-----------|------------|
| 28 | `EmptyCollection` | `src/main/java/io/github/junggikim/refined/predicate/collection/EmptyCollection.java` | `EmptyCollection<T extends Collection<?>> implements Constraint<T>` |
| 29 | `AscendingList` | `src/main/java/io/github/junggikim/refined/predicate/collection/AscendingList.java` | `AscendingList<T extends Comparable<T>> implements Constraint<List<T>>` |
| 30 | `DescendingList` | `src/main/java/io/github/junggikim/refined/predicate/collection/DescendingList.java` | `DescendingList<T extends Comparable<T>> implements Constraint<List<T>>` |

---

### Group B: 파라미터 있음 — `of(...)` 팩토리 (41개)

#### Group B-1: 비제네릭 파라미터 (15개)

##### Numeric — Divisibility (6개)

| # | 클래스 | 파일 경로 | 생성자 | `of()` 시그니처 | violation code |
|---|--------|-----------|--------|----------------|----------------|
| 31 | `DivisibleByInt` | `.../numeric/DivisibleByInt.java` | `(int divisor)` — `divisor == 0` 시 IAE | `of(int divisor)` | `"divisible-by-int"` |
| 32 | `DivisibleByLong` | `.../numeric/DivisibleByLong.java` | `(long divisor)` — `divisor == 0L` 시 IAE | `of(long divisor)` | `"divisible-by-long"` |
| 33 | `DivisibleByBigInteger` | `.../numeric/DivisibleByBigInteger.java` | `(BigInteger divisor)` — null/ZERO 시 IAE | `of(BigInteger divisor)` | `"divisible-by-big-integer"` |
| 34 | `NonDivisibleByInt` | `.../numeric/NonDivisibleByInt.java` | `(int divisor)` — `divisor == 0` 시 IAE | `of(int divisor)` | `"non-divisible-by-int"` |
| 35 | `NonDivisibleByLong` | `.../numeric/NonDivisibleByLong.java` | `(long divisor)` — `divisor == 0` 시 IAE | `of(long divisor)` | `"non-divisible-by-long"` |
| 36 | `NonDivisibleByBigInteger` | `.../numeric/NonDivisibleByBigInteger.java` | `(BigInteger divisor)` — null/ZERO 시 IAE | `of(BigInteger divisor)` | `"non-divisible-by-big-integer"` |

##### Numeric — Modulo (2개)

| # | 클래스 | 파일 경로 | 생성자 | `of()` 시그니처 | violation code |
|---|--------|-----------|--------|----------------|----------------|
| 37 | `ModuloInt` | `.../numeric/ModuloInt.java` | `(int divisor, int remainder)` — `divisor == 0` 시 IAE | `of(int divisor, int remainder)` | `"modulo-int"` |
| 38 | `ModuloLong` | `.../numeric/ModuloLong.java` | `(long divisor, long remainder)` — `divisor == 0` 시 IAE | `of(long divisor, long remainder)` | `"modulo-long"` |

##### String — 파라미터 (7개)

| # | 클래스 | 파일 경로 | 생성자 | `of()` 시그니처 | violation code |
|---|--------|-----------|--------|----------------|----------------|
| 39 | `LengthAtLeast` | `.../string/LengthAtLeast.java` | `(int minimum)` | `of(int minimum)` | `"length-at-least"` |
| 40 | `LengthAtMost` | `.../string/LengthAtMost.java` | `(int maximum)` | `of(int maximum)` | `"length-at-most"` |
| 41 | `LengthBetween` | `.../string/LengthBetween.java` | `(int minimum, int maximum)` — `min > max` 시 IAE | `of(int minimum, int maximum)` | `"length-between"` |
| 42 | `MatchesRegex` | `.../string/MatchesRegex.java` | `(String pattern)` — Pattern.compile() | `of(String pattern)` | `"matches-regex"` |
| 43 | `StartsWith` | `.../string/StartsWith.java` | `(String prefix)` | `of(String prefix)` | `"starts-with"` |
| 44 | `EndsWith` | `.../string/EndsWith.java` | `(String suffix)` | `of(String suffix)` | `"ends-with"` |
| 45 | `Contains` | `.../string/Contains.java` | `(String infix)` | `of(String infix)` | `"contains"` |

#### Group B-2: 제네릭 Comparable (10개)

##### Comparison (6개)

| # | 클래스 | 파일 경로 | 생성자 | `of()` 시그니처 | violation code |
|---|--------|-----------|--------|----------------|----------------|
| 46 | `GreaterThan` | `.../numeric/GreaterThan.java` | `(T minimum)` | `<T extends Comparable<T>> of(T minimum)` | `"greater-than"` |
| 47 | `GreaterOrEqual` | `.../numeric/GreaterOrEqual.java` | `(T minimum)` | `<T extends Comparable<T>> of(T minimum)` | `"greater-or-equal"` |
| 48 | `LessThan` | `.../numeric/LessThan.java` | `(T maximum)` | `<T extends Comparable<T>> of(T maximum)` | `"less-than"` |
| 49 | `LessOrEqual` | `.../numeric/LessOrEqual.java` | `(T maximum)` | `<T extends Comparable<T>> of(T maximum)` | `"less-or-equal"` |
| 50 | `EqualTo` | `.../numeric/EqualTo.java` | `(T expected)` | `<T extends Comparable<T>> of(T expected)` | `"equal-to"` |
| 51 | `NotEqualTo` | `.../numeric/NotEqualTo.java` | `(T expected)` | `<T extends Comparable<T>> of(T expected)` | `"not-equal-to"` |

##### Interval (4개)

| # | 클래스 | 파일 경로 | 생성자 | `of()` 시그니처 | violation code |
|---|--------|-----------|--------|----------------|----------------|
| 52 | `OpenInterval` | `.../numeric/OpenInterval.java` | `(T min, T max)` — validateIntervalBounds | `<T extends Comparable<T>> of(T minimum, T maximum)` | `"open-interval"` |
| 53 | `ClosedInterval` | `.../numeric/ClosedInterval.java` | `(T min, T max)` — validateIntervalBounds | `<T extends Comparable<T>> of(T minimum, T maximum)` | `"closed-interval"` |
| 54 | `OpenClosedInterval` | `.../numeric/OpenClosedInterval.java` | `(T min, T max)` — validateIntervalBounds | `<T extends Comparable<T>> of(T minimum, T maximum)` | `"open-closed-interval"` |
| 55 | `ClosedOpenInterval` | `.../numeric/ClosedOpenInterval.java` | `(T min, T max)` — validateIntervalBounds | `<T extends Comparable<T>> of(T minimum, T maximum)` | `"closed-open-interval"` |

#### Group B-3: Collection bounded (4개)

| # | 클래스 | 파일 경로 | 클래스 선언 | 생성자 | `of()` 시그니처 | violation code |
|---|--------|-----------|------------|--------|----------------|----------------|
| 56 | `MinSize` | `.../collection/MinSize.java` | `MinSize<T extends Collection<?>>` | `(int minimum)` | `<T extends Collection<?>> of(int minimum)` | `"min-size"` |
| 57 | `MaxSize` | `.../collection/MaxSize.java` | `MaxSize<T extends Collection<?>>` | `(int maximum)` | `<T extends Collection<?>> of(int maximum)` | `"max-size"` |
| 58 | `SizeBetween` | `.../collection/SizeBetween.java` | `SizeBetween<T extends Collection<?>>` | `(int min, int max)` — `min > max` 시 IAE | `<T extends Collection<?>> of(int minimum, int maximum)` | `"size-between"` |
| 59 | `SizeEqual` | `.../collection/SizeEqual.java` | `SizeEqual<T extends Collection<?>>` | `(int size)` | `<T extends Collection<?>> of(int size)` | `"size-equal"` |

#### Group B-4: Collection unbounded T (4개)

| # | 클래스 | 파일 경로 | 클래스 선언 | 생성자 | `of()` 시그니처 | violation code |
|---|--------|-----------|------------|--------|----------------|----------------|
| 60 | `ContainsElement` | `.../collection/ContainsElement.java` | `ContainsElement<T>` | `(T expected)` | `<T> of(T expected)` | `"contains-element"` |
| 61 | `ForAllElements` | `.../collection/ForAllElements.java` | `ForAllElements<T>` | `(Predicate<T> predicate)` | `<T> of(Predicate<T> predicate)` | `"for-all-elements"` |
| 62 | `ExistsElement` | `.../collection/ExistsElement.java` | `ExistsElement<T>` | `(Predicate<T> predicate)` | `<T> of(Predicate<T> predicate)` | `"exists-element"` |
| 63 | `CountMatches` | `.../collection/CountMatches.java` | `CountMatches<T>` | `(Predicate<T> predicate, int expectedCount)` | `<T> of(Predicate<T> predicate, int expectedCount)` | `"count-matches"` |

#### Group B-5: List predicates (5개)

| # | 클래스 | 파일 경로 | 클래스 선언 | 생성자 | `of()` 시그니처 | violation code |
|---|--------|-----------|------------|--------|----------------|----------------|
| 64 | `HeadSatisfies` | `.../collection/HeadSatisfies.java` | `HeadSatisfies<T>` | `(Predicate<T> predicate)` | `<T> of(Predicate<T> predicate)` | `"head-satisfies"` |
| 65 | `LastSatisfies` | `.../collection/LastSatisfies.java` | `LastSatisfies<T>` | `(Predicate<T> predicate)` | `<T> of(Predicate<T> predicate)` | `"last-satisfies"` |
| 66 | `IndexSatisfies` | `.../collection/IndexSatisfies.java` | `IndexSatisfies<T>` | `(int index, Predicate<T> predicate)` | `<T> of(int index, Predicate<T> predicate)` | `"index-satisfies"` |
| 67 | `InitSatisfies` | `.../collection/InitSatisfies.java` | `InitSatisfies<T>` | `(Predicate<List<T>> predicate)` | `<T> of(Predicate<List<T>> predicate)` | `"init-satisfies"` |
| 68 | `TailSatisfies` | `.../collection/TailSatisfies.java` | `TailSatisfies<T>` | `(Predicate<List<T>> predicate)` | `<T> of(Predicate<List<T>> predicate)` | `"tail-satisfies"` |

#### Group B-6: Logical (3개)

| # | 클래스 | 파일 경로 | 클래스 선언 | 생성자 | `of()` 시그니처 | violation code |
|---|--------|-----------|------------|--------|----------------|----------------|
| 69 | `AllOf` | `.../logical/AllOf.java` | `AllOf<T>` | `(List<Constraint<T>> constraints)` | `<T> of(List<Constraint<T>> constraints)` | `"all-of"` |
| 70 | `AnyOf` | `.../logical/AnyOf.java` | `AnyOf<T>` | `(List<Constraint<T>> constraints)` | `<T> of(List<Constraint<T>> constraints)` | `"any-of"` |
| 71 | `Not` | `.../logical/Not.java` | `Not<T>` | `(Constraint<T> constraint)` | `<T> of(Constraint<T> constraint)` | `"not"` |

---

## Phase 1: Group A-1 비제네릭 싱글턴 (27개) — `instance()`

### 적용 패턴

기존 코드는 **절대 변경하지 않고**, `private static final INSTANCE` 필드 + `public static instance()` 메서드만 추가합니다.

```java
// === 변경 전 ===
public final class And implements Constraint<List<Boolean>> {

    private final Constraint<List<Boolean>> delegate = RefinedSupport.andBooleanList();

    @Override
    public Validation<Violation, List<Boolean>> validate(List<Boolean> value) {
        return delegate.validate(value);
    }
}

// === 변경 후 ===
public final class And implements Constraint<List<Boolean>> {

    private static final And INSTANCE = new And();

    private final Constraint<List<Boolean>> delegate = RefinedSupport.andBooleanList();

    /**
     * Returns the shared singleton instance.
     *
     * @return shared {@code And} instance
     */
    public static And instance() {
        return INSTANCE;
    }

    @Override
    public Validation<Violation, List<Boolean>> validate(List<Boolean> value) {
        return delegate.validate(value);
    }
}
```

### 주의사항

- `INSTANCE` 필드는 반드시 클래스의 **첫 번째 필드**로 선언 (`delegate` 앞)
- Javadoc의 `@return` 태그에서 `{@code ClassName}` 부분은 해당 클래스명으로 변경
- 기본 생성자는 암묵적(implicit)이므로 추가로 명시하지 않음

---

## Phase 2: Group A-2 제네릭 싱글턴 (3개) — `instance()` + unchecked cast

### 적용 패턴

```java
// === EmptyCollection 변경 후 ===
public final class EmptyCollection<T extends Collection<?>> implements Constraint<T> {

    private static final EmptyCollection<?> INSTANCE = new EmptyCollection<>();

    private final Constraint<T> delegate;

    /**
     * Returns the shared singleton instance.
     *
     * @param <T> collection type
     * @return shared {@code EmptyCollection} instance
     */
    @SuppressWarnings("unchecked")
    public static <T extends Collection<?>> EmptyCollection<T> instance() {
        return (EmptyCollection<T>) INSTANCE;
    }

    public EmptyCollection() {
        this.delegate = RefinedSupport.require("empty-collection", "collection must be empty", Collection::isEmpty);
    }

    @Override
    public Validation<Violation, T> validate(T value) {
        return delegate.validate(value);
    }
}
```

### 3개 클래스별 정확한 시그니처

| 클래스 | INSTANCE 타입 | `instance()` 반환 타입 | 제네릭 바운드 |
|--------|--------------|----------------------|-------------|
| `EmptyCollection` | `EmptyCollection<?>` | `EmptyCollection<T>` | `<T extends Collection<?>>` |
| `AscendingList` | `AscendingList<?>` | `AscendingList<T>` | `<T extends Comparable<T>>` |
| `DescendingList` | `DescendingList<?>` | `DescendingList<T>` | `<T extends Comparable<T>>` |

### 주의사항

- `@SuppressWarnings("unchecked")`는 반드시 **메서드 레벨**에 적용 (클래스 레벨 X)
- `INSTANCE` 타입은 와일드카드 `<?>` 사용
- `AscendingList`와 `DescendingList`는 기존에 암묵적 기본 생성자 사용 — 기존 상태 유지 (명시적 생성자 추가 금지)
- `EmptyCollection`은 기존에 명시적 `public EmptyCollection()` 생성자 있음 — 그대로 유지
- INSTANCE 필드는 기존 delegate 필드 **앞**에 위치

---

## Phase 3: Group B-1 비제네릭 파라미터 (15개) — `of(...)`

### 적용 패턴

```java
// === DivisibleByInt 변경 후 (기존 코드 끝에 추가) ===
public final class DivisibleByInt implements Constraint<Integer> {

    // ... 기존 필드, 생성자, validate() 그대로 ...

    /**
     * Creates a new {@code DivisibleByInt} constraint.
     *
     * @param divisor the divisor (must not be 0)
     * @return new constraint instance
     */
    public static DivisibleByInt of(int divisor) {
        return new DivisibleByInt(divisor);
    }
}
```

### 15개 정확한 `of()` 코드

```java
// Divisibility
public static DivisibleByInt of(int divisor) { return new DivisibleByInt(divisor); }
public static DivisibleByLong of(long divisor) { return new DivisibleByLong(divisor); }
public static DivisibleByBigInteger of(BigInteger divisor) { return new DivisibleByBigInteger(divisor); }
public static NonDivisibleByInt of(int divisor) { return new NonDivisibleByInt(divisor); }
public static NonDivisibleByLong of(long divisor) { return new NonDivisibleByLong(divisor); }
public static NonDivisibleByBigInteger of(BigInteger divisor) { return new NonDivisibleByBigInteger(divisor); }

// Modulo
public static ModuloInt of(int divisor, int remainder) { return new ModuloInt(divisor, remainder); }
public static ModuloLong of(long divisor, long remainder) { return new ModuloLong(divisor, remainder); }

// String
public static LengthAtLeast of(int minimum) { return new LengthAtLeast(minimum); }
public static LengthAtMost of(int maximum) { return new LengthAtMost(maximum); }
public static LengthBetween of(int minimum, int maximum) { return new LengthBetween(minimum, maximum); }
public static MatchesRegex of(String pattern) { return new MatchesRegex(pattern); }
public static StartsWith of(String prefix) { return new StartsWith(prefix); }
public static EndsWith of(String suffix) { return new EndsWith(suffix); }
public static Contains of(String infix) { return new Contains(infix); }
```

### Javadoc 패턴

```java
/**
 * Creates a new {@code ClassName} constraint.
 *
 * @param paramName description
 * @return new constraint instance
 */
```

파라미터 설명은 기존 생성자의 Javadoc이 있으면 따르고, 없으면 생성자의 파라미터명 그대로 사용.

### 주의사항

- `of()` 메서드는 클래스의 **마지막 메서드**로 위치 (validate() 뒤)
- `of()` 본문은 단순히 `return new ClassName(args);` — 검증 로직은 생성자에서 이미 수행
- 메서드 본문이 한 줄이더라도 Checkstyle `NeedBraces` 규칙 준수 위해 `{ }` 사용

---

## Phase 4: Group B-2 제네릭 Comparable (10개) — `of(...)`

### 적용 패턴

```java
// === GreaterThan 변경 후 ===
public final class GreaterThan<T extends Comparable<T>> implements Constraint<T> {

    // ... 기존 코드 그대로 ...

    /**
     * Creates a new {@code GreaterThan} constraint.
     *
     * @param minimum the exclusive lower bound
     * @param <T> comparable type
     * @return new constraint instance
     */
    public static <T extends Comparable<T>> GreaterThan<T> of(T minimum) {
        return new GreaterThan<>(minimum);
    }
}
```

### 10개 정확한 `of()` 코드

```java
// Comparison (single param)
public static <T extends Comparable<T>> GreaterThan<T> of(T minimum) { return new GreaterThan<>(minimum); }
public static <T extends Comparable<T>> GreaterOrEqual<T> of(T minimum) { return new GreaterOrEqual<>(minimum); }
public static <T extends Comparable<T>> LessThan<T> of(T maximum) { return new LessThan<>(maximum); }
public static <T extends Comparable<T>> LessOrEqual<T> of(T maximum) { return new LessOrEqual<>(maximum); }
public static <T extends Comparable<T>> EqualTo<T> of(T expected) { return new EqualTo<>(expected); }
public static <T extends Comparable<T>> NotEqualTo<T> of(T expected) { return new NotEqualTo<>(expected); }

// Interval (two params)
public static <T extends Comparable<T>> OpenInterval<T> of(T minimum, T maximum) { return new OpenInterval<>(minimum, maximum); }
public static <T extends Comparable<T>> ClosedInterval<T> of(T minimum, T maximum) { return new ClosedInterval<>(minimum, maximum); }
public static <T extends Comparable<T>> OpenClosedInterval<T> of(T minimum, T maximum) { return new OpenClosedInterval<>(minimum, maximum); }
public static <T extends Comparable<T>> ClosedOpenInterval<T> of(T minimum, T maximum) { return new ClosedOpenInterval<>(minimum, maximum); }
```

### 주의사항

- `<T extends Comparable<T>>` 타입 파라미터를 메서드 레벨에 선언
- `return new ClassName<>(args);` 에서 다이아몬드 연산자 사용

---

## Phase 5: Group B-3~6 나머지 제네릭 (16개) — `of(...)`

### Collection bounded (4개)

```java
public static <T extends Collection<?>> MinSize<T> of(int minimum) { return new MinSize<>(minimum); }
public static <T extends Collection<?>> MaxSize<T> of(int maximum) { return new MaxSize<>(maximum); }
public static <T extends Collection<?>> SizeBetween<T> of(int minimum, int maximum) { return new SizeBetween<>(minimum, maximum); }
public static <T extends Collection<?>> SizeEqual<T> of(int size) { return new SizeEqual<>(size); }
```

### Collection unbounded T (4개)

```java
public static <T> ContainsElement<T> of(T expected) { return new ContainsElement<>(expected); }
public static <T> ForAllElements<T> of(Predicate<T> predicate) { return new ForAllElements<>(predicate); }
public static <T> ExistsElement<T> of(Predicate<T> predicate) { return new ExistsElement<>(predicate); }
public static <T> CountMatches<T> of(Predicate<T> predicate, int expectedCount) { return new CountMatches<>(predicate, expectedCount); }
```

### List predicates (5개)

```java
public static <T> HeadSatisfies<T> of(Predicate<T> predicate) { return new HeadSatisfies<>(predicate); }
public static <T> LastSatisfies<T> of(Predicate<T> predicate) { return new LastSatisfies<>(predicate); }
public static <T> IndexSatisfies<T> of(int index, Predicate<T> predicate) { return new IndexSatisfies<>(index, predicate); }
public static <T> InitSatisfies<T> of(Predicate<List<T>> predicate) { return new InitSatisfies<>(predicate); }
public static <T> TailSatisfies<T> of(Predicate<List<T>> predicate) { return new TailSatisfies<>(predicate); }
```

### Logical (3개)

```java
public static <T> AllOf<T> of(List<Constraint<T>> constraints) { return new AllOf<>(constraints); }
public static <T> AnyOf<T> of(List<Constraint<T>> constraints) { return new AnyOf<>(constraints); }
public static <T> Not<T> of(Constraint<T> constraint) { return new Not<>(constraint); }
```

### Javadoc 패턴 (모든 제네릭 of())

```java
/**
 * Creates a new {@code ClassName} constraint.
 *
 * @param paramName description
 * @param <T> element/value type
 * @return new constraint instance
 */
```

---

## Phase 6: 테스트

### 새 파일: `src/test/java/io/github/junggikim/refined/predicate/StaticFactoryMethodTest.java`

기존 3개 테스트 파일은 **절대 변경하지 않음** (하위 호환 증명).

### 기존 테스트 패턴 참고

기존 테스트에서 사용하는 패턴:
- `assertEquals(value, predicate.validate(value).get())` — valid 검증
- `assertEquals("code", predicate.validate(invalid).getError().code())` — invalid 검증
- `assertThrows(IllegalArgumentException.class, () -> ...)` — 생성자 가드 검증
- `listOf(...)` — `io.github.junggikim.refined.support.TestCollections.listOf`
- `@TestFactory` + `DynamicTest` + `Stream<DynamicTest>` 패턴

### 테스트 내용

**Group A (30개)**: 각 `instance()`에 대해:
1. valid 값을 통과시키는지 (`validate(valid).get()`)
2. invalid 값을 거부하는지 (`validate(invalid).getError().code()`)
3. **동일 인스턴스를 반환하는지** (`assertSame(X.instance(), X.instance())`)

**Group B (41개)**: 각 `of(...)`에 대해:
1. valid 값을 통과시키는지
2. invalid 값을 거부하는지

### 테스트 데이터 참고 (기존 테스트에서 사용된 값)

#### Boolean

| Predicate | valid | invalid | code |
|-----------|-------|---------|------|
| `And` | `listOf(true, true)` | `listOf(true, false)` | `"and"` |
| `Or` | `listOf(false, true)` | `listOf(false, false)` | `"or"` |
| `Xor` | `listOf(true, false)` | `listOf(true, true)` | `"xor"` |
| `Nand` | `listOf(true, false)` | `listOf(true, true)` | `"nand"` |
| `Nor` | `listOf(false, false)` | `listOf(false, true)` | `"nor"` |
| `OneOf` | `listOf(false, true, false)` | `listOf(true, true, false)` | `"one-of"` |
| `TrueValue` | `true` | `false` | `"true"` |
| `FalseValue` | `false` | `true` | `"false"` |

#### Character

| Predicate | valid | invalid | code |
|-----------|-------|---------|------|
| `IsDigitChar` | `'1'` | `'a'` | `"digit-char"` |
| `IsLetterChar` | `'a'` | `'1'` | `"letter-char"` |
| `IsLetterOrDigitChar` | `'a'` | `'!'` | `"letter-or-digit-char"` |
| `IsLowerCaseChar` | `'a'` | `'A'` | `"lower-case-char"` |
| `IsUpperCaseChar` | `'A'` | `'a'` | `"upper-case-char"` |
| `IsWhitespaceChar` | `' '` | `'a'` | `"whitespace-char"` |
| `IsSpecialChar` | `'!'` | `'a'` | `"special-char"` |

#### Numeric — Parity & Special

| Predicate | valid | invalid | code |
|-----------|-------|---------|------|
| `EvenInt` | `2` | `1` | `"even-int"` |
| `OddInt` | `1` | `2` | `"odd-int"` |
| `EvenLong` | `2L` | `1L` | `"even-long"` |
| `OddLong` | `1L` | `2L` | `"odd-long"` |
| `EvenBigInteger` | `BigInteger.valueOf(2)` | `BigInteger.valueOf(1)` | `"even-big-integer"` |
| `OddBigInteger` | `BigInteger.valueOf(1)` | `BigInteger.valueOf(2)` | `"odd-big-integer"` |
| `FiniteFloatPredicate` | `1.0f` | `Float.POSITIVE_INFINITY` | `"finite-float"` |
| `FiniteDoublePredicate` | `1.0` | `Double.POSITIVE_INFINITY` | `"finite-double"` |
| `NonNaNFloatPredicate` | `1.0f` | `Float.NaN` | `"non-nan-float"` |
| `NonNaNDoublePredicate` | `1.0` | `Double.NaN` | `"non-nan-double"` |

#### String — 파라미터 없음

| Predicate | valid | invalid | code |
|-----------|-------|---------|------|
| `NotEmpty` | `"a"` | `""` | `"not-empty"` |
| `NotBlank` | `"a"` | `"  "` | `"not-blank"` |

#### Group A-2 제네릭 싱글턴

| Predicate | valid | invalid | code |
|-----------|-------|---------|------|
| `EmptyCollection.instance()` | `listOf()` | `listOf(1)` | `"empty-collection"` |
| `AscendingList.instance()` | `listOf(1, 2, 3)` | `listOf(3, 2, 1)` | `"ascending-list"` |
| `DescendingList.instance()` | `listOf(3, 2, 1)` | `listOf(1, 2, 3)` | `"descending-list"` |

#### Group B 테스트 데이터 (41개) — 대표 예시

```java
// Comparison
GreaterThan.of(1).validate(2).get()          // valid
GreaterThan.of(1).validate(1).getError().code()  // "greater-than"

// Interval
ClosedInterval.of(1, 10).validate(5).get()   // valid
ClosedInterval.of(1, 10).validate(11).getError().code()  // "closed-interval"

// Divisibility
DivisibleByInt.of(3).validate(9).get()       // valid
DivisibleByInt.of(3).validate(10).getError().code()  // "divisible-by-int"

// String
LengthBetween.of(2, 5).validate("abc").get() // valid
LengthBetween.of(2, 5).validate("a").getError().code()  // "length-between"

// Collection
MinSize.of(2).validate(listOf(1, 2)).get()   // valid
ForAllElements.of(v -> v > 0).validate(listOf(1, 2)).get()  // valid

// Logical
Not.of(new GreaterThan<>(5)).validate(3).get()  // valid
AllOf.of(listOf(new GreaterThan<>(0), new LessThan<>(10))).validate(5).get()  // valid
```

### 테스트 구조 (추천)

```java
package io.github.junggikim.refined.predicate;

import static io.github.junggikim.refined.support.TestCollections.listOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

// ... 71개 predicate import ...

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class StaticFactoryMethodTest {

    // === Group A: instance() 싱글턴 테스트 ===

    @TestFactory
    Stream<DynamicTest> singletonInstancesValidateCorrectly() {
        // 30개 instance()의 valid/invalid 검증
        // DynamicTest.dynamicTest() 패턴 사용
    }

    @Test
    void singletonInstancesReturnSameReference() {
        // 30개 모두 assertSame(X.instance(), X.instance())
    }

    // === Group B: of() 팩토리 테스트 ===

    @TestFactory
    Stream<DynamicTest> factoryMethodsValidateCorrectly() {
        // 41개 of()의 valid/invalid 검증
    }
}
```

---

## Phase 7: 1차 빌드 검증 — 정적 분석 + 테스트 + 커버리지

구현 완료 직후 모든 Gradle 검사를 실행하여 빌드 통과를 확인합니다.

### Step 7-1: 전체 빌드 검사

```bash
./gradlew clean check
```

통과해야 하는 항목:
- `compileJava` / `compileTestJava` — 컴파일 오류 없음
- `checkstyleMain` — 6개 규칙 위반 없음
- `spotbugsMain` / `spotbugsTest` — Effort.MAX, Confidence.MEDIUM 기준 위반 없음
- `pmdMain` — PMD 7.10.0 규칙 위반 없음
- `test` — 기존 3개 테스트 파일 + 새 StaticFactoryMethodTest 모두 통과
- `jacocoTestCoverageVerification` — 6개 메트릭 모두 100%
- `javadoc` — Javadoc 생성 오류 없음

### Step 7-2: Java 8 호환성 검사

```bash
./gradlew testJava8
```

- Java 8 런타임에서 모든 테스트 통과 확인

### Step 7-3: 실패 시 대응

- 실패한 항목별로 원인 분석 후 해당 Phase의 코드를 수정
- 수정 후 `./gradlew clean check` 재실행하여 전체 통과 확인

---

## Phase 8: Claude 2차 검증 — 코드 리뷰

Phase 7 빌드가 모두 통과한 후, Claude가 구현 결과를 꼼꼼히 리뷰합니다.

### Step 8-1: 하위 호환성 검증

- 모든 71개 파일에서 기존 public 생성자가 **그대로 유지**되었는지 확인
- 기존 API (생성자 시그니처, `validate()` 메서드)가 변경되지 않았는지 확인
- 기존 3개 테스트 파일이 **수정 없이** 통과하는지 확인

### Step 8-2: 패턴 일관성 검증

- Group A 30개: 모두 `private static final INSTANCE` + `public static instance()` 패턴이 일관되게 적용되었는지
- Group B 41개: 모두 `public static ... of(...)` 패턴이 일관되게 적용되었는지
- Javadoc이 모든 새 메서드에 올바르게 작성되었는지
- `@SuppressWarnings("unchecked")`가 필요한 3개 제네릭 싱글턴에만 적용되었는지

### Step 8-3: 테스트 충분성 검증

- StaticFactoryMethodTest에서 71개 팩토리 메서드가 **모두** 테스트되는지 확인
- Group A: valid/invalid 검증 + `assertSame` 싱글턴 검증이 30개 모두에 있는지
- Group B: valid/invalid 검증이 41개 모두에 있는지
- 기존 테스트와 새 테스트 사이에 중복/충돌이 없는지

### Step 8-4: 코드 품질 검증

- 불필요한 import가 추가되지 않았는지
- Checkstyle, SpotBugs, PMD 규칙을 준수하는지 (코드 레벨 리뷰)
- 정적 필드 초기화 순서가 안전한지 (INSTANCE -> delegate 관계)

---

## Phase 9: Codex 2차 검증 — 독립 리뷰

Claude 리뷰 후, Codex에게 독립적인 코드 리뷰를 요청합니다.

### Codex 리뷰 요청 항목

1. **정확성**: 모든 71개 정적 팩토리 메서드의 시그니처와 반환 타입이 올바른지
2. **안전성**: 제네릭 싱글턴의 unchecked cast가 type erasure 하에서 안전한지
3. **완전성**: 누락된 파일이나 메서드가 없는지 (71개 전수 확인)
4. **Javadoc**: `@param`, `@return` 태그가 정확하고 일관되게 작성되었는지
5. **테스트 커버리지**: 모든 새 코드 경로가 테스트되는지
6. **하위 호환성**: 기존 public API가 보존되었는지

### Codex 리뷰 결과 반영

- 지적 사항이 있으면 수정 후 Phase 7 (빌드 검증)부터 다시 실행
- 모든 지적 사항 해결 후 최종 확인

---

## Phase 10: 최종 확인

모든 검증을 통과한 후 최종 상태를 확인합니다.

```bash
./gradlew clean check testJava8   # 전체 빌드 + Java 8 호환 최종 확인
```

### 최종 체크리스트

- [ ] `./gradlew clean check` 통과 (정적 분석 + 테스트 + 커버리지 100%)
- [ ] `./gradlew testJava8` 통과 (Java 8 런타임 호환)
- [ ] 기존 3개 테스트 파일 변경 없음 확인
- [ ] 71개 소스 파일에 정적 팩토리 메서드 추가 확인
- [ ] StaticFactoryMethodTest에서 71개 메서드 전수 테스트 확인
- [ ] Claude 코드 리뷰 통과
- [ ] Codex 코드 리뷰 통과
- [ ] git status로 변경된 파일 수 확인 (72개: 71 소스 + 1 테스트)

---

## 위험 요소 및 대응

| 위험 | 대응 |
|------|------|
| SpotBugs가 3개 제네릭 싱글턴의 unchecked cast 경고 | `@SuppressWarnings("unchecked")` 메서드 레벨 적용. 불충분 시 `config/spotbugs/exclude.xml`에 추가 |
| 정적 초기화 순서 문제 (`INSTANCE` -> `delegate`) | 안전함: static 필드(`INSTANCE`) 초기화 시 `new X()` 호출 -> 인스턴스 필드(`delegate`) 초기화 완료 후 할당 |
| 스레드 안전성 | JLS 12.4.2에 의해 static final 필드 초기화는 스레드 안전 보장 |
| Checkstyle 위반 | 추가 코드는 `WhitespaceAround`, `NeedBraces`, `OneStatementPerLine` 등 모든 규칙 충족 |
| 2차 검증에서 누락 발견 | Phase 7->8->9->10 순서로 반복하여 모든 문제 해결 후 최종 확인 |

---

## 기존 테스트 파일 (변경하지 않음)

| # | 파일 경로 |
|---|-----------|
| 1 | `src/test/java/io/github/junggikim/refined/predicate/PredicateTypesTest.java` |
| 2 | `src/test/java/io/github/junggikim/refined/predicate/AdditionalPredicateTypesTest.java` |
| 3 | `src/test/java/io/github/junggikim/refined/predicate/NewPredicateTypesTest.java` |

## 유틸리티 참고

| 클래스 | 경로 | 용도 |
|--------|------|------|
| `TestCollections` | `src/test/java/io/github/junggikim/refined/support/TestCollections.java` | `listOf(T...)` — Java 8 호환 `List.of()` 대체 |
| `RefinedSupport` | `src/main/java/io/github/junggikim/refined/internal/RefinedSupport.java` | 내부 유틸리티 (public API 아님) |
| `Constraint<T>` | `src/main/java/io/github/junggikim/refined/core/Constraint.java` | `@FunctionalInterface` — `validate(T)` 메서드 |
| `Violation` | `src/main/java/io/github/junggikim/refined/violation/Violation.java` | `Violation.of(code, message)` 팩토리 |
| `Validation<E, A>` | `src/main/java/io/github/junggikim/refined/validation/Validation.java` | `Valid`/`Invalid` — fail-fast |
