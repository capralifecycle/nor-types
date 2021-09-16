package no.fnf.helsenett.types

import no.fnf.helsenett.types.validation.Mod11

@JvmInline
value class OrganisationNumber private constructor(val value: String) {
    companion object {

        internal val FACTORS = intArrayOf(3, 2, 7, 6, 5, 4, 3, 2)

        fun of(value: String): OrganisationNumber =
            value
                .trim()
                .also {
                    require(it.length == 9) {
                        "Value ($it) does not have 9 digits and is consequently not a valid organisation number"
                    }
                    require(it.all { char -> char.isDigit() }) {
                        "Value ($it) does not consist of only digits " +
                            "and is consequently not a valid organisation number"
                    }

                    require(it.hasValidOrgNumberCheckDigit()) {
                        "Value ($it) does not have valid Modulus11 check digits and is consequently not a valid birth number"
                    }
                }
                .let { OrganisationNumber(it) }

        fun String.toOrganisationNumber(): OrganisationNumber = of(this)

        internal fun String.hasValidOrgNumberCheckDigit(): Boolean =
            orgNumberHasValidCheckDigit(this)

        private fun orgNumberHasValidCheckDigit(value: String): Boolean =
            value.last().asDigit() == Mod11.calcCheckDigit(value, *FACTORS)
    }
}
