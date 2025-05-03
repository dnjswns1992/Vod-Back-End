package com.example.StreamCraft.oauth2.Model;

import com.example.StreamCraft.oauth2.ProviderUser.ProviderUser;

import java.util.Arrays;
import java.util.List;

/**
 * OAuth2 로그인 시, 어떤 소셜 로그인(Google, Naver 등)인지 판별하여
 * 해당 Provider에 맞는 Converter를 위임하는 추상 클래스
 *
 * 이 클래스는 다양한 OAuth2 공급자별로 Converter(GoogleConverter, NaverConverter 등)를 보유하고 있으며,
 * 요청에 따라 적절한 Converter를 선택하여 ProviderUser 객체로 변환합니다.
 */

public abstract class AbstractDelegatingOauth2Converter implements ProviderOauth2Converter<ProviderUserRequest,ProviderUser>{

    // 각 소셜 플랫폼별 Converter 리스트

    // 각 소셜 플랫폼별 Converter 리스트
    private List<? extends ProviderOauth2Converter<ProviderUserRequest, ProviderUser>> converters
            = Arrays.asList(
            new GoogleConverter(),  // Google 로그인 변환기
            new NaverConverter()    // Naver 로그인 변환기
    );

    /**
     * 주어진 ProviderUserRequest를 적절한 Converter에게 위임하여 ProviderUser 객체로 변환
     *
     * @param providerUserRequest OAuth2 인증 공급자 정보 요청 객체
     * @return 변환된 ProviderUser 객체 또는 지원되지 않는 공급자인 경우 null
     */
    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {
        for (ProviderOauth2Converter<ProviderUserRequest, ProviderUser> converter : converters) {
            ProviderUser result = converter.converter(providerUserRequest);

            if(result != null) {
                return result; // 지원하는 공급자일 경우 해당 Converter 결과 반환
            }
        }
        return null; // 어떤 Converter도 처리하지 못할 경우 null 반환
    }
}
