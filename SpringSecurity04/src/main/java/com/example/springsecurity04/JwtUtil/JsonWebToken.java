package com.example.springsecurity04.JwtUtil;


import com.example.springsecurity04.Repository.UserRepository;
import com.example.springsecurity04.Service.jwtCheckService.UserDetailsInformation;
import com.example.springsecurity04.Table.UserAccount.UserEntity;
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

public class JsonWebToken {

    private final static SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

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

    public static Claims  getToken(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
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
