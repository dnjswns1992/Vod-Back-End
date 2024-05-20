package com.example.springsecurity04.Service.UserAccountService;

import com.example.springsecurity04.JwtUtil.JsonWebToken;
import com.example.springsecurity04.Repository.Oauth2UserRepository;
import com.example.springsecurity04.Repository.UserRepository;
import com.example.springsecurity04.Table.Oauth2Entity;
import com.example.springsecurity04.Table.UserEntity;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final Oauth2UserRepository userRepository;
    private final UserRepository formUserRepository;
    public Oauth2Entity UserInfo(String Token){
        Claims token = JsonWebToken.getToken(Token);
        String username = token.get("username",String.class);
        Optional<Oauth2Entity> byEmail = userRepository.findByEmail(username);

        if(byEmail.isPresent()) return byEmail.get();
        else return null;

    }
    public UserEntity FormUserInfo(String token) {
        Claims token1 = JsonWebToken.getToken(token);
        String username = token1.get("username",String.class);
        Optional<UserEntity> userEntityByUsername = formUserRepository.findUserEntityByUsername(username);

        if(userEntityByUsername.isPresent()) return userEntityByUsername.get();
        else return null;
    }
}
