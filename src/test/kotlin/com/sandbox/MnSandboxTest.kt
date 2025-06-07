package com.sandbox
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client

@MicronautTest
class MnSandboxTest(
    private val application: EmbeddedApplication<*>,
    @Client("/") private val httpClient: HttpClient
    ): StringSpec({

    "test the server is running" {
        assert(application.isRunning)
    }

    "test /hello endpoint" {
        val response = httpClient.toBlocking().retrieve("/hello")
        response shouldBe "Hello world"
    }
})
