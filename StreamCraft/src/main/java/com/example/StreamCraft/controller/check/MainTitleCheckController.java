package com.example.StreamCraft.controller.check;

import com.example.StreamCraft.Repository.video.upload.VideoUploadRepository;
import com.example.StreamCraft.Entity.Video.UploadMainTitleEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 메인 타이틀 중복 여부를 검사하는 컨트롤러
 */

@RestController
@RequiredArgsConstructor
public class MainTitleCheckController {

    /**
     * 주어진 title이 이미 DB에 존재하는지 확인하는 API
     * @param title 사용자가 입력한 영상 제목
     * @return 409 - 중복됨, 200 - 등록 가능
     */
    private final VideoUploadRepository videoUploadRepository;


    @GetMapping("/api/MainTitle/check")
    @Tag(name = "영상처리 컨트롤러 , description = 관리자가 영상을 올릴 때 중복된 영상을 올리면 에러를 터뜨림")

    public ResponseEntity<String> MainTitleCheck(String title) {

        Optional<UploadMainTitleEntity> byTitleContaining = videoUploadRepository.findByTitleContaining(title);

        if(byTitleContaining.isPresent()) return ResponseEntity.status(409).body("해당 이름이 이미 존재 합니다.");
        else return ResponseEntity.status(200).body("등록가능한 메인 타이틀 입니다.");
    }
}
