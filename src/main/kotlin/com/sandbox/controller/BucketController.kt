package com.sandbox.controller

import com.sandbox.aws.S3Service
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.multipart.CompletedFileUpload
import io.swagger.v3.oas.annotations.media.Schema
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/bucket")
class BucketController(private val s3service: S3Service) {

    private val log: Logger = LoggerFactory.getLogger(BucketController::class.java)

    @Get("/list", produces = ["application/json"])
    fun listFiles(@Schema(defaultValue = "docs/123/") path: String): List<String> {
        return s3service.listFiles(path)
    }

    @Post(value = "/", consumes = [MediaType.MULTIPART_FORM_DATA], produces = [MediaType.TEXT_PLAIN])
    fun putFile(@Schema(defaultValue = "docs/123/") path: String, file: CompletedFileUpload): String {

        s3service.uploadObject(path, file)
        return "File uploaded successfully"
    }

    @Get(value = "/file/{filePath}", produces = [MediaType.APPLICATION_OCTET_STREAM])
    fun getFile(@Schema(defaultValue = "docs/123/example.txt") filePath: String): HttpResponse<ByteArray> {
        log.info("Downloading file: {}", filePath)
        val byteArray = s3service.downloadObject(filePath)
        val fileName = filePath.substringAfterLast('/')
        return HttpResponse.ok(byteArray)
            .header("Content-Disposition", "attachment; filename=\"$fileName\"")
    }
}