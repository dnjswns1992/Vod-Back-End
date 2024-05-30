package com.example.springsecurity04.Filter;

import com.example.springsecurity04.Dto.UserInformation;
import com.example.springsecurity04.JwtUtil.JsonWebToken;
import com.example.springsecurity04.Oauth2.ProviderUser.FormLoginOauthCombine;
import com.example.springsecurity04.Repository.CommonRepository;
import com.example.springsecurity04.Repository.Oauth2UserRepository;
import com.example.springsecurity04.Repository.UserRepository;
import com.example.springsecurity04.Service.jwtCheckService.UserDetailsInformation;
import com.example.springsecurity04.Table.Common.CommonEntity;
import com.example.springsecurity04.Table.UserAccount.Oauth2Entity;
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
        UserInformation userInformation = null;
        UserDetailsInformation detailsInformation = null;

        authorization = request.getHeader("Authorization");
        logger.debug("Request URI: " + request.getRequestURI());


        if (!request.getRequestURI().startsWith("/authenticated/") && !request.getRequestURI().startsWith("/admin/")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.info("Authorization header is missing or doesn't start with 'Bearer '");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            String token = authorization.split(" ")[1];
            Claims username = JsonWebToken.getToken(token);
            String provider = username.get("provider", String.class);

            if (provider.equals("FormLogin")) {
                String findUsername = username.get("username", String.class);
                Optional<CommonEntity> byUsername = commonRepository.findByUsername(findUsername);

                if (byUsername.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                userInformation = FormLoginOauthCombine.CommonCombine(byUsername.get());

            } else {
                String oauth2Username = username.get("username", String.class);
                Optional<Oauth2Entity> oauth2User = oauth2UserRepository.findByEmail(oauth2Username);

                if (oauth2User.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                userInformation = FormLoginOauthCombine.Oauth2Combine(oauth2User.get());
            }

            UserDetailsInformation userDetailsInformation = new UserDetailsInformation(userInformation);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetailsInformation, userDetailsInformation.getPassword(), userDetailsInformation.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.info("Exception in JWT Filter: {}", e.getMessage());

            if (e instanceof io.jsonwebtoken.security.SignatureException) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
}
