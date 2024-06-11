package com.example.springsecurity04.Service.S3Serivce;
import com.example.springsecurity04.Config.S3.S3Config;
import com.example.springsecurity04.Dto.Video.Animation.AnimationEpisodeEntityDto;
import com.example.springsecurity04.Dto.Video.UploadMainTitleDto;
import com.example.springsecurity04.Dto.Video.VideoDto;
import com.example.springsecurity04.Handler.WebSocketHandler.ProgressWebSocketHandler;
import com.example.springsecurity04.Repository.EpisodeRepository.AnimationEpisodeEntityRepository;
import com.example.springsecurity04.Repository.EpisodeRepository.DramaEpisodeRepository;
import com.example.springsecurity04.Request.CompleteMultipartUploadRequestCustom;
import com.example.springsecurity04.Table.EpsidoeEntity.AnimationEpisodeEntity;
import com.example.springsecurity04.Repository.VideoRepository.UploadMainTitleRepository;
import com.example.springsecurity04.Table.EpsidoeEntity.DramaEpisodeEntity;
import com.example.springsecurity04.Table.Video.UploadMainTitleEntity;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.UploadPartPresignRequest;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Slf4j
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName;
    private final UploadMainTitleRepository uploadMainTitleRepository;
    private final AnimationEpisodeEntityRepository animationEpisodeEntityRepository;
    private final DramaEpisodeRepository dramaEpisodeRepository;
    private final ProgressWebSocketHandler webSocketHandler;
    private final S3Presigner s3Presigner;




    @Autowired
    public S3Service(S3Config s3Config, UploadMainTitleRepository uploadMainTitleRepository,
                     AnimationEpisodeEntityRepository animationEpisodeEntityRepository,
                     DramaEpisodeRepository dramaEpisodeRepository,ProgressWebSocketHandler webSocketHandler,S3Presigner s3Presigner) {
        this.s3Client = s3Config.s3Client();
        this.bucketName = s3Config.getBucketName();
        this.uploadMainTitleRepository = uploadMainTitleRepository;
        this.animationEpisodeEntityRepository = animationEpisodeEntityRepository;
        this.dramaEpisodeRepository = dramaEpisodeRepository;
        this.webSocketHandler = webSocketHandler;
        this.s3Presigner = s3Presigner;

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
        List<AnimationEpisodeEntity> episode =
                animationEpisodeEntityRepository.findByUploadMainTitleEntityUploadMainTitleEntityIdAndVideoType(id,"main");
        UploadMainTitleEntity uploadMainTitleEntity = uploadMainTitleRepository.findById(id).get();

        AnimationEpisodeEntityDto animationEpisodeEntityDto = new AnimationEpisodeEntityDto();
        animationEpisodeEntityDto.setEpisode(episode);
        animationEpisodeEntityDto.setUploadMainTitleEntity(uploadMainTitleEntity);
        return animationEpisodeEntityDto;
    }

    public AnimationEpisodeEntityDto episodeUserAnimation(int episodeId){

        List<AnimationEpisodeEntity> episodeEntities =
                animationEpisodeEntityRepository.findByUploadMainTitleEntityUploadMainTitleEntityIdAndVideoType(episodeId,"prologue");

        AnimationEpisodeEntityDto animationEpisodeEntityDto = new AnimationEpisodeEntityDto();
        UploadMainTitleEntity uploadMainTitleEntity = uploadMainTitleRepository.findById(episodeId).get();
        animationEpisodeEntityDto.setEpisode(episodeEntities);
        animationEpisodeEntityDto.setUploadMainTitleEntity(uploadMainTitleEntity);

        return animationEpisodeEntityDto;
    }

    /* [ETAG] S3가 파일을 합치기 위한 식별 코드 */
    /* 각 파트가 업로드될 때마다 S3는 각 파트에 대해 ETag 값을 반환합니다.
       이 값은 나중에 전체 객체를 조립할 때 사용됩니다.
    *
    *  [PartNumber]
    * 멀티파트 업로드의 경우: 각 파트를 업로드할 때 PartNumber를 지정해야 합니다.
    * 이는 나중에 모든 파트를 조합할 때 각 파트의 순서를 올바르게 지정하는 데 사용됩니다.
    *
    *  */

    private void saveEpisodeData(VideoDto videoDto, String imageFileUri,MultipartFile subtitle) {
        UploadMainTitleEntity uploadMainTitleEntity = uploadMainTitleRepository.findByTitleContainingAndGenre(videoDto.getTitle(), videoDto.getGenre()).get();

        try {


            String subtitleUrl = null;

            if (uploadMainTitleEntity.getGenre().equals("애니")) {

                if (subtitle != null) {
                    subtitleUrl = upload(subtitle);
                }
                AnimationEpisodeEntity animationEpisode = AnimationEpisodeEntity.builder()
                        .episodeNumber(videoDto.getEpisodeNumber())
                        .imageUrl(imageFileUri)
                        .description(videoDto.getDescription())
                        .genre(videoDto.getGenre())
                        .videoUrl(videoDto.getVideoUrl())
                        .subtitleUrl(null)
                        .entity(uploadMainTitleEntity)
                        .videoType(videoDto.getVideoType())
                        .build();

                animationEpisodeEntityRepository.save(animationEpisode);
            } else if (uploadMainTitleEntity.getGenre().equals("영화")) {
                DramaEpisodeEntity dramaEpisodeEntity = DramaEpisodeEntity.builder()
                        .episodeNumber(videoDto.getEpisodeNumber())
                        .description(videoDto.getDescription())
                        .genre(videoDto.getGenre())
                        .ImageUrl(imageFileUri)
                        .videoUrl(videoDto.getVideoUrl())
                        .build();

                dramaEpisodeRepository.save(dramaEpisodeEntity);
            }
        } catch (Exception e) {
        }
    }


    public void  saveVideoMetadata(VideoDto videoDto, MultipartFile imageFile, MultipartFile subtitleFile){
        try {

            String imageUrl = upload(imageFile);

            saveEpisodeData(videoDto,imageUrl,subtitleFile);

        }catch (Exception e){

        }
    }




    /* 동영상  업로드 메서드 - 사용중지  */

    public String uploadLargeFile(MultipartFile videoFile, VideoDto videoDto, MultipartFile imageFile, String sessionId, MultipartFile subtitle) throws IOException {

        try {


            String videoKey = String.valueOf(Paths.get(System.currentTimeMillis() + "-" + videoFile.getOriginalFilename()));
            String subtitleUrl = null;

            // Video upload
            CreateMultipartUploadRequest createMultipartUploadRequest = CreateMultipartUploadRequest.builder()
                    .bucket(bucketName)
                    .key(videoKey)
                    .build();
            CreateMultipartUploadResponse response = s3Client.createMultipartUpload(createMultipartUploadRequest);
            String uploadId = response.uploadId();

            // Upload parts in parallel
            List<CompletedPart> completedParts = Collections.synchronizedList(new ArrayList<>());
            long partSize = 10 * 1024 * 1024; // Increase part size to 10MB
            byte[] fileBytes = videoFile.getBytes();
            int totalParts = (int) Math.ceil((double) fileBytes.length / partSize);

            ExecutorService executor = Executors.newFixedThreadPool(5); // Adjust thread pool size to 5
            List<CompletableFuture<Void>> futures = new ArrayList<>();

            for (int partNumber = 1; partNumber <= totalParts; partNumber++) {
                int finalPartNumber = partNumber;
                futures.add(CompletableFuture.runAsync(() -> {
                    int start = (finalPartNumber - 1) * (int) partSize;
                    int end = Math.min(fileBytes.length, start + (int) partSize);
                    byte[] partBytes = new byte[end - start];
                    System.arraycopy(fileBytes, start, partBytes, 0, partBytes.length);

                    UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
                            .bucket(bucketName)
                            .key(videoKey)
                            .uploadId(uploadId)
                            .partNumber(finalPartNumber)
                            .build();
                    UploadPartResponse uploadPartResponse = s3Client.uploadPart(uploadPartRequest, RequestBody.fromBytes(partBytes));

                    completedParts.add(CompletedPart.builder()
                            .partNumber(finalPartNumber)
                            .eTag(uploadPartResponse.eTag())
                            .build());
                    /* 웹소켓 동영상 실시간으로 보여줌 */

                    int progress = (int) (((double) (start + partBytes.length) / fileBytes.length) * 100);
                    webSocketHandler.sendMessage(sessionId, progress);
                }, executor));
            }

            // Wait for all parts to be uploaded
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            executor.shutdown();

            // Sort completed parts by part number
            completedParts.sort(Comparator.comparingInt(CompletedPart::partNumber));

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
            String imageFileUri = upload(imageFile);



            saveEpisodeData(videoDto,imageFileUri,subtitle);

            return videoFileUri.toString();
        }catch (OutOfMemoryError e) {
        }
        return bucketName;
    }
    /*
    * 1 . 클라이언트가 백엔드로부터 서명된 URL을 받아옴
    *
    *     클라이언트는 각 파트를 업로드하기 위해 백엔드에게 사전된 URL을 요청한다.
    *     백엔드는 S3에 요청하여 각 파트에 대한 사전 서명된 URL을 생성하고 이를 클라이언트에 반환한다.
    *
    * 2. 클라이언트는 서명된 URL을 가지고 각 파트를 쪼개서 서명된 URL과 함께 S3에 보냄
    *
    *    클라이언트는 받은 사전 URL을 사용하여 각 파트를 S3에 업로드한다.
    *    각 파트를 업로드 할 때 , 사전 서명된 URL을 사용하여 S3에 직접 요청을 보낸다.
    *
    * 3. 이 파트 작업이 S3에 다 올라가면 백엔드로 완료 요청을 보냄:
    *
    *    모든 파트가 성공적으로 업로드되면, 클라이언트는 각 파트의 ETag와 파트 번호를 포함하여 백엔드에 업로드 완료 요청을 보냅니다.
    *
    * 4. 백엔드는 완료 요청을 받아 S3에게 완료되었다고 알림:
    *
    *    백엔드는 클라이언트로부터 받은 정보(각 파트의 ETag와 파트 번호)를 사용하여 S3에 업로드 완료 요청을 보냅니다.
    *
    * 5. S3는 백엔드에 완료 요청을 받아서, 쪼개진 작업을 하나로 병합함:
    *
    *    S3는 업로드 완료 요청을 받으면, 업로드된 모든 파트를 병합하여 하나의 객체로 만듭니다.
    *
     *  */

    // ObjectKey : 파일의 이름
    public String createMultipartUpload(String objectKey) {
        CreateMultipartUploadRequest createMultipartUploadRequest = CreateMultipartUploadRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        CreateMultipartUploadResponse response = s3Client.createMultipartUpload(createMultipartUploadRequest);
        return response.uploadId();
    }
    /* 이 부분의 코드는 실제로 S3에 파일을 업로드하는 것은 아니고,
    앞서 클라이언트가 부분적으로 업로드한 각 파트의 정보를 S3에 전달하여 이 파트들을 결합해 최종 파일로 완성하라는 요청을 보내는 역할을 합니다.
     즉, S3에게 "이 파트들을 결합하여 하나의 파일로 만들어라"라는 명령을 내리는 것입니다. */

    public void completeMultipartUpload(CompleteMultipartUploadRequestCustom customRequest) {
        List<CompletedPart> completedParts = customRequest.getCompletedParts().stream()
                .map(dto -> {
                    String eTag = dto.getETag();
                    log.info("Original ETag: {}", eTag); // ETag 값 로그 출력
                    eTag = eTag.replace("\"", ""); // 따옴표 제거
                    log.info("Sanitized ETag: {}", eTag); // 제거 후 ETag 값 로그 출력
                    if (dto.getPartNumber() < 1) {
                        throw new IllegalArgumentException("PartNumber must be >= 1");
                    }
                    return CompletedPart.builder()
                            .eTag(eTag)
                            .partNumber(dto.getPartNumber())
                            .build();
                })
                .collect(Collectors.toList());

        CompleteMultipartUploadRequest completeMultipartUploadRequest = CompleteMultipartUploadRequest.builder()
                .bucket(bucketName)
                .key(customRequest.getFileName())
                .uploadId(customRequest.getUploadId())
                .multipartUpload(CompletedMultipartUpload.builder().parts(completedParts).build())
                .build();

        // 디버깅용 로그 추가
        log.info("CompleteMultipartUploadRequest: {}", completeMultipartUploadRequest);

        try {
            s3Client.completeMultipartUpload(completeMultipartUploadRequest);
        } catch (S3Exception e) {
            log.error("S3Exception: {}", e.awsErrorDetails().errorMessage());
            log.error("Request ID: {}", e.requestId());
            log.error("HTTP Status Code: {}", e.statusCode());
            log.error("AWS Error Code: {}", e.awsErrorDetails().errorCode());
            log.error("Error Type: {}", e.awsErrorDetails().errorMessage());
            log.error("Service Name: {}", e.awsErrorDetails().serviceName());
            throw e;
        }
    }
    public String generatePresignedUrl(String objectKey, int partNumber, String uploadId) {
        UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .uploadId(uploadId)
                .partNumber(partNumber)
                .build();

        UploadPartPresignRequest presignRequest = UploadPartPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(600))
                .uploadPartRequest(uploadPartRequest)
                .build();

        URL url = s3Presigner.presignUploadPart(presignRequest).url();
        return url.toString();
    }

}
