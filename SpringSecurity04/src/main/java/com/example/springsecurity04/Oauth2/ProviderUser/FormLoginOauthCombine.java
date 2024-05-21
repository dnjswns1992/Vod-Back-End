package com.example.springsecurity04.Oauth2.ProviderUser;

import com.example.springsecurity04.Dto.UserInformation;
import com.example.springsecurity04.Table.Common.CommonEntity;
import com.example.springsecurity04.Table.Oauth2Entity;
import com.example.springsecurity04.Table.UserEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FormLoginOauthCombine {



    public static UserInformation Oauth2Combine(Oauth2Entity entity){

     return   UserInformation.builder()
               .role(entity.getRole())
               .username(entity.getEmail())
               .provider(entity.getProvider())
               .password("없음")
               .nickname(entity.getNickName()).build();
    }
    public static UserInformation FormLoginCombine(UserEntity entity){
       return UserInformation.builder()
                .role(entity.getRole())
                .username(entity.getUsername())
                .provider(entity.getProvider())
                .nickname(entity.getNickName())
                .password(entity.getPassword()).build();
    }
    public static UserInformation CommonCombine(CommonEntity commonEntity) {

     return    UserInformation.builder()
                .role(commonEntity.getRole())
                .provider(commonEntity.getProvider())
                .username(commonEntity.getUsername())
                .nickname(commonEntity.getNickname())
                .password("없음").build();

    }
}
