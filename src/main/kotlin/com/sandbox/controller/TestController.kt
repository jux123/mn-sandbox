package com.sandbox.controller

import com.sandbox.service.ApiClient
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/hello")
class TestController(
    private val apiClient: ApiClient
) {

    private val log: Logger = LoggerFactory.getLogger(TestController::class.java)

    @Get
    suspend fun test(): String {
        log.info("Testing API call...")
        return apiClient.makeCall("https://www.google.com/")
    }
}