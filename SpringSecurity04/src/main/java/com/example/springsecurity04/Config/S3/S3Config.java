package com.example.springsecurity04.Config.S3;

import com.example.springsecurity04.Table.Test.FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Config {

    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretKey);

        return S3Client.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }

    // Getter for bucket name
    public String getBucketName() {
        return bucketName;
    }

    @Bean
    public S3Presigner s3Presigner() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretKey);
        return S3Presigner.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }
}
