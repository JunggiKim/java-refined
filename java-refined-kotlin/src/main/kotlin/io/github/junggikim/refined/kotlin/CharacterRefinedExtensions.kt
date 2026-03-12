package io.github.junggikim.refined.kotlin

import io.github.junggikim.refined.refined.character.DigitChar
import io.github.junggikim.refined.refined.character.LetterChar
import io.github.junggikim.refined.refined.character.LetterOrDigitChar
import io.github.junggikim.refined.refined.character.LowerCaseChar
import io.github.junggikim.refined.refined.character.SpecialChar
import io.github.junggikim.refined.refined.character.UpperCaseChar
import io.github.junggikim.refined.refined.character.WhitespaceChar
import io.github.junggikim.refined.validation.Validation
import io.github.junggikim.refined.violation.Violation

fun Char?.toDigitChar(): Validation<Violation, DigitChar> = DigitChar.of(this)
fun Char?.toDigitCharOrThrow(): DigitChar = DigitChar.unsafeOf(this)

fun Char?.toLetterChar(): Validation<Violation, LetterChar> = LetterChar.of(this)
fun Char?.toLetterCharOrThrow(): LetterChar = LetterChar.unsafeOf(this)

fun Char?.toLetterOrDigitChar(): Validation<Violation, LetterOrDigitChar> = LetterOrDigitChar.of(this)
fun Char?.toLetterOrDigitCharOrThrow(): LetterOrDigitChar = LetterOrDigitChar.unsafeOf(this)

fun Char?.toLowerCaseChar(): Validation<Violation, LowerCaseChar> = LowerCaseChar.of(this)
fun Char?.toLowerCaseCharOrThrow(): LowerCaseChar = LowerCaseChar.unsafeOf(this)

fun Char?.toSpecialChar(): Validation<Violation, SpecialChar> = SpecialChar.of(this)
fun Char?.toSpecialCharOrThrow(): SpecialChar = SpecialChar.unsafeOf(this)

fun Char?.toUpperCaseChar(): Validation<Violation, UpperCaseChar> = UpperCaseChar.of(this)
fun Char?.toUpperCaseCharOrThrow(): UpperCaseChar = UpperCaseChar.unsafeOf(this)

fun Char?.toWhitespaceChar(): Validation<Violation, WhitespaceChar> = WhitespaceChar.of(this)
fun Char?.toWhitespaceCharOrThrow(): WhitespaceChar = WhitespaceChar.unsafeOf(this)
