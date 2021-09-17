package no.liflig.norwegian.types

import kotlinx.serialization.Serializable
import no.liflig.norwegian.types.validation.Mod11

@JvmInline
@Serializable
value class BirthNumber private constructor(val value: String) {
    companion object {

        internal val FACTORS_1 = intArrayOf(3, 7, 6, 1, 8, 9, 4, 5, 2)
        internal val FACTORS_2 = intArrayOf(5, 4, 3, 2, 7, 6, 5, 4, 3, 2)

        fun of(value: String): BirthNumber =
            value
                .trim()
                .also {
                    require(it.length == 11) {
                        "Value ($it) does not have 11 digits and is consequently not a valid birth number"
                    }
                    require(it.all { it.isDigit() }) {
                        "Value ($it) does not consist of only digits and is consequently not a valid birth number"
                    }
                    require(it.hasValidBirthNumberCheckDigits()) {
                        "Value ($it) does not have valid Modulus11 check digits " +
                            "and is consequently not a valid birth number"
                    }

                    // TODO
                    // validate individnummer
                    // validate dato
                    // validate ikke syntetisk?
                }
                .let {
                    BirthNumber(it)
                }

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
