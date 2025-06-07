package com.sandbox.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller
class HelloWorldController {

    @Get("/hello", produces = [MediaType.TEXT_PLAIN])
    fun hello(): String {
        return "Hello world"
    }
}