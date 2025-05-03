package com.example.StreamCraft.dto.user;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserRegisterDto {

    private String username;
    private String password;
    private String role;
    private String email;
    private String nickname;
    private String provider;

}
