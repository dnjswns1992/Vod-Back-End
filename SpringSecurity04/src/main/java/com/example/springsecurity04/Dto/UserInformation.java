package com.example.springsecurity04.Dto;

import com.example.springsecurity04.Oauth2.ProviderUser.ProviderUser;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Slf4j

public class UserInformation {

    @Builder
    public UserInformation(String username, String nickname, String password, String role, String provider) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
        this.provider = provider;
    }

    private String username;
    private String nickname;
    private String password;
    private String role;
    private String provider;


    public UserInformation(UserDto dto){
        this.username = dto.getUsername();
        this.nickname = dto.getNickname();
        this.password = dto.getPassword();
        this.role = dto.getRole();
        this.provider = dto.getProvider();

    }
    public UserInformation(ProviderUser providerUser){
        this.username = providerUser.email();
        this.role = String.valueOf(providerUser.getAuthority().get(0));
        this.provider = providerUser.provider();
        this.nickname = providerUser.nickName();
        this.password = "없음";
    }
}