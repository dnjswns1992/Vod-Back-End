package com.example.springsecurity04.Controller;


import com.example.springsecurity04.Dto.UserDto;
import com.example.springsecurity04.Dto.UserInformation;
import com.example.springsecurity04.Service.UserDetailsInformation;
import com.example.springsecurity04.Service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {



    private final UserService service;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> userRegister(@RequestBody UserDto dto, HttpServletRequest servletRequest){
        boolean join = service.join(dto,servletRequest);
        if(join) return ResponseEntity.status(HttpStatus.OK).body("회원가입이 완료 되었습니다.");
        else return ResponseEntity.status(HttpStatus.CONFLICT).body("사용중인 아이디 입니다.");
    }
    @GetMapping("/login")
    public String login(){
        return "/logim";
    }

    @GetMapping("/user/test1")
    @ResponseBody
    public String test01(Authentication authentication){
        log.info("들어오는지 테스트~~~!!");
        UserDetailsInformation principal = (UserDetailsInformation) authentication.getPrincipal();

        log.info("유저 정보 이름 = {}",principal.getInformation().getUsername());
        log.info("유저 권한 = {} ",principal.getInformation().getRole());
        log.info("유저 닉네임 = {}",principal.getInformation().getNickname());
        return "시발 왜 안드럼ㄴ언밍ㅁㄴ마ㅣ엄이낭ㅁ";
    }



}
