package no.liflig.norwegian.types

@JvmInline
value class NorwegianPhoneNumber private constructor(val value: String) {

    override fun toString(): String = value

    companion object {
        fun of(value: String): NorwegianPhoneNumber =
            value
                .trim()
                .replace(" ", "")
                .also {
                    require(it.isValidPhoneNumber()) { "Value [$value] is not a valid Norwegian phone number" }
                }
                .let {
                    NorwegianPhoneNumber(it)
                }

        private fun String.isValidPhoneNumber(): Boolean {
            val simpleMatch = Regex("^[0-9]{8}\$").containsMatchIn(this)
            val countryPlusMatch = Regex("^\\+47[0-9]{8}$").containsMatchIn(this)
            val countryZerosMatch = Regex("^0047[0-9]{8}$").containsMatchIn(this)

            return simpleMatch || countryPlusMatch || countryZerosMatch
        }
    }
}
