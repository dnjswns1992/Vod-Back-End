package com.example.springsecurity04.Service.S3Serivce;
import com.example.springsecurity04.Config.S3.S3Config;
import com.example.springsecurity04.Dto.Video.Animation.AnimationEpisodeEntityDto;
import com.example.springsecurity04.Dto.Video.UploadMainTitleDto;
import com.example.springsecurity04.Dto.Video.VideoDto;
import com.example.springsecurity04.Handler.WebSocketHandler.ProgressWebSocketHandler;
import com.example.springsecurity04.Repository.EpisodeRepository.AnimationEpisodeEntityRepository;
import com.example.springsecurity04.Repository.EpisodeRepository.DramaEpisodeRepository;
import com.example.springsecurity04.Table.EpsidoeEntity.AnimationEpisodeEntity;
import com.example.springsecurity04.Repository.VideoRepository.UploadMainTitleRepository;
import com.example.springsecurity04.Table.EpsidoeEntity.DramaEpisodeEntity;
import com.example.springsecurity04.Table.Video.UploadMainTitleEntity;
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
    private final UploadMainTitleRepository uploadMainTitleRepository;
    private final AnimationEpisodeEntityRepository animationEpisodeEntityRepository;
    private final DramaEpisodeRepository dramaEpisodeRepository;
    private final ProgressWebSocketHandler webSocketHandler;

    @Autowired
    public S3Service(S3Config s3Config, UploadMainTitleRepository uploadMainTitleRepository,
                     AnimationEpisodeEntityRepository animationEpisodeEntityRepository,
                     DramaEpisodeRepository dramaEpisodeRepository,ProgressWebSocketHandler webSocketHandler) {
        this.s3Client = s3Config.s3Client();
        this.bucketName = s3Config.getBucketName();
        this.uploadMainTitleRepository = uploadMainTitleRepository;
        this.animationEpisodeEntityRepository = animationEpisodeEntityRepository;
        this.dramaEpisodeRepository = dramaEpisodeRepository;
        this.webSocketHandler = webSocketHandler;
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

    public String uploadLargeFile(MultipartFile videoFile, VideoDto videoDto,MultipartFile imageFile,String sessionId) throws IOException {
        String videoKey = String.valueOf(Paths.get(System.currentTimeMillis() + "-" + videoFile.getOriginalFilename()));

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

            int progress = (int) (((double) (i + partBytes.length) / fileBytes.length) * 100);
            webSocketHandler.sendMessage(sessionId, progress);


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

         String videoFileUri = String.valueOf(s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(videoKey)));
        String  imageFileUri = upload(imageFile);


        UploadMainTitleEntity uploadMainTitleEntity = uploadMainTitleRepository.findByTitleContainingAndGenre(videoDto.getTitle(),videoDto.getGenre()).get();

        if(uploadMainTitleEntity.getGenre().equals("애니")) {
            AnimationEpisodeEntity animationEpisode = AnimationEpisodeEntity.builder()
                    .episodeNumber(videoDto.getEpisodeNumber())
                    .imageUrl(imageFileUri)
                    .description(videoDto.getDescription())
                    .genre(videoDto.getGenre())
                    .videoUrl(videoFileUri)
                    .entity(uploadMainTitleEntity).build();

            animationEpisodeEntityRepository.save(animationEpisode);

            ///이거 수정해야 함. 2024 5 30
        }else if(uploadMainTitleEntity.getGenre().equals("영화")) {

            DramaEpisodeEntity dramaEpisodeEntity = DramaEpisodeEntity.builder()
                    .episodeNumber(videoDto.getEpisodeNumber())
                    .description(videoDto.getDescription())
                    .genre(videoDto.getGenre())
                    .ImageUrl(imageFileUri)
                    .videoUrl(videoFileUri)
                    .build();

            dramaEpisodeRepository.save(dramaEpisodeEntity);
        }

        return videoFileUri.toString();
    }


    /* 메인 타이틀 업로드 */
    public ResponseEntity MainTitleUploadService(MultipartFile multipartFile, UploadMainTitleDto dto,MultipartFile mainTitleImage){

        ModelMapper mapper = new ModelMapper();
        try {
            String ImageUrl = upload(multipartFile);
            String mainTitle = upload(mainTitleImage);
            dto.setImageUrl(ImageUrl);
            dto.setMainTitleImageUrl(mainTitle);
            UploadMainTitleEntity fileEntity = mapper.map(dto, UploadMainTitleEntity.class);

            uploadMainTitleRepository.save(fileEntity);

            return ResponseEntity.ok("등록 되었습니다");
        } catch (IOException e) {
            return ResponseEntity.status(400).body("등록 할 수 없습니다.");
        }
    }
    /* 애니메이션 가져오는 서비스 */
    public List<UploadMainTitleEntity> animationVideoService(){
        List<UploadMainTitleEntity> animation = uploadMainTitleRepository.findAll();

        return animation;
    }
    public AnimationEpisodeEntityDto episodeAnimation(int id) {
        List<AnimationEpisodeEntity> episode = animationEpisodeEntityRepository.findByUploadMainTitleEntityUploadMainTitleEntityId(id);
        UploadMainTitleEntity uploadMainTitleEntity = uploadMainTitleRepository.findById(id).get();

        AnimationEpisodeEntityDto animationEpisodeEntityDto = new AnimationEpisodeEntityDto();
        animationEpisodeEntityDto.setEpisode(episode);
        animationEpisodeEntityDto.setUploadMainTitleEntity(uploadMainTitleEntity);


        return animationEpisodeEntityDto;
    }

}
