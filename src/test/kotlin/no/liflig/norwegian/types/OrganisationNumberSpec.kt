package no.liflig.norwegian.types

import no.liflig.norwegian.types.OrganisationNumber.Companion.hasValidOrgNumberCheckDigit
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.jvm.isAccessible
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class OrganisationNumberSpec : Spek({

    describe("Creation of organisation number") {

        it("returns validated tiny type") {
            OrganisationNumber("925906093") // Liflig By Capra AS
        }

        it("throws IllegalArgumentException if input does not have length 9") {
            assertFailsWith<IllegalArgumentException> {
                OrganisationNumber("92590609")
            }
        }

        it("throws IllegalArgumentException if input is not all digits") {
            assertFailsWith<IllegalArgumentException> {
                OrganisationNumber("92590609X")
            }
        }

        it("throws IllegalArgumentException if input does not have valid check digit") {
            assertFailsWith<IllegalArgumentException> {
                OrganisationNumber("925906094")
            }
        }

        it(
            "throws InvocationTargetException (with IllegalArgumentException as cause) " +
                "if external library (e.g. Serialization frameworks) uses reflection to call constructor"
        ) {
            assertFailsWith<InvocationTargetException> {
                val constructor = OrganisationNumber::class.constructors.toList().get(0)
                constructor.isAccessible = true
                constructor.call("INVALID")
            }
        }

        describe("Modulus11 checksum validation") {

            context("checks if the last digit matches the calculated check digit and") {

                it("returns true if they match") {
                    assertTrue("925906093".hasValidOrgNumberCheckDigit()) // Liflig By Capra AS
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
