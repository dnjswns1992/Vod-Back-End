package com.example.springsecurity04.Dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

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
