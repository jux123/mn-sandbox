package com.sandbox.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller
class HelloWorldController {

    private val log: Logger = LoggerFactory.getLogger(HelloWorldController::class.java)

    @Get("/hello", produces = [MediaType.TEXT_PLAIN])
    fun hello(): String {
        log.info("Hello, World!")
        return "Hello world"
    }
}