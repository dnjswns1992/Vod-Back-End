package com.example.StreamCraft.oauth2.Model;

import com.example.StreamCraft.oauth2.ProviderUser.NaverUser;
import com.example.StreamCraft.oauth2.ProviderUser.ProviderUser;

/**
 * 네이버 OAuth2 로그인 응답을 ProviderUser 객체로 변환하는 Converter
 * - registrationId가 "naver"인 경우에만 작동
 * - ConverterAttribute를 통해 필요한 사용자 정보를 추출
 * - NaverUser 객체를 생성해 반환
 */

public class NaverConverter implements ProviderOauth2Converter<ProviderUserRequest,ProviderUser> {


    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {

        // registrationId가 "naver"가 아니면 null 반환 (이 Converter는 네이버 전용)
        if(!providerUserRequest.registration().getRegistrationId().equals("naver")) return null;

        // OAuth2User로부터 네이버 사용자 정보를 추출하여 매핑
        ConverterAttribute converterAttribute = ConverterAttribute.NaverConverterAttribute(providerUserRequest.user());

        // 추출된 정보를 기반으로 NaverUser 객체 생성 및 반환
        return new NaverUser(providerUserRequest.registration(), providerUserRequest.user(),converterAttribute);
    }
}
