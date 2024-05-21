package com.example.springsecurity04.Controller;

import com.example.springsecurity04.JwtUtil.JsonWebToken;
import com.example.springsecurity04.Service.UserAccountService.UserAccountService;
import com.example.springsecurity04.Table.Oauth2Entity;
import com.example.springsecurity04.Table.UserEntity;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5178")
public class UserAccountController {

    private final UserAccountService accountService;

    /* JWT 토큰을 들고 유저의 정보를 가져오는 메서드. */
    @GetMapping("/authenticated/getUserInfo")
    public ResponseEntity UserAccountGet(@RequestHeader("Authorization")String token){
        String JwtToken = token.substring(7);
        Oauth2Entity oauth2Entity = accountService.UserInfo(JwtToken);
        UserEntity userEntity = accountService.FormUserInfo(JwtToken);

        if(oauth2Entity != null) {
            return ResponseEntity.ok(oauth2Entity);
        }
        else return ResponseEntity.ok(userEntity);
    }
}
