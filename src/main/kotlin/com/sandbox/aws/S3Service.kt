package com.sandbox.aws

import jakarta.inject.Singleton
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.nio.file.Path

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

     fun uploadObject(bucket: String, key: String, filePath: Path) {
        val putRequest = PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .build()
        s3.putObject(putRequest, filePath)
    }

    fun downloadObject(bucket: String, key: String, destination: Path) {
        val getRequest = GetObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .build()
        s3.getObject(getRequest, destination)
    }

}