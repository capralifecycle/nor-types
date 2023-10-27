package no.liflig.norwegian.types

import io.kotest.core.spec.style.DescribeSpec
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.jvm.isAccessible
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import no.liflig.norwegian.types.BirthNumber.Companion.hasValidBirthNumberCheckDigits

class BirthNumberSpec :
    DescribeSpec({
      describe("Creation of birth number") {
        it("returns validated tiny type") {
          BirthNumber("02026600114") // Johnny Max Tøsdal
        }

        it("throws IllegalArgumentException if input does not have length 11") {
          assertFailsWith<IllegalArgumentException> { BirthNumber("0202660011") }
        }

        it("throws IllegalArgumentException if input is not all digits") {
          assertFailsWith<IllegalArgumentException> { BirthNumber("0202660011X") }
        }

        it("throws IllegalArgumentException if input does not have valid first check digit") {
          assertFailsWith<IllegalArgumentException> { BirthNumber("02026600124") }
        }

        it("throws IllegalArgumentException if input does not have valid second check digit") {
          assertFailsWith<IllegalArgumentException> { BirthNumber("02026600115") }
        }

        it(
            "throws InvocationTargetException (with IllegalArgumentException as cause) " +
                "if external library (e.g. Serialization frameworks) uses reflection to call constructor") {
              assertFailsWith<InvocationTargetException> {
                val constructor = BirthNumber::class.constructors.toList().get(0)
                constructor.isAccessible = true
                constructor.call("INVALID")
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
