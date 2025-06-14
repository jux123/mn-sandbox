package com.sandbox

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class KotlineCoroutineTest {

    @Test
    @DisplayName("Test coroutine in runBlocking")
    fun testCoroutine1() {
        runBlocking {
            doSomething()
        }
        println("Test finished!")
    }

    @Test
    @DisplayName("Test coroutine in async")
    fun testCoroutine2() = runBlocking {
        val job = launch {
            doSomething()
        }
        println("Continuing test ...")
        println("Job completed: ${job.isCompleted}")
        delay(1500)
        println("Job completed: ${job.isCompleted}")
        println("Test finished!")
    }

    @Test
    @DisplayName("Test coroutine returning values in sync")
    fun testCoroutine3() = runBlocking {
        val result1 = getSomething()
        val result2 = getSomethingLonger()
        println("Result 1: $result1, Result 2: $result2")
        println("Test finished!")
    }

    @Test
    @DisplayName("Test coroutine returning values in async")
    fun testCoroutine4() = runBlocking {
        val result1 = async { getSomething() }
        val result2 = async { getSomethingLonger() }
        println("Result 1: ${result1.await()}")
        println("Result 2: ${result2.await()}")
        println("Test finished!")
    }

    @Test
    @DisplayName("Test coroutine returning values in async with lazy start")
    fun testCoroutine5() = runBlocking {
        val result1 = async(start = CoroutineStart.LAZY) {
            getSomething()
        }

        println("Do something before")
        result1.start()
        println("Do something between")
        println("Result 1: ${result1.await()}")
        println("Test finished!")
    }

    suspend fun doSomething() {
        println("Starting task...")
        delay(1000)
        println("Task done!")
    }

    suspend fun getSomething(): Int {
        println("Starting get task...")
        delay(1000)
        println("Task get done!")

        return 10;
    }

    suspend fun getSomethingLonger(): Int {
        println("Starting get longer task...")
        delay(1500)
        println("Task get longer done!")

        return 20;
    }
}