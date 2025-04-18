package com.example.springsecurity04.Controller.S3Controller;

import com.example.springsecurity04.Dto.Video.Animation.AnimationEpisodeEntityDto;
import com.example.springsecurity04.Dto.Video.Movie.MovieDto;
import com.example.springsecurity04.Dto.Video.UploadMainTitleDto;
import com.example.springsecurity04.Dto.Video.VideoDto;
import com.example.springsecurity04.Request.CompleteMultipartUploadRequestCustom;
import com.example.springsecurity04.Service.S3Serivce.S3Service;
import com.example.springsecurity04.Table.EpsidoeEntity.MovieEpisodeEntity;
import com.example.springsecurity04.Table.Video.UploadMainTitleEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final S3Service s3Service;


    @PostMapping("/api/file/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {

        try {
            String key = s3Service.upload(file);

            return ResponseEntity.ok("File upload success" + key);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("file upload failed" + e.getMessage());
        }
    }

    /* 메인 타이틀 등록 */
    @PostMapping("/api/mainTitle/upload")
    public ResponseEntity<String> uploadMainTitle(@RequestPart("Image") MultipartFile multipartFile,
                                                  @RequestPart("mainTitleDto")UploadMainTitleDto dto,
                                                  @RequestPart("mainTitleImage") MultipartFile mainTitleImage) {

        ResponseEntity responseEntity = s3Service.MainTitleUploadService(multipartFile, dto,mainTitleImage);

        return responseEntity;
    }

    //애니메이션을 가져오는 서비스 (메인 화면에서만)
    @GetMapping("/api/animation/bring")
    public ResponseEntity animationBring(){
        List<UploadMainTitleEntity> uploadMainTitleEntities = s3Service.animationVideoService();

        return uploadMainTitleEntities.isEmpty() ? ResponseEntity.status(404).body("게시글을 찾을 수 없습니다.")
                : ResponseEntity.status(200).body(uploadMainTitleEntities);
    }

    //애니메이션을 가져옴 (에피소드 각 화별)
    @GetMapping("/api/animation/episode/{id}")
    public ResponseEntity animationEpisode(@PathVariable int id) {
        AnimationEpisodeEntityDto animationEpisodeEntityDto = s3Service.episodeAnimation(id);
        return ResponseEntity.status(200).body(animationEpisodeEntityDto);
    }
    @GetMapping("/api/movie/episode/{id}")
    public ResponseEntity movieEpisode(@PathVariable int id){
        MovieDto movieDto = s3Service.episodeMovie(id);
        return ResponseEntity.status(200).body(movieDto);
    }

    //영화 가져옴 (메인 화면에서만)
    @GetMapping("/api/movie/bring")
    public ResponseEntity movieBring(){
        List<UploadMainTitleEntity> uploadMainTitleMovie = s3Service.movieVideoService();

        return uploadMainTitleMovie.isEmpty() ? ResponseEntity.status(404).body("게시글을 찾을 수 없습니다.")
                : ResponseEntity.status(200).body(uploadMainTitleMovie);
    }





    //유저 권한 일 때

    @GetMapping("/api/animation/episode/role_user/{id}")
    public ResponseEntity animationUserEpisode(@PathVariable int id) {
        AnimationEpisodeEntityDto animationEpisodeEntityDto = s3Service.episodeUserAnimation(id);

        return ResponseEntity.status(200).body(animationEpisodeEntityDto);
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
    public ResponseEntity<Map<String, String>> createMultipartUpload(@RequestParam String fileName) {
        String uploadId = s3Service.createMultipartUpload(fileName);
        Map<String, String> response = new HashMap<>();
        response.put("uploadId", uploadId);
        return ResponseEntity.ok(response);
    }
    // 서명된 Url을 주는 api 파트 마다 한번씩 요청 됨
    @GetMapping("/api/s3/generate-presigned-url")
    public ResponseEntity<String> generatePresignedUrl(@RequestParam String fileName,
                                                       @RequestParam int partNumber,
                                                       @RequestParam String uploadId) {
        String url = s3Service.generatePresignedUrl(fileName, partNumber, uploadId);
        return ResponseEntity.ok(url);
    }

    //
    @PostMapping("/api/s3/complete-multipart-upload")
    public ResponseEntity<Void> completeMultipartUpload(@RequestBody CompleteMultipartUploadRequestCustom request) {


        s3Service.completeMultipartUpload(request);
        return ResponseEntity.ok().build();
    }



        @PostMapping("/api/file/video/save-metadata")
    public ResponseEntity<String> saveMetadata(@RequestPart("videoDto") VideoDto videoDto,
                                               @RequestPart("Image") MultipartFile imageFile,
                                               @RequestPart(value = "subtitle", required = false) MultipartFile subtitleFile) {
        s3Service.saveVideoMetadata(videoDto,imageFile,subtitleFile);
        return ResponseEntity.ok("등록 되었습니다.");
    }

}
