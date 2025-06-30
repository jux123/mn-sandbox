package com.sandbox

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class KotlinCoroutineTest {

    val optionsService = OptionsService() // Assuming this is a singleton

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

    class Review (
        val levels: List<String>,
        val options: List<String>
    )

    @Test
    fun testAsyncNoHigherOrderFunction() = runBlocking {

        val review = getReview();
        println("Review: ${review.options}")

        val review2 = getReview2();
        println("Review 2: ${review2.options}")
    }

    @Test
    fun testAsyncRefactoredHigherOrderFunction() = runBlocking {

        val review = getRefactoredReview();
        println("Review: ${review.options}")

        val review2 = getRefactoredReview2();
        println("Review 2: ${review2.options}")
    }

    suspend fun getReviewCommon(
        getOptionsFn: suspend (delay: Long, levels: Deferred<List<String>>) -> List<String>
    ): Review = coroutineScope {
        println("Starting getReview")
        val levels = async {
            getLevels(1000)
        }

        val options = getOptionsFn(500, levels)

        return@coroutineScope Review(levels.await(), options)
    }

    suspend fun getRefactoredReview(): Review =
        getReviewCommon(::getOptions)

    suspend fun getRefactoredReview2(): Review =
        getReviewCommon(optionsService::getOptions2)

    /**
     * @coroutineScope
     * This function is designed for concurrent decomposition of work.
     * When any child coroutine in this scope fails, this scope fails,
     * cancelling all the other children
     */
    suspend fun getReview(): Review = coroutineScope {
        println("Starting getReview")
        val levels = async {
            getLevels(1000)
        }

        val options = getOptions(500, levels)

        return@coroutineScope Review(levels.await(), options)
    }

    suspend fun getReview2(): Review = coroutineScope {
        println("Starting getReview")
        val levels = async {
            getLevels(1000)
        }

        val options = optionsService.getOptions2(500, levels)

        return@coroutineScope Review(levels.await(), options)
    }

    suspend fun getLevels(delay: Long): List<String> {
        println("Starting get levels")
        delay(delay)
        return listOf("Level 1", "Level 2", "Level 3")
    }

    suspend fun getOptions(delay: Long, levels: Deferred<List<String>>): List<String> = coroutineScope {
        println("Starting get options")
        delay(delay)
        return@coroutineScope levels.await().map { "Option $it" }
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