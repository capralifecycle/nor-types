package no.liflig.norwegian.types

/**
 * Throws IllegalArgument exception if requirements fail for given value.
 */
@JvmInline
value class NorwegianPhoneNumber private constructor(val value: String) {

    init {
        require(value.isValidPhoneNumber()) { "Value [$value] is not a valid Norwegian phone number" }
    }

    override fun toString(): String = value

    companion object {

        /**
         * Accepts string with whitespaces and trims it.
         */
        fun of(value: String): NorwegianPhoneNumber =
            value
                .trim()
                .replace(" ", "")
                .let { NorwegianPhoneNumber(it) }

        private fun String.isValidPhoneNumber(): Boolean {
            val simpleMatch = Regex("^[0-9]{8}\$").containsMatchIn(this)
            val countryPlusMatch = Regex("^\\+47[0-9]{8}$").containsMatchIn(this)
            val countryZerosMatch = Regex("^0047[0-9]{8}$").containsMatchIn(this)

            return simpleMatch || countryPlusMatch || countryZerosMatch
        }
    }
}
