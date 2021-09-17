package no.fnf.helsenett.types

import no.fnf.helsenett.types.BirthNumber.Companion.hasValidBirthNumberCheckDigits
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertTrue

class BirthNumberSpec : Spek({

    describe("Creation of birth number") {

        describe("Modulus11 checksum validation") {

            context("checks if the last two digits matches the calculated check digits and") {

                it("returns true if they match") {
                    assertTrue("02026600114".hasValidBirthNumberCheckDigits()) // Johnny Max Tøsdal
                    assertTrue("24076900147".hasValidBirthNumberCheckDigits()) // Aksel Web Opdahl
                }

                it("returns true if they match for D-nummer ([D]irektoratet for sjømenn)") {
                    assertTrue("47086303651".hasValidBirthNumberCheckDigits())
                }

                it("returns false if the do not match") {
                    assertTrue(!"02026600115".hasValidBirthNumberCheckDigits())
                    assertTrue(!"24076900148".hasValidBirthNumberCheckDigits())
                }
            }
        }
    }
})
