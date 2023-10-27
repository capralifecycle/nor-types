package no.liflig.norwegian.types

import no.liflig.norwegian.types.validation.Mod11

/** Throws IllegalArgument exception if requirements fail for given value. */
@JvmInline
value class OrganisationNumber(val value: String) {

  init {
    require(value.length == 9) {
      "Value [$value] does not have 9 digits and is consequently not a valid organisation number"
    }
    require(value.all { it.isDigit() }) {
      "Value [$value] does not consist of only digits and is consequently not a valid organisation number"
    }

    require(value.hasValidOrgNumberCheckDigit()) {
      "Value [$value] does not have valid Modulus11 check digit and is consequently not a valid org number"
    }
  }

  override fun toString(): String = value

  companion object {

    internal val FACTORS = intArrayOf(3, 2, 7, 6, 5, 4, 3, 2)

    internal fun String.hasValidOrgNumberCheckDigit(): Boolean = orgNumberHasValidCheckDigit(this)

    private fun orgNumberHasValidCheckDigit(value: String): Boolean =
        value.last().asDigit() == Mod11.calcCheckDigit(value, *FACTORS)
  }
}
