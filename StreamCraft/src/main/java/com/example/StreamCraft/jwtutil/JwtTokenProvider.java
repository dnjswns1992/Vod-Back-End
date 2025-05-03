package com.example.StreamCraft.jwtutil;


import com.example.StreamCraft.Repository.user.UserRepository;
import com.example.StreamCraft.service.userdetails.UserDetailsInformation;
import com.example.StreamCraft.Entity.user.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;

/**
 * JWT 토큰 생성 및 파싱 관련 유틸리티 클래스
 */
public class JwtTokenProvider {

    // 서명 알고리즘을 위한 SecretKey 생성 (HS256)
    private final static SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * 일반 사용자(Form 로그인)용 JWT 토큰을 생성합니다.
     * @param username 사용자 ID
     * @param repository 사용자 정보 조회용 Repository
     * @return JWT 토큰 문자열
     */
    public static String createJwtToken(String username, UserRepository repository){

        UserEntity userEntity = repository.findUserEntityByUsername(username).get();

        Claims claims = Jwts.claims();

        claims.put("username",userEntity.getUsername());
        claims.put("role",userEntity.getRole());
        claims.put("provider",userEntity.getProvider());


        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (60000 * 30 * 24)))
                .signWith(key).compact();
    }

    /**
     * JWT 토큰에서 Claims(정보)를 추출합니다.
     * @param token JWT 토큰
     * @return Claims - JWT에 포함된 정보
     */
    public static Claims  getToken(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /**
     * OAuth2 로그인 사용자용 JWT 토큰을 생성합니다.
     * @param authentication 인증 정보
     * @return JWT 토큰 문자열
     */
    public static String createOauth2JwtToken(Authentication authentication){
        UserDetailsInformation principal = (UserDetailsInformation) authentication.getPrincipal();
        long expire = 60000 * 24 * 30;
        Claims claims = Jwts.claims();

        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
        claims.put("provider",principal.getInformation().getProvider());
        claims.put("username",principal.getInformation().getUsername());


        for (GrantedAuthority authority : authorities) {
            claims.put("role",authority.getAuthority());
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .signWith(key).compact();

    }
}
