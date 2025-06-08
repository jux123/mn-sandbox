package com.sandbox.aws

import jakarta.inject.Singleton
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.nio.file.Path

@Singleton
class S3Client(private val s3: S3Client) {

    fun listFiles(bucketName: String): List<String> {
        val listObjectsRequest = ListObjectsV2Request.builder()
            .bucket(bucketName)
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