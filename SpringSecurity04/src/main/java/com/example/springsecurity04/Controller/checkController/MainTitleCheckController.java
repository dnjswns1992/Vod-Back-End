package com.example.springsecurity04.Controller.checkController;

import com.example.springsecurity04.Repository.VideoRepository.UploadMainTitleRepository;
import com.example.springsecurity04.Table.Video.UploadMainTitleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MainTitleCheckController {


    private final UploadMainTitleRepository uploadMainTitleRepository;

    @GetMapping("/api/MainTitle/check")
    public ResponseEntity<String> MainTitleCheck(String title) {

        Optional<UploadMainTitleEntity> byTitleContaining = uploadMainTitleRepository.findByTitleContaining(title);

        if(byTitleContaining.isPresent()) return ResponseEntity.status(409).body("해당 이름이 이미 존재 합니다.");
        else return ResponseEntity.status(200).body("등록가능한 메인 타이틀 입니다.");
    }
}
