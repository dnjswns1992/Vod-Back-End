package com.example.StreamCraft.filter;

import com.example.StreamCraft.dto.user.UserInfoResponseDto;
import com.example.StreamCraft.jwtutil.JwtTokenProvider;
import com.example.StreamCraft.oauth2.ProviderUser.FormLoginOauthCombine;
import com.example.StreamCraft.Repository.user.CommonRepository;
import com.example.StreamCraft.Repository.user.Oauth2UserRepository;
import com.example.StreamCraft.Repository.user.UserRepository;
import com.example.StreamCraft.service.userdetails.UserDetailsInformation;
import com.example.StreamCraft.Entity.commom.MergedUserEntity;
import com.example.StreamCraft.Entity.user.Oauth2Entity;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * JWT 토큰 유효성을 검사하고 인증 정보를 Spring Security 컨텍스트에 설정하는 필터
 * - /authenticated/** 또는 /admin/** 요청 시 필수
 * - FormLogin 사용자와 OAuth2 사용자를 구분하여 처리
 */

@Lazy
@RequiredArgsConstructor
@Slf4j
@Component
public class JwtCheckFilter extends OncePerRequestFilter {

    private final UserRepository repository;
    private final Oauth2UserRepository oauth2UserRepository;
    private final CommonRepository commonRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = null;
        UserInfoResponseDto userInfoResponseDto = null;
        UserDetailsInformation detailsInformation = null;

        authorization = request.getHeader("Authorization"); // 요청 헤더에서 토큰 추출
        logger.debug("Request URI: " + request.getRequestURI());


        // 인증이 필요한 URI가 아니면 필터를 통과시킴

        if (!request.getRequestURI().startsWith("/authenticated/") && !request.getRequestURI().startsWith("/admin/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Authorization 헤더 검증

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.info("Authorization header is missing or doesn't start with 'Bearer '");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            // Bearer 토큰 추출 및 파싱

            String token = authorization.split(" ")[1];
            Claims username = JwtTokenProvider.getToken(token);
            String provider = username.get("provider", String.class);

            // 로그인 방식에 따라 사용자 정보 조회

            if (provider.equals("FormLogin")) {
                String findUsername = username.get("username", String.class);
                Optional<MergedUserEntity> byUsername = commonRepository.findByUsername(findUsername);

                if (byUsername.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                userInfoResponseDto = FormLoginOauthCombine.CommonCombine(byUsername.get());

            } else {

                // OAuth2 로그인

                String oauth2Username = username.get("username", String.class);
                Optional<Oauth2Entity> oauth2User = oauth2UserRepository.findByEmail(oauth2Username);

                if (oauth2User.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                userInfoResponseDto = FormLoginOauthCombine.Oauth2Combine(oauth2User.get());
            }

            // 사용자 정보를 기반으로 UserDetails 객체 생성 및 시큐리티 컨텍스트 설정

            UserDetailsInformation userDetailsInformation = new UserDetailsInformation(userInfoResponseDto);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetailsInformation, userDetailsInformation.getPassword(), userDetailsInformation.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            filterChain.doFilter(request, response);  // 다음 필터로 요청 전달

        } catch (Exception e) {
            log.info("Exception in JWT Filter: {}", e.getMessage());

            if (e instanceof io.jsonwebtoken.security.SignatureException) {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 잘못된 서명

            } else {

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 일반 오류

            }
        }
    }
}
