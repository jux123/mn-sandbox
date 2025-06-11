package com.sandbox

import io.kotest.core.spec.style.StringSpec
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

@MicronautTest
class KotlinTest : StringSpec({

    "test stream" {
        val set = setOf("one", "two", "three")

        val groupByWithO = set.groupBy { it.contains('o') } //Map<Boolean, List<String>> LinkedHashMap

//        println("Elements with 'o': $groupByWithO")
//        println("Class: ${groupByWithO.javaClass.simpleName}")

        groupByWithO.forEach { (key, value) -> {
            println("Foreach: Group with 'o' in name: $key, elements: $value")
        }}

        for ((key, value) in groupByWithO) {
            println("For: Group with 'o' in name: $key, elements: $value")
        }
    }

})