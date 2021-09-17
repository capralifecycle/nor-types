package no.liflig.norwegian.types

internal fun Char.asDigit(): Int = Character.getNumericValue(this)
