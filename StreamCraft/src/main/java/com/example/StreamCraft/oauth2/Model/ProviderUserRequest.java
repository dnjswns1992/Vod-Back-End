package com.example.StreamCraft.oauth2.Model;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * OAuth2 로그인 요청 시 사용되는 DTO 클래스
 * - registration: OAuth2 공급자(Google, Naver 등)의 설정 정보
 * - user: OAuth2 인증 후 반환된 사용자 정보
 *
 * 예: Google 로그인 후 Google 사용자 정보와 등록된 Google Client 정보가 이 객체로 전달됨
 */
public record ProviderUserRequest(ClientRegistration registration, OAuth2User user) {

}

