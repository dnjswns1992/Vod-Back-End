package com.example.springsecurity04.Service.UserAccountService;

import com.example.springsecurity04.JwtUtil.JsonWebToken;
import com.example.springsecurity04.Repository.Oauth2UserRepository;
import com.example.springsecurity04.Table.Oauth2Entity;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final Oauth2UserRepository userRepository;
    public Oauth2Entity UserInfo(String Token){
        Claims token = JsonWebToken.getToken(Token);
        String username = token.get("username", String.class);
        Oauth2Entity oauth2Entity = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return oauth2Entity;
    }
}
