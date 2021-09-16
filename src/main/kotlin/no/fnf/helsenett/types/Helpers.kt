package no.fnf.helsenett.types

internal fun Char.asDigit(): Int = Character.getNumericValue(this)
