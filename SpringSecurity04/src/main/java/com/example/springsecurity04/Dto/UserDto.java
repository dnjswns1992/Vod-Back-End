package com.example.springsecurity04.Dto;


import com.example.springsecurity04.Repository.CommonRepository;
import com.example.springsecurity04.Repository.Oauth2UserRepository;
import com.example.springsecurity04.Repository.UserRepository;
import com.example.springsecurity04.Table.UserEntity;
import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@RequiredArgsConstructor
public class UserDto {




    private String username;
    private String password;
    private String role;
    private String email;
    private String nickname;
    private String provider;


}
