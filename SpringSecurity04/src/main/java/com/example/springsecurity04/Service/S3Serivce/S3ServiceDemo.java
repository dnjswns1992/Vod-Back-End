//package com.example.springsecurity04.Service.S3Serivce;
//
//import com.example.springsecurity04.Config.S3.S3Config;
//import com.example.springsecurity04.Request.CompleteMultipartUploadRequestCustom;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import software.amazon.awssdk.core.sync.RequestBody;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.*;
//import software.amazon.awssdk.services.s3.presigner.S3Presigner;
//import software.amazon.awssdk.services.s3.presigner.model.UploadPartPresignRequest;
//
//import java.io.IOException;
//import java.net.URL;
//import java.nio.file.Paths;
//import java.time.Duration;
//import java.util.List;
//import java.util.stream.Collectors;
//@Service
//@Slf4j
//public class S3ServiceDemo {
//
//    private final S3Client s3Client;
//    private final String bucketName;
//
//    private final S3Presigner s3Presigner;
//
//    S3ServiceDemo(S3Config s3Config,S3Presigner s3Presigner) {
//
//        this.s3Client = s3Config.s3Client();
//        this.bucketName = s3Config.getBucketName();
//        this.s3Presigner = s3Presigner;
//    }
//
//    /* 이미지 업로드 메서드 */
//    public String upload(MultipartFile file) throws IOException {
//        String key = String.valueOf(Paths.get(System.currentTimeMillis() + "-" + file.getOriginalFilename()));
//        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                .bucket(bucketName)
//                .key(key)
//                .build();
//        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
//        URL fileUri = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(key));
//        return fileUri.toString();
//    }
//
//    // ObjectKey : 파일의 이름
//    public String createMultipartUpload(String objectKey) {
//        CreateMultipartUploadRequest createMultipartUploadRequest = CreateMultipartUploadRequest.builder()
//                .bucket(bucketName)
//                .key(objectKey)
//                .build();
//
//        CreateMultipartUploadResponse response = s3Client.createMultipartUpload(createMultipartUploadRequest);
//        return response.uploadId();
//    }
//    /* 이 부분의 코드는 실제로 S3에 파일을 업로드하는 것은 아니고,
//    앞서 클라이언트가 부분적으로 업로드한 각 파트의 정보를 S3에 전달하여 이 파트들을 결합해 최종 파일로 완성하라는 요청을 보내는 역할을 합니다.
//     즉, S3에게 "이 파트들을 결합하여 하나의 파일로 만들어라"라는 명령을 내리는 것입니다. */
//
//    public void completeMultipartUpload(CompleteMultipartUploadRequestCustom customRequest) {
//        List<CompletedPart> completedParts = customRequest.getCompletedParts().stream()
//                .map(dto -> {
//                    String eTag = dto.getETag();
//                    log.info("Original ETag: {}", eTag); // ETag 값 로그 출력
//                    eTag = eTag.replace("\"", ""); // 따옴표 제거
//                    log.info("Sanitized ETag: {}", eTag); // 제거 후 ETag 값 로그 출력
//                    if (dto.getPartNumber() < 1) {
//                        throw new IllegalArgumentException("PartNumber must be >= 1");
//                    }
//                    return CompletedPart.builder()
//                            .eTag(eTag)
//                            .partNumber(dto.getPartNumber())
//                            .build();
//                })
//                .collect(Collectors.toList());
//
//        CompleteMultipartUploadRequest completeMultipartUploadRequest = CompleteMultipartUploadRequest.builder()
//                .bucket(bucketName)
//                .key(customRequest.getFileName())
//                .uploadId(customRequest.getUploadId())
//                .multipartUpload(CompletedMultipartUpload.builder().parts(completedParts).build())
//                .build();
//
//        // 디버깅용 로그 추가
//        log.info("CompleteMultipartUploadRequest: {}", completeMultipartUploadRequest);
//
//        try {
//            s3Client.completeMultipartUpload(completeMultipartUploadRequest);
//        } catch (S3Exception e) {
//            log.error("S3Exception: {}", e.awsErrorDetails().errorMessage());
//            log.error("Request ID: {}", e.requestId());
//            log.error("HTTP Status Code: {}", e.statusCode());
//            log.error("AWS Error Code: {}", e.awsErrorDetails().errorCode());
//            log.error("Error Type: {}", e.awsErrorDetails().errorMessage());
//            log.error("Service Name: {}", e.awsErrorDetails().serviceName());
//            throw e;
//        }
//    }
//    public String generatePresignedUrl(String objectKey, int partNumber, String uploadId) {
//        UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
//                .bucket(bucketName)
//                .key(objectKey)
//                .uploadId(uploadId)
//                .partNumber(partNumber)
//                .build();
//
//        UploadPartPresignRequest presignRequest = UploadPartPresignRequest.builder()
//                .signatureDuration(Duration.ofMinutes(600))
//                .uploadPartRequest(uploadPartRequest)
//                .build();
//
//        URL url = s3Presigner.presignUploadPart(presignRequest).url();
//        return url.toString();
//    }
//
//
//}
