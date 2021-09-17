package no.liflig.norwegian.types.validation

import no.liflig.norwegian.types.asDigit

object Mod11 {
    fun calcCheckDigit(value: String, vararg factors: Int): Int {
        require(value.all { it.isDigit() })
        val checksum = checkSum(value, *factors)
        val rest = checksum % 11
        return if (rest == 0) 0 else 11 - rest
    }

    private fun checkSum(value: String, vararg factors: Int): Int =
        value
            .map { it.asDigit() }
            .zip(factors.toList()) { char, factor -> char * factor }
            .sum()
}
