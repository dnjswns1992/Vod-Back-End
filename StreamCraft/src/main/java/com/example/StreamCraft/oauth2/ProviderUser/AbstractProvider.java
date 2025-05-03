package com.example.StreamCraft.oauth2.ProviderUser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ✅ AbstractProvider는 OAuth2 로그인 공급자(Google, Naver 등)로부터 받은 사용자 정보를
 *    공통된 형식으로 변환해주는 추상 클래스입니다.
 *
 * 이 클래스를 상속받는 ProviderUser(GoogleUser, NaverUser 등)는 로그인 시 OAuth2User의 정보를
 * 프로젝트 내부 형식에 맞게 통일하여 사용하게 됩니다.
 */
@Slf4j
public abstract class AbstractProvider implements ProviderUser {

    private ClientRegistration registration; // 어떤 OAuth2 공급자인지 (google, naver 등)
    private OAuth2User oAuth2User; // 인증된 사용자 정보 (Spring Security 제공)
    private Map<String,Object> attribute; // 사용자 속성 정보 (이메일, 이름 등)

    /**
     * 생성자 - OAuth2 인증 후 필요한 정보들을 주입받음
     */
    public AbstractProvider(ClientRegistration registration, OAuth2User oAuth2User, Map<String, Object> attribute) {
        this.registration = registration;
        this.oAuth2User = oAuth2User;
        this.attribute = attribute;
    }
    /**
     * ✅ 프로필 이미지 URL 반환
     * - Google의 경우 picture 속성 사용
     */
    public String profileImage(){
        return attribute.get("picture").toString();
    }
    /**
     * ✅ 사용자 이메일 반환
     */
    @Override
    public String email() {
        return attribute.get("email").toString();
    }
    /**
     * ✅ 어떤 공급자(Google, Naver 등)로 로그인했는지 반환
     */
    @Override
    public String provider() {
        return registration.getRegistrationId();
    }
    /**
     * ✅ 사용자의 권한 목록 반환
     * - 기본적으로 OAuth2User가 가지고 있는 권한을 Spring Security용으로 변환
     */
    @Override
    public List<? extends GrantedAuthority> getAuthority() {
        return oAuth2User.getAuthorities().stream().
                map(auth-> new SimpleGrantedAuthority(auth.getAuthority())).collect(Collectors.toList());
    }


    /**
     * ✅ 사용자 속성 반환
     */
    @Override
    public Map<String, Object> getAttribute() {
        return attribute;
    }

    /**
     * ✅ 닉네임 반환
     * - 기본적으로 공급자별로 "익명의 유저"를 디비에 저장
     * - Oauth 사용자는 유저 이름을 설정할 수 없음으로 익명의 유저로 명명
     * - 추후 변경가능
     */
    @Override
    public String nickName() {
        if(provider().equals("google"))  return "익명의 유저";
        else if(provider().equals("naver")) return "익명의 유저";
        return null;

    }
}
