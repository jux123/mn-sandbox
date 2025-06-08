package com.sandbox.controller

import com.sandbox.aws.S3Service
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.swagger.v3.oas.annotations.media.Schema

@Controller("/bucket")
class BucketController(private val s3service: S3Service) {

    @Get("/list", produces = ["application/json"])

    fun listFiles(@Schema(defaultValue = "docs/123/") path: String): List<String> {
        return s3service.listFiles(path)
    }
}