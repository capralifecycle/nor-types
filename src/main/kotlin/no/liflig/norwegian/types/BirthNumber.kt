package no.liflig.norwegian.types

import no.liflig.norwegian.types.validation.Mod11

/**
 * Throws IllegalArgument exception if requirements fail for given value.
 */
@JvmInline
value class BirthNumber(val value: String) {

    init {
        require(value.length == 11) {
            "Value [$value] does not have 11 digits and is consequently not a valid birth number"
        }
        require(value.all { it.isDigit() }) {
            "Value [$value] does not consist of only digits and is consequently not a valid birth number"
        }
        require(value.hasValidBirthNumberCheckDigits()) {
            "Value [$value] does not have valid Modulus11 check digits and is consequently not a valid birth number"
        }
        // TODO
        // validate individnummer
        // validate dato
        // validate ikke syntetisk?
    }

    override fun toString(): String = value

    companion object {

        internal val FACTORS_1 = intArrayOf(3, 7, 6, 1, 8, 9, 4, 5, 2)
        internal val FACTORS_2 = intArrayOf(5, 4, 3, 2, 7, 6, 5, 4, 3, 2)

        internal fun String.hasValidBirthNumberCheckDigits(): Boolean =
            birthNumberHasValidCheckDigits(this)

        private fun birthNumberHasValidCheckDigits(value: String): Boolean {
            val firstCheckDigit = Mod11.calcCheckDigit(value, *FACTORS_1)
            val secondCheckDigit = Mod11.calcCheckDigit(value, *FACTORS_2)

            val secondToLastDigit = value.dropLast(1).last().asDigit()
            val lastDigit = value.last().asDigit()

            return secondToLastDigit == firstCheckDigit && lastDigit == secondCheckDigit
        }
    }
}
