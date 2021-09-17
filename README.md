# nor-types
Validated tiny types for Norwegian domain concepts in Kotlin language.

This library contains value classes for common Norwegian domain types. "value classes" in Kotlin are used to create a
wrapper around some type, giving it certain properties.
Inspired by domain driven design (value objects), they are immutable classes that disavow the concept of identity for
their instances.
In other words, two instances of a value class with the same fields are indistinguishable for all purposes
so using references of these objects makes no sense.

A tiny type is a simply lightweight value class with only one field of primitive type.


This gives the following advantages:

* The tiny type tells the developer what the item means in the domain.
* Type checking removes potential of erroneous parameter order in functions that have several parameters of the same primitive type.
* Validation rules can be placed in init block of type to make sure it cannot be instantiated with invalid state.
The idea is that once you instantiate use this type in your code you are guaranteed that it never contain an invalid
value so no further validation is needed later in application flow.
* Value classes are optimized and does not suffer the performance hit you do when boxing primitives in regular classes.


### Reference
https://kotlinlang.org/docs/inline-classes.html


## Usage
Instantiating types with invalid values throws IllegalArgumentException and must be handled accordingly using try/catch,
either (arrow-kt) etc.

E.g.
```
try{ BirthNumber("") }
catch(t: Throwable) {
    // Handle throwable
}
```
