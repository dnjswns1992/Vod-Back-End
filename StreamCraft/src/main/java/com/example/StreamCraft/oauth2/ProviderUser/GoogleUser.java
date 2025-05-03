package com.example.StreamCraft.oauth2.ProviderUser;

import com.example.StreamCraft.oauth2.Model.ConverterAttribute;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;
/**
 * ✅ GoogleUser 클래스
 *
 * - Google OAuth2 로그인 후 받은 사용자 정보를 표준 ProviderUser 형식으로 감싸는 클래스
 * - AbstractProvider 추상 클래스를 상속하여 공통 필드/메서드 활용
 * - ConverterAttribute에서 구글 정보 Map을 추출해 부모 클래스에 전달
 */
public class GoogleUser extends AbstractProvider{

    /**
     * 생성자: 구글 사용자 정보로 GoogleUser 인스턴스 생성
     *
     * @param registration OAuth2 ClientRegistration (구글 공급자 정보)
     * @param oAuth2User OAuth2User 객체 (인증된 사용자 정보)
     * @param converterAttribute 사용자 속성 정보를 담고 있는 컨버터 DTO
     */
    public GoogleUser(ClientRegistration registration, OAuth2User oAuth2User, ConverterAttribute converterAttribute){
        super(registration,oAuth2User,converterAttribute.getGoogleMap());
    }
    /**
     * ✅ Google 사용자의 고유 ID 반환
     * - 구글의 경우 "sub" 키가 고유 식별자
     */
    @Override
    public String getId() {
        return getAttribute().get("sub").toString();
    }
    /**
     * ✅ Google 사용자의 닉네임 반환
     * - 일반적으로 "name" 속성 사용
     */
    @Override
    public String getNickName() {
        return getAttribute().get("name").toString();
    }


}
