package com.example.springsecurity04.Controller.S3Controller;

import com.example.springsecurity04.Dto.Video.Animation.AnimationEpisodeEntityDto;
import com.example.springsecurity04.Dto.Video.UploadMainTitleDto;
import com.example.springsecurity04.Dto.Video.VideoDto;
import com.example.springsecurity04.Service.S3Serivce.S3Service;
import com.example.springsecurity04.Table.EpsidoeEntity.AnimationEpisodeEntity;
import com.example.springsecurity04.Table.Video.UploadMainTitleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
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
    /* 에피소드 (동영상) 등록 */
    @PostMapping("/api/file/video/upload")
    public ResponseEntity<String> uploadVideo(
            @RequestPart("video") MultipartFile videoFile,
            @RequestPart("videoDto") VideoDto videoDto,
            @RequestPart("Image") MultipartFile imageFile,
            @RequestHeader("Session-Id")String sessionId) {
        try {
            String key = s3Service.uploadLargeFile(videoFile, videoDto,imageFile,sessionId);
            return ResponseEntity.ok("Video upload success: " + key);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Video upload failed: " + e.getMessage());
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

    @GetMapping("/api/animation/bring")
    public ResponseEntity animationBring(){
        List<UploadMainTitleEntity> uploadMainTitleEntities = s3Service.animationVideoService();

        return uploadMainTitleEntities.isEmpty() ? ResponseEntity.status(404).body("게시글을 찾을 수 없습니다.")
                : ResponseEntity.status(200).body(uploadMainTitleEntities);
    }
    @GetMapping("/api/animation/episode/{id}")
    public ResponseEntity animationEpisode(@PathVariable int id) {
        AnimationEpisodeEntityDto animationEpisodeEntityDto = s3Service.episodeAnimation(id);
        return ResponseEntity.status(200).body(animationEpisodeEntityDto);
    }

}
