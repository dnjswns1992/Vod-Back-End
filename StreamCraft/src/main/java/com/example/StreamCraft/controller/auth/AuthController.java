package com.example.StreamCraft.controller.auth;


import com.example.StreamCraft.dto.user.UserRegisterDto;
import com.example.StreamCraft.Service.UserRegisterService.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@Slf4j

    /**
    * 회원가입 관련 요청을 처리하는 컨트롤러
    */
public class AuthController {

    private final UserService service;
    /**
     * 회원가입 처리
     *
     * @param dto 사용자 정보 DTO
     * @param multipartFile 프로필 이미지 파일
     * @param servletRequest 요청 객체 (IP 등 추출용)
     * @return 회원가입 결과 메시지
     */

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> userRegister(@RequestPart("dto") UserRegisterDto dto
                                          ,@RequestPart("image")MultipartFile multipartFile,
                                          HttpServletRequest servletRequest){
        boolean join = service.join(dto, servletRequest, multipartFile);
        if(join) return ResponseEntity.status(200).body("회원가입 완료");
        else return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 아이디 입니다.");

    }

}
