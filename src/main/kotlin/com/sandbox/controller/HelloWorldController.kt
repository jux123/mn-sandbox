package com.sandbox.controller

import com.sandbox.service.ApiClient
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/api")
open class HelloWorldController(
    private val apiClient: ApiClient
) {

    private val log: Logger = LoggerFactory.getLogger(HelloWorldController::class.java)

    @Get(value ="/hello", produces = [MediaType.TEXT_PLAIN])
    fun hello(): String {
        log.info("Hello, World!")
        return "Hello world"
    }
//
//    @Get("/call/{url}", produces = [MediaType.TEXT_PLAIN])
//    suspend fun apiCall(url: String): String {
//
//        return apiClient.makeCall(url)
//    }

}