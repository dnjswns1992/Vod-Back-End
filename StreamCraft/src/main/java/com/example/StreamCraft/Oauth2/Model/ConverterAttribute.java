package com.example.StreamCraft.Oauth2.Model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

/**
 * OAuth2 인증 후 받아온 사용자 정보를 소셜 로그인 제공자(Google, Naver)에 따라 분기하여 저장하는 클래스
 * - 각 소셜 플랫폼에 따라 사용자 정보 형식이 다르기 때문에 분리 저장
 */

@Builder
@Getter
public class ConverterAttribute {

    // 구글 로그인 사용자 정보 맵 (그대로 attributes에 담겨 있음)
    private  Map<String, Object> googleMap;
    // 네이버 로그인 사용자 정보 맵 ("response" 내부에 있음)
    private  Map <String,Object> naverMap;

    /**
     * 구글 로그인 응답을 변환하여 ConverterAttribute 객체 생성
     *
     * @param oAuth2User OAuth2 인증된 사용자 정보
     * @return ConverterAttribute 객체 (googleMap에 데이터 저장됨)
     */
    public static ConverterAttribute googleAttributeConverter(OAuth2User oAuth2User){
        return ConverterAttribute.builder().googleMap(oAuth2User.getAttributes()).build();
    }
    public static ConverterAttribute NaverConverterAttribute(OAuth2User oAuth2User){
        Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttributes().get("response");
        return ConverterAttribute.builder().naverMap(response).build();
    }

}
