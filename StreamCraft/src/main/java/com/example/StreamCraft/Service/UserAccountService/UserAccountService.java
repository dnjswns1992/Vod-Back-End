package com.example.StreamCraft.Service.UserAccountService;

import com.example.StreamCraft.jwtutil.JwtTokenProvider;
import com.example.StreamCraft.Repository.Oauth2UserRepository;
import com.example.StreamCraft.Repository.UserRepository;
import com.example.StreamCraft.Table.UserAccount.Oauth2Entity;
import com.example.StreamCraft.Table.UserAccount.UserEntity;
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
        Claims token = JwtTokenProvider.getToken(Token);
        String username = token.get("username",String.class);
        Optional<Oauth2Entity> byEmail = userRepository.findByEmail(username);

        if(byEmail.isPresent()) return byEmail.get();
        else return null;

    }
    public UserEntity FormUserInfo(String token) {
        Claims token1 = JwtTokenProvider.getToken(token);
        String username = token1.get("username",String.class);
        Optional<UserEntity> userEntityByUsername = formUserRepository.findUserEntityByUsername(username);

        if(userEntityByUsername.isPresent()) return userEntityByUsername.get();
        else return null;
    }
}
