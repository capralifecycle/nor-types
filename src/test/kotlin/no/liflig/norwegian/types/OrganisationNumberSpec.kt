package no.liflig.norwegian.types

import no.liflig.norwegian.types.OrganisationNumber.Companion.hasValidOrgNumberCheckDigit
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class OrganisationNumberSpec : Spek({

    describe("Creation of organisation number") {

        it("returns validated tiny type") {
            OrganisationNumber.of("999523625") //  Finans Norge Forsikringsdrift
        }

        it("throws IllegalArgumentException if input does not have length 9") {
            assertFailsWith<IllegalArgumentException> {
                OrganisationNumber.of("99952362")
            }
        }

        it("throws IllegalArgumentException if input is not all digits") {
            assertFailsWith<IllegalArgumentException> {
                OrganisationNumber.of("99952362X")
            }
        }

        it("throws IllegalArgumentException if input does not have valid check digit") {
            assertFailsWith<IllegalArgumentException> {
                OrganisationNumber.of("999523626")
            }
        }

        describe("Modulus11 checksum validation") {

            context("checks if the last digit matches the calculated check digit and") {

                it("returns true if they match") {
                    assertTrue("999523625".hasValidOrgNumberCheckDigit()) // Finans Norge Forsikringsdrift
                    assertTrue("988097039".hasValidOrgNumberCheckDigit()) // Capra Consulting AS
                    assertTrue("085082981".hasValidOrgNumberCheckDigit()) // Random Number starting with '0'
                }

                it("returns false if they do not match") {
                    assertTrue(!"997725647".hasValidOrgNumberCheckDigit())
                    assertTrue(!"988097030".hasValidOrgNumberCheckDigit())
                }
            }
        }
    }
})
