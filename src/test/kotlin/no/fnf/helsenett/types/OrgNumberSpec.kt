package no.fnf.helsenett.types

import no.fnf.helsenett.types.OrganisationNumber.Companion.hasValidOrgNumberCheckDigit
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertTrue

class OrgNumberSpec : Spek({

    describe("Creation of organization number") {

        describe("Modulus11 checksum validation") {

            context("of organisation number checks if the last digit matches the calculated check digit and") {

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
