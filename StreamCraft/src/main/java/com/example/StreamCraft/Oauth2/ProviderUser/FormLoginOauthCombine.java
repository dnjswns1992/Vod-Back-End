package com.example.StreamCraft.Oauth2.ProviderUser;

import com.example.StreamCraft.dto.user.UserInfoResponseDto;
import com.example.StreamCraft.Table.Common.CommonEntity;
import com.example.StreamCraft.Table.UserAccount.Oauth2Entity;
import com.example.StreamCraft.Table.UserAccount.UserEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FormLoginOauthCombine {



    public static UserInfoResponseDto Oauth2Combine(Oauth2Entity entity){

     return   UserInfoResponseDto.builder()
               .role(entity.getRole())
               .username(entity.getEmail())
               .provider(entity.getProvider())
               .password("없음")
               .nickname(entity.getNickName()).build();
    }
    public static UserInfoResponseDto FormLoginCombine(UserEntity entity){
       return UserInfoResponseDto.builder()
                .role(entity.getRole())
                .username(entity.getUsername())
                .provider(entity.getProvider())
                .nickname(entity.getNickName())
                .password(entity.getPassword()).build();
    }
    public static UserInfoResponseDto CommonCombine(CommonEntity commonEntity) {

     return    UserInfoResponseDto.builder()
                .role(commonEntity.getRole())
                .provider(commonEntity.getProvider())
                .username(commonEntity.getUsername())
                .nickname(commonEntity.getNickname())
                .password("없음").build();

    }
}
