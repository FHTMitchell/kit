package kit.kboxes.option

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class OptionTest {

    data class Box<T>(val value: T)

    fun <T> toSingleCollection(arg: T?): Option<Collection<T>> =
            when (arg) {
                null -> None
                is Number, is String -> Some(setOf(arg))  // value type
                else -> Some(listOf(arg)) // not null
            }


    @Test
    fun `test option returns`() {

        val some1 = toSingleCollection("hello")
        val some2 = toSingleCollection(Box(1))
        val none = toSingleCollection(null)

        assertTrue( some1 is Some && some1.value is Set )
        assertTrue( some2 is Some && some2.value is List )
        assertTrue( none == None )

        val vals = listOf(some1, some2, none)
        println(vals)

        val valsWrap = toSingleCollection(vals)
        println(valsWrap)

    }




}