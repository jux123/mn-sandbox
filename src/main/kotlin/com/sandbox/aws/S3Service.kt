package com.sandbox.aws

import io.micronaut.http.multipart.CompletedFileUpload
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.*

@Singleton
class S3Service(private val s3: S3Client) {

    private val log: Logger = LoggerFactory.getLogger(S3Service::class.java)

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

    fun deleteFile(key: String) {
        try {
            val deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build()
            s3.deleteObject(deleteObjectRequest)
        } catch (e: Exception) {
            throw RuntimeException("Failed to delete file: $key", e)
        }
    }

    fun deleteFolder(path: String) {
        try {
            val listObjectsRequest = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix(path)
                .delimiter("/")
                .build()
            val contents = s3.listObjectsV2(listObjectsRequest).contents()

            val objectIdentifiers = contents.map {
                log.info("Deleting file: {}", it.key())
                ObjectIdentifier.builder().key(it.key()).build()
            }

            val deleteObjectsRequest = DeleteObjectsRequest.builder()
                .bucket(bucketName)
                .delete(Delete.builder().objects(objectIdentifiers).build())
                .build()
            s3.deleteObjects(deleteObjectsRequest)
        } catch (e: Exception) {
            throw RuntimeException("Failed to delete folder: $path", e)
        }
    }
}