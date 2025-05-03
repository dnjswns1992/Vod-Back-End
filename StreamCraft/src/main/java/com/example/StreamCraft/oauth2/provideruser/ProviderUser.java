package com.example.StreamCraft.oauth2.provideruser;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;
/**
 * ✅ ProviderUser 인터페이스
 *
 * - OAuth2 로그인 후 각 소셜 제공자(Google, Naver 등)로부터 받아온 사용자 정보를 통일된 방식으로 다루기 위한 인터페이스입니다.
 * - Spring Security에서 인증된 사용자 정보로 활용하기 위한 공통 계약 정의
 * - 이를 구현하는 클래스(GoogleUser, NaverUser 등)는 각 플랫폼의 사용자 정보를 표준화된 형태로 제공하게 됩니다.
 */
public interface ProviderUser {

    /**
     * 소셜 제공자(Google, Naver 등)에서 발급한 고유 사용자 ID
     */
    String getId();

    /**
     * 사용자 닉네임 (공급자별로 "name" 또는 "nickname" 등)
     */
    String nickName();
    /**
     * 사용자 프로필 이미지 URL
     */
    String profileImage();
    /**
     * 사용자 이메일 주소
     */
    String email();
    /**
     * 로그인에 사용된 공급자 이름 (ex: "google", "naver")
     */
    String provider();

    /**
     * 사용자 권한 목록 (ROLE_USER 등)
     */
    List<? extends GrantedAuthority> getAuthority();
    /**
     * 공급자로부터 전달받은 사용자 속성 전체 Map
     */
    Map<String,Object> getAttribute();

    /**
     * 사용자 닉네임 반환 (nickName()과 같은 기능, 오버라이딩 상황에 따라 구분될 수 있음)
     * 현재 사용 X
     */
    String getNickName();
}
