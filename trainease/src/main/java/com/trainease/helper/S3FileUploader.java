package com.trainease.helper;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;


public class S3FileUploader {

    public static void uploadFileToS3(MultipartFile file, String folderPath) throws IOException {
        String awsAccessKeyId = "your-access-key";
        String awsSecretAccessKey = "your-secret-key";
        String bucketName = "your-bucket-name";
        String objectKey = folderPath + file.getOriginalFilename();

        AmazonS3 s3Client = AmazonS3Client.builder()
                .withRegion("region-name")
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(awsAccessKeyId, awsSecretAccessKey)))
                .build();

        byte[] bytes = file.getBytes();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(bytes.length);

        PutObjectRequest request = new PutObjectRequest(bucketName, objectKey, new ByteArrayInputStream(bytes), metadata);
        s3Client.putObject(request);
    }

}
