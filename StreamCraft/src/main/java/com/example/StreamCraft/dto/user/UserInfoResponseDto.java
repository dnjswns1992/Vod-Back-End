package com.example.StreamCraft.dto.user;

import com.example.StreamCraft.Oauth2.ProviderUser.ProviderUser;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@Slf4j

public class UserInfoResponseDto {

    @Builder
    public UserInfoResponseDto(String username, String nickname, String password, String role, String provider) {
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


    public UserInfoResponseDto(UserRegisterDto dto){
        this.username = dto.getUsername();
        this.nickname = dto.getNickname();
        this.password = dto.getPassword();
        this.role = dto.getRole();
        this.provider = dto.getProvider();

    }
    public UserInfoResponseDto(ProviderUser providerUser){
        this.username = providerUser.email();
        this.role = String.valueOf(providerUser.getAuthority().get(0));
        this.provider = providerUser.provider();
        this.nickname = providerUser.nickName();
        this.password = "없음";
    }
}