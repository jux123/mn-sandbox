package com.sandbox.service

import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import jakarta.inject.Singleton
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.withTimeout
import org.slf4j.LoggerFactory


@Singleton
class ApiClient(private val httpClient: HttpClient) {

    suspend fun makeCall(url: String): String {
        return try {
            withTimeout(3) {
                val request = HttpRequest.GET<Any>(url).headers(headers)
                val response = httpClient.exchange(request, ByteArray::class.java).awaitSingle()
                val contentType = response.contentType.orElse(null)

                if (contentType == null || !contentType.name.startsWith("text/html", ignoreCase = true)) {
                    throw RuntimeException("Content-Type is not text/html: $contentType")
                }

                val charset = response.characterEncoding ?: Charsets.UTF_8
                val htmlContent = response.body().toString(charset)

                extractData(htmlContent, url)
            }
        } catch (e: Exception) {
            log.error("Failed to make API call: $url", e)
            throw  e
        }
    }

    private fun extractData(htmlContent: String, link: String): String {
        return "url: $link\n data: ${htmlContent.substring(0, 8)}..."
    }

    private val headers = mapOf<CharSequence, CharSequence>(
        HttpHeaders.USER_AGENT to "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36",
        HttpHeaders.ACCEPT to "*/*"
    )

    companion object {
        val log = LoggerFactory.getLogger(ApiClient::class.java)!!
    }
}