package com.sandbox.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.server.types.files.StreamedFile
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.ByteArrayInputStream
import java.io.InputStream

@Controller("/download")
class DownloadController {

    private val log: Logger = LoggerFactory.getLogger(BucketController::class.java)

    val docx: ByteArray = this::class.java.classLoader.getResource("test.docx")!!.readBytes()
    val png: ByteArray = this::class.java.classLoader.getResource("test.png")!!.readBytes()

    @Get("/docx/byteArray")
    fun downloadByteArray(): HttpResponse<ByteArray> {
        log.info("Downloading byte array size: ${docx.size}")
        return HttpResponse.ok(docx)
            .header("Content-Disposition", "attachment; filename=test.docx")
    }

    @Get("/docx/streamedFile")
    fun downloadStreamedFile(): StreamedFile {
        log.info("Downloading byte array size: ${docx.size}")
        return StreamedFile(
            ByteArrayInputStream(docx),
            MediaType.of("application/x-tika-ooxml")
        )
    }

    @Get("/png/byteArray")
    fun downloadByteArrayPng(): HttpResponse<ByteArray> {
        log.info("Downloading byte array size: ${png.size}")
        return HttpResponse.ok(png)
            .header("Content-Disposition", "attachment; filename=test.png")
    }

    @Get("/png/streamedFile")
    fun downloadStreamedFilePng(): StreamedFile {
        log.info("Downloading byte array size: ${png.size}")
        return StreamedFile(
            ByteArrayInputStream(png),
            MediaType.of("image/png")
        )
    }
}