package com.example.StreamCraft.service.userservice;

import com.example.StreamCraft.jwtutil.JwtTokenProvider;
import com.example.StreamCraft.Repository.user.Oauth2UserRepository;
import com.example.StreamCraft.Repository.user.UserRepository;
import com.example.StreamCraft.Entity.user.Oauth2Entity;
import com.example.StreamCraft.Entity.user.UserEntity;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
/**
 * ✅ UserQueryService
 * - JWT 토큰을 기반으로 현재 로그인한 사용자의 정보를 조회하는 서비스
 * - Oauth2 로그인 사용자 또는 일반 폼 로그인 사용자 모두 지원
 */
@Service
@RequiredArgsConstructor
public class UserQueryService {
    /**
     * ✅ 소셜 로그인 사용자(Oauth2Entity) 조회
     * @param token JWT 토큰
     * @return Oauth2Entity (해당 토큰에 해당하는 사용자 정보) or null
     */

    private final Oauth2UserRepository userRepository;
    private final UserRepository formUserRepository;


    public Oauth2Entity UserInfo(String Token){
        Claims token = JwtTokenProvider.getToken(Token);
        String username = token.get("username",String.class);
        Optional<Oauth2Entity> byEmail = userRepository.findByEmail(username);

        if(byEmail.isPresent()) return byEmail.get();
        else return null;

    }
    /**
     * ✅ 일반 폼 로그인 사용자(UserEntity) 조회
     * @param token JWT 토큰
     * @return UserEntity (해당 토큰에 해당하는 사용자 정보) or null
     */
    public UserEntity FormUserInfo(String token) {
        Claims token1 = JwtTokenProvider.getToken(token);
        String username = token1.get("username",String.class);
        Optional<UserEntity> userEntityByUsername = formUserRepository.findUserEntityByUsername(username);

        if(userEntityByUsername.isPresent()) return userEntityByUsername.get();
        else return null;
    }
}
