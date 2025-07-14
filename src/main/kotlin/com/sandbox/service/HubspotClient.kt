package com.sandbox.service

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.micronaut.serde.annotation.Serdeable

@Client("hubspot")
interface HubspotClient {

    //FIXME: same problem as with controllers -- in mn v4.9 the path value is not being processed by mn
    @Post("/conversations/v3/visitor-identification/tokens/create")
    suspend fun createIdToken(
        @Body visitor: Visitor,
        @Header("Authorization") authorization: String
    ): String
}

@Serdeable
data class Visitor(
    val firstName: String,
    val lastName: String,
    val email: String
)

@Serdeable
data class IdTokenResponse(
    val token: String
)