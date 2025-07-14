package com.sandbox.controller

import com.sandbox.service.ApiClient
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.random.Random

@Controller("/hello")
class TestController(
    private val apiClient: ApiClient
) {

    private val log: Logger = LoggerFactory.getLogger(TestController::class.java)

    @Get
    suspend fun test(): String {
        log.info("Testing API call...")
        val urls = listOf(
            "https://snactions.nazk.gov.ua/en/api/v4/person/" + Random.nextInt(100, 999),
            "https://webgate.ec.europa.eu/fsd/fsf/")
        return apiClient.makeCall(urls[1])
    }
}