package com.example.StreamCraft.controller.user;

import com.example.StreamCraft.service.userservice.UserQueryService;
import com.example.StreamCraft.Entity.user.Oauth2Entity;
import com.example.StreamCraft.Entity.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UserAccountController {

    private final UserQueryService accountService;

    /* JWT 토큰을 들고 유저의 정보를 가져오는 메서드.
    유저가 새로고침 및 화면이동할때마다 계속 요청 됨
    * */

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
