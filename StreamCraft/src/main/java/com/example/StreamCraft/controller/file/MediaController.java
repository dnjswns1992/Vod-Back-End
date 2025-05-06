package com.example.StreamCraft.controller.file;

import com.example.StreamCraft.dto.media.animation.AnimationEpisodeResponseDto;
import com.example.StreamCraft.dto.media.movie.MovieEpisodeResponseDto;
import com.example.StreamCraft.dto.media.upload.UploadMainTitleRequestDto;
import com.example.StreamCraft.dto.media.upload.VideoMetadataRequestDto;
import com.example.StreamCraft.Request.CompleteMultipartUploadRequestCustom;
import com.example.StreamCraft.service.s3.S3Service;
import com.example.StreamCraft.Entity.Video.UploadMainTitleEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MediaController {

    private final S3Service s3Service;


    // 🔹 단일 파일 업로드
    @PostMapping("/api/file/upload")
    @Tag(name = "홈 타이틀 업로드 , description : 관리자가 사용자의 화면에 보여질 영화 제목 혹은 애니메이션을 업로드 ")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {

        try {
            String key = s3Service.upload(file);

            return ResponseEntity.ok("File upload success" + key);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("file upload failed" + e.getMessage());
        }
    }

    // 🔹 메인 타이틀 업로드
    @PostMapping("/api/mainTitle/upload")
    @Tag(name = "메인 타이틀 업로드, description = 사용자가 해당 영화 및 애니메이션을 클릭시 영화 정보 및 회차가 뿌려지게 끔 함")
    public ResponseEntity<String> uploadMainTitle(@RequestPart("Image") MultipartFile multipartFile,
                                                  @RequestPart("mainTitleDto") UploadMainTitleRequestDto dto,
                                                  @RequestPart("mainTitleImage") MultipartFile mainTitleImage) {

        ResponseEntity responseEntity = s3Service.MainTitleUploadService(multipartFile, dto,mainTitleImage);

        return responseEntity;
    }

    // 🔹 애니메이션 목록 (메인 화면용)
    @GetMapping("/api/animation/bring")
    @Tag(name = "애니메이션 가져옴, description : 사용자가 홈 화면에 있을때 애니메이션 목록들을 화면에 뿌림")
    public ResponseEntity animationBring(){
        List<UploadMainTitleEntity> uploadMainTitleEntities = s3Service.animationVideoService();

        return uploadMainTitleEntities.isEmpty() ? ResponseEntity.status(404).body("게시글을 찾을 수 없습니다.")
                : ResponseEntity.status(200).body(uploadMainTitleEntities);
    }

    // 🔹 애니메이션 에피소드 (단일 ID)
    @GetMapping("/api/animation/episode/{id}")
    public ResponseEntity animationEpisode(@PathVariable int id) {

        AnimationEpisodeResponseDto animationEpisodeResponseDto = s3Service.episodeAnimation(id);
        return ResponseEntity.status(200).body(animationEpisodeResponseDto);
    }

    // 🔹 영화 에피소드 (단일 ID)
    @GetMapping("/api/movie/episode/{id}")
    @Tag(name = "영화 상세정보, description : 사용자가 애니메이션을 클릭했을 때 클릭한 영화의 해당되는 회차를 가져옴")
    public ResponseEntity movieEpisode(@PathVariable int id){
        MovieEpisodeResponseDto movieEpisodeResponseDto = s3Service.episodeMovie(id);
        return ResponseEntity.status(200).body(movieEpisodeResponseDto);
    }

    // 🔹 영화 목록 (메인 화면용)
    @GetMapping("/api/movie/bring")
    @Tag(name = "영화 가져옴, description : 사용자가 홈 화면에 있을때 영화 목록들을 화면에 뿌림")
    public ResponseEntity movieBring(){
        List<UploadMainTitleEntity> uploadMainTitleMovie = s3Service.movieVideoService();

        return uploadMainTitleMovie.isEmpty() ? ResponseEntity.status(404).body("게시글을 찾을 수 없습니다.")
                : ResponseEntity.status(200).body(uploadMainTitleMovie);
    }





    //유저 권한 일 때

    @GetMapping("/api/animation/episode/role_user/{id}")
    @Tag(name = "애니메이션 상세정보, description : 사용자가 애니메이션을 클릭했을 때 클릭한 애니메이션의 해당되는 회차를 가져옴")
    public ResponseEntity animationUserEpisode(@PathVariable int id) {
        log.info("🎬 요청된 애니메이션 ID: {}", id);
        AnimationEpisodeResponseDto animationEpisodeResponseDto = s3Service.episodeAnimation(id);
        log.info("📺 반환될 에피소드 수: {}", animationEpisodeResponseDto.getEpisode().size());
        return ResponseEntity.status(200).body(animationEpisodeResponseDto);
    }


    /* fileName: 업로드하려는 파일의 이름입니다. 이는 S3 버킷 내에서 해당 파일이 저장될 객체의 키(Key)를 나타냅니다. 예를 들어, example-video.mp4와 같은 값이 될 수 있습니다.

    partNumber: 멀티파트 업로드에서 현재 업로드 중인 파트의 번호입니다. S3에서는 각 파트를 번호로 식별하며, 번호는 1부터 시작합니다. 예를 들어, 첫 번째 파트는 partNumber가 1이고, 두 번째 파트는 2입니다.

    uploadId: 멀티파트 업로드를 시작할 때 S3에서 생성된 업로드 ID입니다. 이 ID는 전체 업로드 세션을 식별하며, 모든 파트 업로드 요청과 완료 요청에 사용됩니다.

    파라미터가 없는 경우
    각 파라미터가 없는 경우 발생할 수 있는 문제는 다음과 같습니다:

    fileName이 없는 경우:

    파일 이름은 S3에서 파일을 저장할 위치를 결정하는 데 필수적입니다.
    파일 이름이 없으면 S3에서 파일을 업로드할 위치를 지정할 수 없으므로, 사전 서명된 URL을 생성할 수 없습니다.
    이로 인해 요청은 실패하며, 클라이언트는 적절한 URL을 받지 못합니다.
    partNumber가 없는 경우:

    멀티파트 업로드에서 각 파트는 고유의 번호로 식별됩니다.
    partNumber가 없으면 S3는 업로드할 파트를 식별할 수 없습니다.
    이로 인해 사전 서명된 URL을 생성할 수 없고, 업로드할 파트를 지정할 수 없으므로 요청은 실패하게 됩니다.
    uploadId가 없는 경우:

    uploadId는 멀티파트 업로드 세션을 식별하는 데 사용됩니다.
    uploadId가 없으면 S3는 어떤 멀티파트 업로드 세션에 이 파트가 속하는지 알 수 없습니다.
    이로 인해 사전 서명된 URL을 생성할 수 없고, 파트를 업로드할 수 없으므로 요청은 실패하게 됩니다. */


    @GetMapping("/api/s3/create-multipart-upload")
    @Operation(
            summary = "S3 멀티파트 업로드 시작",
            description = "파일 이름을 전달하면, AWS S3와 통신하여 멀티파트 업로드를 위한 uploadId를 발급받습니다."
    )
    public ResponseEntity<Map<String, String>> createMultipartUpload(@RequestParam String fileName) {
        String uploadId = s3Service.createMultipartUpload(fileName);
        Map<String, String> response = new HashMap<>();
        response.put("uploadId", uploadId);
        return ResponseEntity.ok(response);
    }
    // 서명된 Url을 주는 api 파트 마다 한번씩 요청 됨
    @GetMapping("/api/s3/generate-presigned-url")
    @Operation(
            summary = "S3 업로드용 Presigned URL 생성",
            description = "파일 조각(partNumber)에 대해 AWS S3에 직접 업로드할 수 있는 URL을 발급합니다."
    )
    public ResponseEntity<String> generatePresignedUrl(@RequestParam String fileName,
                                                       @RequestParam int partNumber,
                                                       @RequestParam String uploadId) {
        String url = s3Service.generatePresignedUrl(fileName, partNumber, uploadId);
        return ResponseEntity.ok(url);
    }

    //
    @PostMapping("/api/s3/complete-multipart-upload")
    @Operation(
            summary = "S3 멀티파트 업로드 완료",
            description = "업로드된 파일 파트들을 조합해 S3에서 하나의 완성된 파일로 병합합니다."
    )
    public ResponseEntity<Void> completeMultipartUpload(@RequestBody CompleteMultipartUploadRequestCustom request) {


        s3Service.completeMultipartUpload(request);
        return ResponseEntity.ok().build();
    }



        @PostMapping("/api/file/video/save-metadata")
        @Operation(
                summary = "영상 메타데이터 등록",
                description = "영상 제목, 설명, 회차 번호, 썸네일 이미지, 자막 파일 등을 DB에 저장합니다."
        )
    public ResponseEntity<String> saveMetadata(@RequestPart("videoDto") VideoMetadataRequestDto videoMetadataRequestDto,
                                               @RequestPart("Image") MultipartFile imageFile,
                                               @RequestPart(value = "subtitle", required = false) MultipartFile subtitleFile) {
        s3Service.saveVideoMetadata(videoMetadataRequestDto,imageFile,subtitleFile);
        return ResponseEntity.ok("등록 되었습니다.");
    }

}
