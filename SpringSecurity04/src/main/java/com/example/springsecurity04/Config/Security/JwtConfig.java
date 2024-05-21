package com.example.springsecurity04.Config.Security;


import com.example.springsecurity04.Filter.JwtCheckFilter;
import com.example.springsecurity04.Filter.Oauth2LoginSuccessHandler;
import com.example.springsecurity04.Filter.UserCheckFilter;
import com.example.springsecurity04.Repository.CommonRepository;
import com.example.springsecurity04.Repository.Oauth2UserRepository;
import com.example.springsecurity04.Repository.UserRepository;

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
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class JwtConfig {


    private final UserRepository repository;
    private final Oauth2UserRepository oauth2UserRepository;
    private final CommonRepository commonRepository;



    @Bean
    SecurityFilterChain filterChain(HttpSecurity security, AuthenticationConfiguration configuration,  @Qualifier("corsConfigurationSource") CorsConfigurationSource configurationSource) throws Exception {
        security.httpBasic(AbstractHttpConfigurer :: disable);
        security.formLogin(AbstractHttpConfigurer :: disable);
        security.csrf(csrf -> csrf.disable());
        security.csrf(AbstractHttpConfigurer ::disable);
        security.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        security.cors(cors -> cors.configurationSource(configurationSource));

        security.addFilterBefore(new UserCheckFilter(configuration.getAuthenticationManager(),repository), UsernamePasswordAuthenticationFilter.class);
        security.addFilterBefore(new JwtCheckFilter(repository,oauth2UserRepository,commonRepository), UsernamePasswordAuthenticationFilter.class);

        security.authorizeHttpRequests(request -> request.antMatchers("/register","/login","/user/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/manager/**").hasAnyRole("ADMIN","MANAGER")
                .antMatchers("/authenticated/**").hasAnyRole("ADMIN","MANAGER","USER")
                .anyRequest().authenticated());

        security.exceptionHandling(halnder-> halnder.accessDeniedHandler(new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                log.info("권한 에러 터지는지 확인 중");
            }
        }));


        security.oauth2Login(login -> login.successHandler(new Oauth2LoginSuccessHandler(oauth2UserRepository)));



        return security.build();
        }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    @Lazy
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().antMatchers("static/css/**");
    }

}
