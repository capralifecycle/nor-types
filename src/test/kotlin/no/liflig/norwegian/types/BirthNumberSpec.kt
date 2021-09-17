package no.liflig.norwegian.types

import no.liflig.norwegian.types.BirthNumber.Companion.hasValidBirthNumberCheckDigits
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class BirthNumberSpec : Spek({

    describe("Creation of birth number") {

        it("returns validated tiny type") {
            BirthNumber.of("02026600114") // Johnny Max Tøsdal
        }

        it("throws IllegalArgumentException if input does not have length 11") {
            assertFailsWith<IllegalArgumentException> {
                BirthNumber.of("0202660011")
            }
        }

        it("throws IllegalArgumentException if input is not all digits") {
            assertFailsWith<IllegalArgumentException> {
                BirthNumber.of("0202660011X")
            }
        }

        it("throws IllegalArgumentException if input does not have valid first check digit") {
            assertFailsWith<IllegalArgumentException> {
                BirthNumber.of("02026600124")
            }
        }

        it("throws IllegalArgumentException if input does not have valid second check digit") {
            assertFailsWith<IllegalArgumentException> {
                BirthNumber.of("02026600115")
            }
        }

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
