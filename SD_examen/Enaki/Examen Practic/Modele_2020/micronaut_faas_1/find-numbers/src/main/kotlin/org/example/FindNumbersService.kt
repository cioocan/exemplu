package org.example

import io.micronaut.context.annotation.Prototype
import kotlin.random.Random


sealed class SetADT {
    abstract val elements: List<Int>

    fun forEach(action: (Int) -> Unit) {
        elements.forEach(action)
    }
}

data class ListSet(override val elements: List<Int>) : SetADT()

@Prototype
class FindNumbersService {
    private fun generateRandomSet(): SetADT {
        val randomNumbers = List(100) { Random.nextInt(1, 100) }
        return ListSet(randomNumbers)
    }

    private val multimeA = generateRandomSet()
    private val multimeB = generateRandomSet()

    fun findNumbers(): List<Pair<Int, Int>>{
        val result = mutableListOf<Pair<Int, Int>>()

        multimeA.forEach {
            a ->
                multimeB.forEach loop@{
                    b->
                        if( a * b == a + ( b * 3)) result += Pair(a, b)
                }
        }

        return result.toList()
    }

}