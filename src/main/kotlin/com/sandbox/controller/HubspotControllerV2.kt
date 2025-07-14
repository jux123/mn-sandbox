package com.sandbox.controller

import com.sandbox.service.HubspotClient
import com.sandbox.service.IdTokenResponse
import com.sandbox.service.Visitor
import io.micronaut.context.annotation.Property
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/token2")
class HubspotControllerV2(
    private val hubspotClient: HubspotClient,
    @Property(name = "hubspot.api-url") private val hubspotUrl: String,
    @Property(name = "hubspot.access-token") private val hubspotAccessToken: String) {

    private val log: Logger = LoggerFactory.getLogger(HubspotControllerV2::class.java)

    @Get
    suspend fun getVisitorIdToken(): String {
        val body = mapOf(
            "firstName" to "Gob",
            "lastName" to "Bluth",
            "email" to "visitor-email@example.com"
        )

        val visitor = Visitor("Gob", "Bluth", "visitor-email@example.com")

        val idToken = hubspotClient.createIdToken(visitor, "Bearer $hubspotAccessToken")
//        log.debug("Got response: {}", idToken)
        return idToken
    }

}