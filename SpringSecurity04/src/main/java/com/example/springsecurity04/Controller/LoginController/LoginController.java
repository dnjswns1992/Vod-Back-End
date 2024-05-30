package com.example.springsecurity04.Controller.LoginController;


import com.example.springsecurity04.Dto.UserDto;
import com.example.springsecurity04.Service.jwtCheckService.UserDetailsInformation;
import com.example.springsecurity04.Service.UserRegisterService.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@Slf4j

public class LoginController {



    private final UserService service;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> userRegister(@RequestPart("dto") UserDto dto
                                          ,@RequestPart("image")MultipartFile multipartFile,
                                          HttpServletRequest servletRequest){
        boolean join = service.join(dto, servletRequest, multipartFile);
        if(join) return ResponseEntity.status(200).body("회원가입 완료");
        else return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 아이디 입니다.");

    }

}
