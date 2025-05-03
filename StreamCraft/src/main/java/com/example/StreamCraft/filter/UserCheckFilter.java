package com.example.StreamCraft.filter;

import com.example.StreamCraft.jwtutil.JwtTokenProvider;
import com.example.StreamCraft.Repository.user.UserRepository;
import com.example.StreamCraft.service.userdetails.UserDetailsInformation;
import com.example.StreamCraft.Entity.user.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@Component
@Lazy
public class UserCheckFilter extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationManager manager;
    private final UserRepository repository;

    public UserCheckFilter(AuthenticationManager manager,UserRepository repository) {
        super(manager);
      this.manager = manager;
      this.repository = repository;
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            UserEntity userEntity = mapper.readValue(request.getInputStream(), UserEntity.class);
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(userEntity.getUsername(),userEntity.getPassword(),
                            List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS")));
           return manager.authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"" + failed.getMessage() + "\"}");
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsInformation information = (UserDetailsInformation) authResult.getPrincipal();
        String username = information.getInformation().getUsername();

        String jwtToken = JwtTokenProvider.createJwtToken(username,repository);

        if(information != null) {


            response.addHeader("Authorization","Bearer "+jwtToken);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"token\": \"" + jwtToken + "\"}");

        }

    }
}
