package io.github.junggikim.refined.kotlin

import io.github.junggikim.refined.refined.numeric.FiniteDouble
import io.github.junggikim.refined.refined.numeric.FiniteFloat
import io.github.junggikim.refined.refined.numeric.NaturalBigInteger
import io.github.junggikim.refined.refined.numeric.NaturalByte
import io.github.junggikim.refined.refined.numeric.NaturalInt
import io.github.junggikim.refined.refined.numeric.NaturalLong
import io.github.junggikim.refined.refined.numeric.NaturalShort
import io.github.junggikim.refined.refined.numeric.NegativeBigDecimal
import io.github.junggikim.refined.refined.numeric.NegativeBigInteger
import io.github.junggikim.refined.refined.numeric.NegativeByte
import io.github.junggikim.refined.refined.numeric.NegativeDouble
import io.github.junggikim.refined.refined.numeric.NegativeFloat
import io.github.junggikim.refined.refined.numeric.NegativeInt
import io.github.junggikim.refined.refined.numeric.NegativeLong
import io.github.junggikim.refined.refined.numeric.NegativeShort
import io.github.junggikim.refined.refined.numeric.NonNaNDouble
import io.github.junggikim.refined.refined.numeric.NonNaNFloat
import io.github.junggikim.refined.refined.numeric.NonNegativeBigDecimal
import io.github.junggikim.refined.refined.numeric.NonNegativeBigInteger
import io.github.junggikim.refined.refined.numeric.NonNegativeByte
import io.github.junggikim.refined.refined.numeric.NonNegativeDouble
import io.github.junggikim.refined.refined.numeric.NonNegativeFloat
import io.github.junggikim.refined.refined.numeric.NonNegativeInt
import io.github.junggikim.refined.refined.numeric.NonNegativeLong
import io.github.junggikim.refined.refined.numeric.NonNegativeShort
import io.github.junggikim.refined.refined.numeric.NonPositiveBigDecimal
import io.github.junggikim.refined.refined.numeric.NonPositiveBigInteger
import io.github.junggikim.refined.refined.numeric.NonPositiveByte
import io.github.junggikim.refined.refined.numeric.NonPositiveDouble
import io.github.junggikim.refined.refined.numeric.NonPositiveFloat
import io.github.junggikim.refined.refined.numeric.NonPositiveInt
import io.github.junggikim.refined.refined.numeric.NonPositiveLong
import io.github.junggikim.refined.refined.numeric.NonPositiveShort
import io.github.junggikim.refined.refined.numeric.NonZeroBigDecimal
import io.github.junggikim.refined.refined.numeric.NonZeroBigInteger
import io.github.junggikim.refined.refined.numeric.NonZeroByte
import io.github.junggikim.refined.refined.numeric.NonZeroDouble
import io.github.junggikim.refined.refined.numeric.NonZeroFloat
import io.github.junggikim.refined.refined.numeric.NonZeroInt
import io.github.junggikim.refined.refined.numeric.NonZeroLong
import io.github.junggikim.refined.refined.numeric.NonZeroShort
import io.github.junggikim.refined.refined.numeric.PositiveBigDecimal
import io.github.junggikim.refined.refined.numeric.PositiveBigInteger
import io.github.junggikim.refined.refined.numeric.PositiveByte
import io.github.junggikim.refined.refined.numeric.PositiveDouble
import io.github.junggikim.refined.refined.numeric.PositiveFloat
import io.github.junggikim.refined.refined.numeric.PositiveInt
import io.github.junggikim.refined.refined.numeric.PositiveLong
import io.github.junggikim.refined.refined.numeric.PositiveShort
import io.github.junggikim.refined.refined.numeric.ZeroToOneDouble
import io.github.junggikim.refined.refined.numeric.ZeroToOneFloat
import io.github.junggikim.refined.validation.Validation
import io.github.junggikim.refined.violation.Violation
import java.math.BigDecimal
import java.math.BigInteger

fun Double?.toFiniteDouble(): Validation<Violation, FiniteDouble> = FiniteDouble.of(this)
fun Double?.toFiniteDoubleOrThrow(): FiniteDouble = FiniteDouble.unsafeOf(this)
fun Float?.toFiniteFloat(): Validation<Violation, FiniteFloat> = FiniteFloat.of(this)
fun Float?.toFiniteFloatOrThrow(): FiniteFloat = FiniteFloat.unsafeOf(this)
fun BigInteger?.toNaturalBigInteger(): Validation<Violation, NaturalBigInteger> = NaturalBigInteger.of(this)
fun BigInteger?.toNaturalBigIntegerOrThrow(): NaturalBigInteger = NaturalBigInteger.unsafeOf(this)
fun Byte?.toNaturalByte(): Validation<Violation, NaturalByte> = NaturalByte.of(this)
fun Byte?.toNaturalByteOrThrow(): NaturalByte = NaturalByte.unsafeOf(this)
fun Int?.toNaturalInt(): Validation<Violation, NaturalInt> = NaturalInt.of(this)
fun Int?.toNaturalIntOrThrow(): NaturalInt = NaturalInt.unsafeOf(this)
fun Long?.toNaturalLong(): Validation<Violation, NaturalLong> = NaturalLong.of(this)
fun Long?.toNaturalLongOrThrow(): NaturalLong = NaturalLong.unsafeOf(this)
fun Short?.toNaturalShort(): Validation<Violation, NaturalShort> = NaturalShort.of(this)
fun Short?.toNaturalShortOrThrow(): NaturalShort = NaturalShort.unsafeOf(this)
fun BigDecimal?.toNegativeBigDecimal(): Validation<Violation, NegativeBigDecimal> = NegativeBigDecimal.of(this)
fun BigDecimal?.toNegativeBigDecimalOrThrow(): NegativeBigDecimal = NegativeBigDecimal.unsafeOf(this)
fun BigInteger?.toNegativeBigInteger(): Validation<Violation, NegativeBigInteger> = NegativeBigInteger.of(this)
fun BigInteger?.toNegativeBigIntegerOrThrow(): NegativeBigInteger = NegativeBigInteger.unsafeOf(this)
fun Byte?.toNegativeByte(): Validation<Violation, NegativeByte> = NegativeByte.of(this)
fun Byte?.toNegativeByteOrThrow(): NegativeByte = NegativeByte.unsafeOf(this)
fun Double?.toNegativeDouble(): Validation<Violation, NegativeDouble> = NegativeDouble.of(this)
fun Double?.toNegativeDoubleOrThrow(): NegativeDouble = NegativeDouble.unsafeOf(this)
fun Float?.toNegativeFloat(): Validation<Violation, NegativeFloat> = NegativeFloat.of(this)
fun Float?.toNegativeFloatOrThrow(): NegativeFloat = NegativeFloat.unsafeOf(this)
fun Int?.toNegativeInt(): Validation<Violation, NegativeInt> = NegativeInt.of(this)
fun Int?.toNegativeIntOrThrow(): NegativeInt = NegativeInt.unsafeOf(this)
fun Long?.toNegativeLong(): Validation<Violation, NegativeLong> = NegativeLong.of(this)
fun Long?.toNegativeLongOrThrow(): NegativeLong = NegativeLong.unsafeOf(this)
fun Short?.toNegativeShort(): Validation<Violation, NegativeShort> = NegativeShort.of(this)
fun Short?.toNegativeShortOrThrow(): NegativeShort = NegativeShort.unsafeOf(this)
fun Double?.toNonNaNDouble(): Validation<Violation, NonNaNDouble> = NonNaNDouble.of(this)
fun Double?.toNonNaNDoubleOrThrow(): NonNaNDouble = NonNaNDouble.unsafeOf(this)
fun Float?.toNonNaNFloat(): Validation<Violation, NonNaNFloat> = NonNaNFloat.of(this)
fun Float?.toNonNaNFloatOrThrow(): NonNaNFloat = NonNaNFloat.unsafeOf(this)
fun BigDecimal?.toNonNegativeBigDecimal(): Validation<Violation, NonNegativeBigDecimal> = NonNegativeBigDecimal.of(this)
fun BigDecimal?.toNonNegativeBigDecimalOrThrow(): NonNegativeBigDecimal = NonNegativeBigDecimal.unsafeOf(this)
fun BigInteger?.toNonNegativeBigInteger(): Validation<Violation, NonNegativeBigInteger> = NonNegativeBigInteger.of(this)
fun BigInteger?.toNonNegativeBigIntegerOrThrow(): NonNegativeBigInteger = NonNegativeBigInteger.unsafeOf(this)
fun Byte?.toNonNegativeByte(): Validation<Violation, NonNegativeByte> = NonNegativeByte.of(this)
fun Byte?.toNonNegativeByteOrThrow(): NonNegativeByte = NonNegativeByte.unsafeOf(this)
fun Double?.toNonNegativeDouble(): Validation<Violation, NonNegativeDouble> = NonNegativeDouble.of(this)
fun Double?.toNonNegativeDoubleOrThrow(): NonNegativeDouble = NonNegativeDouble.unsafeOf(this)
fun Float?.toNonNegativeFloat(): Validation<Violation, NonNegativeFloat> = NonNegativeFloat.of(this)
fun Float?.toNonNegativeFloatOrThrow(): NonNegativeFloat = NonNegativeFloat.unsafeOf(this)
fun Int?.toNonNegativeInt(): Validation<Violation, NonNegativeInt> = NonNegativeInt.of(this)
fun Int?.toNonNegativeIntOrThrow(): NonNegativeInt = NonNegativeInt.unsafeOf(this)
fun Long?.toNonNegativeLong(): Validation<Violation, NonNegativeLong> = NonNegativeLong.of(this)
fun Long?.toNonNegativeLongOrThrow(): NonNegativeLong = NonNegativeLong.unsafeOf(this)
fun Short?.toNonNegativeShort(): Validation<Violation, NonNegativeShort> = NonNegativeShort.of(this)
fun Short?.toNonNegativeShortOrThrow(): NonNegativeShort = NonNegativeShort.unsafeOf(this)
fun BigDecimal?.toNonPositiveBigDecimal(): Validation<Violation, NonPositiveBigDecimal> = NonPositiveBigDecimal.of(this)
fun BigDecimal?.toNonPositiveBigDecimalOrThrow(): NonPositiveBigDecimal = NonPositiveBigDecimal.unsafeOf(this)
fun BigInteger?.toNonPositiveBigInteger(): Validation<Violation, NonPositiveBigInteger> = NonPositiveBigInteger.of(this)
fun BigInteger?.toNonPositiveBigIntegerOrThrow(): NonPositiveBigInteger = NonPositiveBigInteger.unsafeOf(this)
fun Byte?.toNonPositiveByte(): Validation<Violation, NonPositiveByte> = NonPositiveByte.of(this)
fun Byte?.toNonPositiveByteOrThrow(): NonPositiveByte = NonPositiveByte.unsafeOf(this)
fun Double?.toNonPositiveDouble(): Validation<Violation, NonPositiveDouble> = NonPositiveDouble.of(this)
fun Double?.toNonPositiveDoubleOrThrow(): NonPositiveDouble = NonPositiveDouble.unsafeOf(this)
fun Float?.toNonPositiveFloat(): Validation<Violation, NonPositiveFloat> = NonPositiveFloat.of(this)
fun Float?.toNonPositiveFloatOrThrow(): NonPositiveFloat = NonPositiveFloat.unsafeOf(this)
fun Int?.toNonPositiveInt(): Validation<Violation, NonPositiveInt> = NonPositiveInt.of(this)
fun Int?.toNonPositiveIntOrThrow(): NonPositiveInt = NonPositiveInt.unsafeOf(this)
fun Long?.toNonPositiveLong(): Validation<Violation, NonPositiveLong> = NonPositiveLong.of(this)
fun Long?.toNonPositiveLongOrThrow(): NonPositiveLong = NonPositiveLong.unsafeOf(this)
fun Short?.toNonPositiveShort(): Validation<Violation, NonPositiveShort> = NonPositiveShort.of(this)
fun Short?.toNonPositiveShortOrThrow(): NonPositiveShort = NonPositiveShort.unsafeOf(this)
fun BigDecimal?.toNonZeroBigDecimal(): Validation<Violation, NonZeroBigDecimal> = NonZeroBigDecimal.of(this)
fun BigDecimal?.toNonZeroBigDecimalOrThrow(): NonZeroBigDecimal = NonZeroBigDecimal.unsafeOf(this)
fun BigInteger?.toNonZeroBigInteger(): Validation<Violation, NonZeroBigInteger> = NonZeroBigInteger.of(this)
fun BigInteger?.toNonZeroBigIntegerOrThrow(): NonZeroBigInteger = NonZeroBigInteger.unsafeOf(this)
fun Byte?.toNonZeroByte(): Validation<Violation, NonZeroByte> = NonZeroByte.of(this)
fun Byte?.toNonZeroByteOrThrow(): NonZeroByte = NonZeroByte.unsafeOf(this)
fun Double?.toNonZeroDouble(): Validation<Violation, NonZeroDouble> = NonZeroDouble.of(this)
fun Double?.toNonZeroDoubleOrThrow(): NonZeroDouble = NonZeroDouble.unsafeOf(this)
fun Float?.toNonZeroFloat(): Validation<Violation, NonZeroFloat> = NonZeroFloat.of(this)
fun Float?.toNonZeroFloatOrThrow(): NonZeroFloat = NonZeroFloat.unsafeOf(this)
fun Int?.toNonZeroInt(): Validation<Violation, NonZeroInt> = NonZeroInt.of(this)
fun Int?.toNonZeroIntOrThrow(): NonZeroInt = NonZeroInt.unsafeOf(this)
fun Long?.toNonZeroLong(): Validation<Violation, NonZeroLong> = NonZeroLong.of(this)
fun Long?.toNonZeroLongOrThrow(): NonZeroLong = NonZeroLong.unsafeOf(this)
fun Short?.toNonZeroShort(): Validation<Violation, NonZeroShort> = NonZeroShort.of(this)
fun Short?.toNonZeroShortOrThrow(): NonZeroShort = NonZeroShort.unsafeOf(this)
fun BigDecimal?.toPositiveBigDecimal(): Validation<Violation, PositiveBigDecimal> = PositiveBigDecimal.of(this)
fun BigDecimal?.toPositiveBigDecimalOrThrow(): PositiveBigDecimal = PositiveBigDecimal.unsafeOf(this)
fun BigInteger?.toPositiveBigInteger(): Validation<Violation, PositiveBigInteger> = PositiveBigInteger.of(this)
fun BigInteger?.toPositiveBigIntegerOrThrow(): PositiveBigInteger = PositiveBigInteger.unsafeOf(this)
fun Byte?.toPositiveByte(): Validation<Violation, PositiveByte> = PositiveByte.of(this)
fun Byte?.toPositiveByteOrThrow(): PositiveByte = PositiveByte.unsafeOf(this)
fun Double?.toPositiveDouble(): Validation<Violation, PositiveDouble> = PositiveDouble.of(this)
fun Double?.toPositiveDoubleOrThrow(): PositiveDouble = PositiveDouble.unsafeOf(this)
fun Float?.toPositiveFloat(): Validation<Violation, PositiveFloat> = PositiveFloat.of(this)
fun Float?.toPositiveFloatOrThrow(): PositiveFloat = PositiveFloat.unsafeOf(this)
fun Int?.toPositiveInt(): Validation<Violation, PositiveInt> = PositiveInt.of(this)
fun Int?.toPositiveIntOrThrow(): PositiveInt = PositiveInt.unsafeOf(this)
fun Long?.toPositiveLong(): Validation<Violation, PositiveLong> = PositiveLong.of(this)
fun Long?.toPositiveLongOrThrow(): PositiveLong = PositiveLong.unsafeOf(this)
fun Short?.toPositiveShort(): Validation<Violation, PositiveShort> = PositiveShort.of(this)
fun Short?.toPositiveShortOrThrow(): PositiveShort = PositiveShort.unsafeOf(this)
fun Double?.toZeroToOneDouble(): Validation<Violation, ZeroToOneDouble> = ZeroToOneDouble.of(this)
fun Double?.toZeroToOneDoubleOrThrow(): ZeroToOneDouble = ZeroToOneDouble.unsafeOf(this)
fun Float?.toZeroToOneFloat(): Validation<Violation, ZeroToOneFloat> = ZeroToOneFloat.of(this)
fun Float?.toZeroToOneFloatOrThrow(): ZeroToOneFloat = ZeroToOneFloat.unsafeOf(this)
