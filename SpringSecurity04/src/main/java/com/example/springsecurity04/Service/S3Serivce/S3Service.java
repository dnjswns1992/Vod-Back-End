package com.example.springsecurity04.Service.S3Serivce;
import com.example.springsecurity04.Config.S3.S3Config;
import com.example.springsecurity04.Dto.Video.UploadMainTitleDto;
import com.example.springsecurity04.Dto.Video.VideoDto;
import com.example.springsecurity04.Repository.VideoRepository.UploadMainTitleRepository;
import com.example.springsecurity04.Repository.VideoRepository.VideoRepository;
import com.example.springsecurity04.Table.Video.UploadMainTitleEntity;
import com.example.springsecurity04.Table.Video.VideoEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName;
    private final VideoRepository videoRepository;
    private final UploadMainTitleRepository uploadMainTitleRepository;

    @Autowired
    public S3Service(S3Config s3Config, VideoRepository videoRepository, UploadMainTitleRepository uploadMainTitleRepository) {
        this.s3Client = s3Config.s3Client();
        this.bucketName = s3Config.getBucketName();
        this.videoRepository = videoRepository;
        this.uploadMainTitleRepository = uploadMainTitleRepository;
    }

    /* 이미지 업로드 메서드 */
    public String upload(MultipartFile file) throws IOException {
        String key = String.valueOf(Paths.get(System.currentTimeMillis() + "-" + file.getOriginalFilename()));
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
        URL fileUri = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(key));
        return fileUri.toString();
    }




    /* 동영상  업로드 메서드 */
    public String uploadLargeFile(MultipartFile videoFile, VideoDto videoDto, MultipartFile imageFile) throws IOException {
        String videoKey = String.valueOf(Paths.get(System.currentTimeMillis() + "-" + videoFile.getOriginalFilename()));
        String imageKey = String.valueOf(Paths.get(System.currentTimeMillis() + "-" + imageFile.getOriginalFilename()));

        // Video upload
        CreateMultipartUploadRequest createMultipartUploadRequest = CreateMultipartUploadRequest.builder()
                .bucket(bucketName)
                .key(videoKey)
                .build();
        CreateMultipartUploadResponse response = s3Client.createMultipartUpload(createMultipartUploadRequest);
        String uploadId = response.uploadId();

        // Upload parts
        List<CompletedPart> completedParts = new ArrayList<>();
        long partSize = 5 * 1024 * 1024; // 5MB
        byte[] fileBytes = videoFile.getBytes();
        int partNumber = 1;
        for (int i = 0; i < fileBytes.length; i += partSize) {
            int end = Math.min(fileBytes.length, i + (int) partSize);
            byte[] partBytes = new byte[end - i];
            System.arraycopy(fileBytes, i, partBytes, 0, partBytes.length);

            UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
                    .bucket(bucketName)
                    .key(videoKey)
                    .uploadId(uploadId)
                    .partNumber(partNumber)
                    .build();
            UploadPartResponse uploadPartResponse = s3Client.uploadPart(uploadPartRequest, RequestBody.fromBytes(partBytes));

            completedParts.add(CompletedPart.builder()
                    .partNumber(partNumber)
                    .eTag(uploadPartResponse.eTag())
                    .build());

            partNumber++;
        }

        // Complete the multipart upload
        CompletedMultipartUpload completedMultipartUpload = CompletedMultipartUpload.builder()
                .parts(completedParts)
                .build();
        CompleteMultipartUploadRequest completeMultipartUploadRequest = CompleteMultipartUploadRequest.builder()
                .bucket(bucketName)
                .key(videoKey)
                .uploadId(uploadId)
                .multipartUpload(completedMultipartUpload)
                .build();
        s3Client.completeMultipartUpload(completeMultipartUploadRequest);

        URL videoFileUri = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(videoKey));

        // Image upload
        PutObjectRequest putImageRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(imageKey)
                .build();
        s3Client.putObject(putImageRequest, RequestBody.fromBytes(imageFile.getBytes()));
        URL imageFileUri = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(imageKey));

        // Save video details in database
        VideoEntity videoEntity = VideoEntity.builder()
                .title(videoDto.getTitle())
                .content(videoDto.getContent())
                .category(videoDto.getCategory())
                .ImageUrl(imageFileUri.toString())
                .videoUrl(videoFileUri.toString())
                .build();
        videoRepository.save(videoEntity);

        return videoFileUri.toString();
    }
    /* 메인 타이틀 업로드 */
    public ResponseEntity MainTitleUploadService(MultipartFile multipartFile, UploadMainTitleDto dto){

        ModelMapper mapper = new ModelMapper();
        try {
            String ImageUrl = upload(multipartFile);
            dto.setImageUrl(ImageUrl);
            UploadMainTitleEntity fileEntity = mapper.map(dto, UploadMainTitleEntity.class);
            uploadMainTitleRepository.save(fileEntity);

            return ResponseEntity.ok("등록 되었습니다");
        } catch (IOException e) {
            return ResponseEntity.status(400).body("등록 할 수 없습니다.");
        }

    }

}
