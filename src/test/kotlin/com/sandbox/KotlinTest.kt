package com.sandbox

import io.kotest.core.spec.style.StringSpec
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import kotlinx.coroutines.delay

@MicronautTest
class KotlinTest : StringSpec({

    "test stream" {
        val set = setOf("one", "two", "three")

        val groupByWithO = set.groupBy { it.contains('o') } //Map<Boolean, List<String>> LinkedHashMap

        groupByWithO.forEach { (key, value) -> {
            println("Foreach: Group with 'o' in name: $key, elements: $value")
        }}

        for ((key, value) in groupByWithO) {
            println("For: Group with 'o' in name: $key, elements: $value")
        }
    }

    "test higher order function" {
        val numbers = listOf(1, 2, 3, 4, 5)


        fun calcList(numbers: List<Int>, a: Int, fx: (Int, Int) -> Int): List<Int> {
            val result: MutableList<Int>  = mutableListOf()
            for (number in numbers) {
                result.add(fx(number, a))
            }
            return result
        }

        val newNumbers = calcList(numbers, 3) { number, a -> number * a }

        println("Old numbers: $numbers")
        println("New numbers: $newNumbers")
    }

    "test higher order function second" {
        val numbers = listOf(1, 2, 3, 4, 5)


        fun calcList(numbers: List<Int>, a: Int, fx: (Int, Int) -> Int): List<Int> {
            val result: MutableList<Int>  = mutableListOf()
            for (number in numbers) {
                result.add(fx(number, a))
            }
            return result
        }

        fun multiply(number: Int, a: Int): Int { return number * a }

        val newNumbers = calcList(numbers, 3, ::multiply)

        println("Old numbers: $numbers")
        println("New numbers: $newNumbers")
    }

    "test coroutine: default" {
        // Kotest tests automatically run in coroutine blocking context
        doSomething()
        println("Test finished!")
    }
}) {
    companion object {

        suspend fun doSomething() {
            println("Starting task...")
            delay(1000)
            println("Task done!")
        }
    }
}