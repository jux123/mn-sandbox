package com.sandbox.aws

import io.micronaut.http.multipart.CompletedFileUpload
import jakarta.inject.Singleton
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectResponse
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.nio.file.Path
import kotlin.io.path.Path

@Singleton
class S3Service(private val s3: S3Client) {

    val bucketName = "test-bucket-ace69d39-97c8-485a-9a87-b34964a83ca8"

    fun listFiles(path: String): List<String> {
        val listObjectsRequest = ListObjectsV2Request.builder()
            .bucket(bucketName)
            .prefix(path)
            .delimiter("/")
            .build()
        return s3.listObjectsV2(listObjectsRequest).contents().map { it.key() }
    }

     fun uploadObject(path: String, file: CompletedFileUpload) {
        val putRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(path + file.filename)
            .build()
         val requestBody = RequestBody.fromBytes(file.bytes)
        s3.putObject(putRequest, requestBody)
    }

    fun downloadObject(key: String): ByteArray {
        val getRequest = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build()
        val objectAsBytes = s3.getObjectAsBytes(getRequest)
        return objectAsBytes.asByteArray()

    }

}