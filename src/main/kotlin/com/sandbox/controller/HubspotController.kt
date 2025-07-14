package com.sandbox.controller

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.HttpClient
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/token")
class HubspotController(
    private val httpClient: HttpClient,
    @Property(name = "hubspot.api-url") private val hubspotUrl: String,
    @Property(name = "hubspot.access-token") private val hubspotAccessToken: String) {

    private val log: Logger = LoggerFactory.getLogger(HubspotController::class.java)

    @Get
    @ExecuteOn(TaskExecutors.BLOCKING)
    fun getVisitorIdToken(): String {
        val body = mapOf(
            "firstName" to "Gob",
            "lastName" to "Bluth",
            "email" to "visitor-email@example.com"
        )
        val request = HttpRequest.POST("$hubspotUrl/conversations/v3/visitor-identification/tokens/create", body)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer $hubspotAccessToken")


        val tokenJson = httpClient.toBlocking().retrieve(request)
        return tokenJson
    }

}