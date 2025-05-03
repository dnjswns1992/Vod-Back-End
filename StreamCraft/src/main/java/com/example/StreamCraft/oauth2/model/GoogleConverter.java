package com.example.StreamCraft.oauth2.model;

import com.example.StreamCraft.oauth2.provideruser.GoogleUser;
import com.example.StreamCraft.oauth2.provideruser.ProviderUser;

/**
 * Google OAuth2 인증 정보를 ProviderUser 객체로 변환하는 Converter 클래스
 * - Google 로그인 요청인 경우에만 작동
 * - Google 로그인 응답을 GoogleUser 객체로 감싸서 반환
 */

public class GoogleConverter implements ProviderOauth2Converter<ProviderUserRequest,ProviderUser> {

    /**
     * Google 로그인 요청을 처리하여 ProviderUser 객체로 변환
     *
     * @param providerUserRequest 소셜 로그인 요청 정보 (OAuth2User 및 ClientRegistration 포함)
     * @return GoogleUser 객체 (Google 로그인일 경우), 그 외에는 null
     */

    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {

        // Google 이외의 로그인 요청은 처리하지 않음
        if(!providerUserRequest.registration().getRegistrationId().equals("google")) return null;

        // Google 사용자 정보를 attribute로 변환
        ConverterAttribute converterAttribute = ConverterAttribute.googleAttributeConverter(providerUserRequest.user());

        // GoogleUser 객체 생성 및 반환
        return new GoogleUser(providerUserRequest.registration(), providerUserRequest.user(),converterAttribute);
    }
}
