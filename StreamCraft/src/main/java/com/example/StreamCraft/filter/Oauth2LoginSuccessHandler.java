package com.example.StreamCraft.filter;

import com.example.StreamCraft.dto.user.UserInfoResponseDto;
import com.example.StreamCraft.jwtutil.AuthenticatedUserUtil;
import com.example.StreamCraft.jwtutil.JwtTokenProvider;
import com.example.StreamCraft.Repository.Oauth2UserRepository;
import com.example.StreamCraft.Table.UserAccount.Oauth2Entity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * OAuth2 로그인 성공 시 실행되는 핸들러
 * - 사용자 IP 저장
 * - JWT 토큰 발급
 * - 클라이언트 페이지로 토큰 포함하여 리다이렉트
 */

@Slf4j
@RequiredArgsConstructor
public class Oauth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final Oauth2UserRepository oauth2UserRepository;
    /**
     * OAuth2 로그인 성공 시 동작
     * @param request 클라이언트 요청
     * @param response 서버 응답
     * @param authentication 인증된 사용자 정보
     */

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // JWT 토큰 생성에 사용할 사용자 정보 추출
        UserInfoResponseDto userAccount = AuthenticatedUserUtil.getUserAccount();

        // 클라이언트 IP 추출
        String remoteAddr = request.getRemoteAddr();

        // 사용자 이메일로 Oauth2Entity 조회 후 IP 업데이트
        Optional<Oauth2Entity> byEmail = oauth2UserRepository.findByEmail(userAccount.getUsername());

        if(byEmail.isPresent()) {
            byEmail.get().setUserIp(remoteAddr);
            oauth2UserRepository.save(byEmail.get());
        }


        // OAuth2 사용자용 JWT 생성
        String oauth2JwtToken = JwtTokenProvider.createOauth2JwtToken(authentication);

        // 응답 헤더 및 상태 설정
        log.info("jwt Token = {}","Bearer "+oauth2JwtToken);
        response.setContentType("application/json");
        response.addHeader("Authorization","Bearer "+oauth2JwtToken);
        response.setStatus(HttpServletResponse.SC_OK);

        // 프론트엔드 페이지로 리다이렉트 (토큰 포함)
        response.sendRedirect("https://localhost:5173/callback?access-token=" + oauth2JwtToken);

    }
}
