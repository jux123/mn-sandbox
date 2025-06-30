package com.sandbox

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class OptionsService {
    suspend fun getOptions2(delay: Long, levels: Deferred<List<String>>): List<String> = coroutineScope {
        println("Starting get options2")
        delay(delay)
        return@coroutineScope levels.await().map { "Option2 $it" }
    }
}