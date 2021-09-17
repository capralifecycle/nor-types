package no.liflig.norwegian.types

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.jvm.isAccessible
import kotlin.test.assertFailsWith

object NorwegianPhoneNumberSpec : Spek({

    describe("Creation of Norwegian phone number object") {

        context("of phone number") {

            it("throws exception for invalid numbers") {
                val invalidNumbers = listOf(
                    "1",
                    "Trond",
                    "123456789",
                    "12-34-56-78",
                    "+47123456789",
                    "+4812345678",
                    "-4712345678",
                    "0047123456789",
                    "004812345678"
                )
                invalidNumbers.forEach {
                    assertFailsWith<IllegalArgumentException> {
                        NorwegianPhoneNumber.of(it)
                    }
                }
            }

            it(
                "throws InvocationTargetException (with IllegalArgumentException as cause) " +
                    "if external library (e.g. Serialization frameworks) uses reflection to call constructor"
            ) {
                assertFailsWith<InvocationTargetException> {
                    val constructor = NorwegianPhoneNumber::class.constructors.toList().get(0)
                    constructor.isAccessible = true
                    constructor.call("INVALID")
                }
            }

            it("does not throw exception for valid numbers") {
                val validNumbers = listOf(
                    "12345678",
                    " 12345678 ",
                    "12 34 56 78",
                    "+4712345678",
                    "+47 12 34 56 78",
                    "+47 12345678",
                    "004712345678",
                    "00 47 12 34 56 78"
                )

                validNumbers.forEach {
                    NorwegianPhoneNumber.of(it)
                }
            }
        }
    }
})
