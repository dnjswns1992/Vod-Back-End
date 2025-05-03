package com.example.StreamCraft.service.userdetails;

import com.example.StreamCraft.dto.user.UserInfoResponseDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
/**
 * ✅ UserDetails + OAuth2User 를 동시에 구현한 사용자 인증 클래스
 * - Spring Security에서 사용자를 인증한 후 보안 컨텍스트에 저장할 객체
 * - Form 로그인 / OAuth2 로그인 사용자 모두를 이 클래스로 통합하여 관리
 */
@Data
@Slf4j
public class UserDetailsInformation implements UserDetails, OAuth2User {


    // 사용자 정보 DTO (닉네임, 이메일, 권한 등 포함)
    //jwtToken을 사용하기위해 Oauth 사용자와 , 일반 사용자의 Dto를 변환하여 저장
    private final UserInfoResponseDto information;

    public UserDetailsInformation(UserInfoResponseDto information) {
        this.information = information;
    }
    /**
     * OAuth2User 인터페이스 구현 (현재 사용 안 함)
     */
    @Override
    public <A> A getAttribute(String name) {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
       return null;
    }



    /**
     * ✅ 사용자 권한(ROLE_USER 등) 반환
     * - UserInfoResponseDto의 role 값을 기반으로 GrantedAuthority 구현
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return information.getRole();
            }
        });
        return authorities;
    }

    /**
     * ✅ 사용자 비밀번호 반환
     * - Form 로그인 사용자만 사용 (OAuth2는 password="없음")
     */
    @Override
    public String getPassword() {
        return information.getPassword();
    }
    /**
     * ✅ 사용자 이름 반환 (username 또는 이메일)
     */
    @Override
    public String getUsername() {
        return information.getUsername();
    }
    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // 비밀번호 만료 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
