package com.sandbox.service

import com.sandbox.controller.HubspotControllerV2
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpRequest
import io.micronaut.http.annotation.Filter
import io.micronaut.http.filter.ClientFilterChain
import io.micronaut.http.filter.HttpClientFilter
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
@Filter(serviceId = ["hubspot"])
class HubspotClientFilter: HttpClientFilter {
    private val log: Logger = LoggerFactory.getLogger(HubspotClientFilter::class.java)

    override fun doFilter(
        request: MutableHttpRequest<*>?,
        chain: ClientFilterChain?
    ): Publisher<out HttpResponse<*>?>? {
        log.debug("Received request: {}", request)
        log.debug("Body: ", request?.body)


        return chain?.proceed(request)
    }


}