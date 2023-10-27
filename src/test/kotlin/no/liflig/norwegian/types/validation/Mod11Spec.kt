package no.liflig.norwegian.types.validation

import io.kotest.core.spec.style.DescribeSpec
import kotlin.test.assertEquals
import no.liflig.norwegian.types.BirthNumber
import no.liflig.norwegian.types.OrganisationNumber

object Mod11Spec :
    DescribeSpec({
      describe("Calculation of Modulus11 checksum digits") {
        it("returns the first check digit for birth number (second last digit)") {
          assertEquals(
              1, Mod11.calcCheckDigit("02026600114", *BirthNumber.FACTORS_1)) // Johhny Max Tøsdal
          assertEquals(
              4, Mod11.calcCheckDigit("24076900147", *BirthNumber.FACTORS_1)) // Aksel Web Opdahl
        }

        it("returns the second check digit for birth number (last digit)") {
          assertEquals(
              4, Mod11.calcCheckDigit("02026600114", *BirthNumber.FACTORS_2)) // Johhny Max Tøsdal
          assertEquals(
              7, Mod11.calcCheckDigit("24076900147", *BirthNumber.FACTORS_2)) // Aksel Web Opdahl
        }

        it("returns the check digit for organisation number (last digit)") {
          assertEquals(
              5,
              Mod11.calcCheckDigit(
                  "999523625", *OrganisationNumber.FACTORS)) // Finans Norge Forsikringsdrift
          assertEquals(
              9,
              Mod11.calcCheckDigit("988097039", *OrganisationNumber.FACTORS)) // Capra Consulting AS
        }
      }
    })
