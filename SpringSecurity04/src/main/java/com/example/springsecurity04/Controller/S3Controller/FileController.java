package com.example.springsecurity04.Controller.S3Controller;

import com.example.springsecurity04.Dto.Video.UploadMainTitleDto;
import com.example.springsecurity04.Dto.Video.VideoDto;
import com.example.springsecurity04.Service.S3Serivce.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    @PostMapping("/api/file/video/upload")
    public ResponseEntity<String> uploadVideo(
            @RequestPart("video") MultipartFile videoFile,
            @RequestPart("videoDto") VideoDto videoDto,
            @RequestPart("image") MultipartFile imageFile) {
        try {
            String key = s3Service.uploadLargeFile(videoFile, videoDto, imageFile);
            return ResponseEntity.ok("Video upload success: " + key);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Video upload failed: " + e.getMessage());
        }
    }
    @PostMapping("/api/mainTitle/upload")
    public ResponseEntity<String> uploadMainTitle(@RequestPart("Image") MultipartFile multipartFile,
                                                  @RequestPart("mainTitleDto")UploadMainTitleDto dto) {

        ResponseEntity responseEntity = s3Service.MainTitleUploadService(multipartFile, dto);

        return responseEntity;
    }
}
