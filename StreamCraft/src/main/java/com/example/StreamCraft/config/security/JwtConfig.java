// JWT 기반 Spring Security 설정 클래스
package com.example.StreamCraft.config.security;

import com.example.StreamCraft.filter.JwtCheckFilter;
import com.example.StreamCraft.filter.Oauth2LoginSuccessHandler;
import com.example.StreamCraft.filter.UserCheckFilter;
import com.example.StreamCraft.Repository.user.CommonRepository;
import com.example.StreamCraft.Repository.user.Oauth2UserRepository;
import com.example.StreamCraft.Repository.user.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity // Spring Security 활성화
@RequiredArgsConstructor
@Slf4j
public class JwtConfig {

    private final UserRepository repository;
    private final Oauth2UserRepository oauth2UserRepository;
    private final CommonRepository commonRepository;

    // 보안 필터 체인 구성
    @Bean
    SecurityFilterChain filterChain(
            HttpSecurity security,
            AuthenticationConfiguration configuration,
            @Qualifier("corsConfigurationSource") CorsConfigurationSource corsConfigurationSource
    ) throws Exception {

        // 기본 인증, 로그인 폼 비활성화
        security.httpBasic(AbstractHttpConfigurer::disable);
        security.formLogin(AbstractHttpConfigurer::disable);
        security.csrf(csrf -> csrf.disable()); // CSRF 비활성화 (JWT 사용 시 일반적)

        // 세션 비활성화 → JWT 기반 인증은 무상태(stateless)
        security.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // CORS 설정 적용
        security.cors(cors -> cors.configurationSource(corsConfigurationSource));

        // 커스텀 필터 추가 (인증 전)
        security.addFilterBefore(
                new UserCheckFilter(configuration.getAuthenticationManager(), repository),
                UsernamePasswordAuthenticationFilter.class
        );
        security.addFilterBefore(
                new JwtCheckFilter(repository, oauth2UserRepository, commonRepository),
                UsernamePasswordAuthenticationFilter.class
        );

        // 권한/역할 기반 접근 제어
        security.authorizeHttpRequests(request -> request
                .antMatchers(
                        "/register", "/check-username", "/check-email",
                        "/api/**", "/login", "/user/**", "/error", "/ws/**"
                ).permitAll() // 인증 없이 허용
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/authenticated/**").hasAnyRole("ADMIN", "MANAGER", "USER")
                .anyRequest().authenticated() // 나머지는 인증 필요
        );

        // 권한 예외 처리 핸들러
        security.exceptionHandling(handler -> handler.accessDeniedHandler(new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                log.info("권한 에러 터지는지 확인 중");

            }
        }));

        // OAuth2 로그인 성공 핸들러 설정
        security.oauth2Login(login -> login
                .successHandler(new Oauth2LoginSuccessHandler(oauth2UserRepository))
        );

        return security.build();
    }

    // 비밀번호 암호화기 (BCrypt)
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    // 인증 매니저 Bean 등록 (로그인 필터나 기타 인증 기능에서 필요)
    @Bean
    @Lazy
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // 정적 자원 등 보안 필터 적용 제외
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("static/css/**", "/ws/upload-progress");
    }
}
