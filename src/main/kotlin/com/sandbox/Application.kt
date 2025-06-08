package com.sandbox

import io.micronaut.runtime.Micronaut.run
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info


@OpenAPIDefinition(
	info = Info(
		title = "sandbox",
		version = "0.1"
	)
)
class Application

fun main(args: Array<String>) {
	run(*args)
}

