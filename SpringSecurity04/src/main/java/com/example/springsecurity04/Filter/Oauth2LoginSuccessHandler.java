package com.example.springsecurity04.Filter;

import com.example.springsecurity04.Dto.UserInformation;
import com.example.springsecurity04.JwtUtil.DetailsAccountUserDetail;
import com.example.springsecurity04.JwtUtil.JsonWebToken;
import com.example.springsecurity04.Repository.Oauth2UserRepository;
import com.example.springsecurity04.Table.UserAccount.Oauth2Entity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class Oauth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final Oauth2UserRepository oauth2UserRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        UserInformation userAccount = DetailsAccountUserDetail.getUserAccount();

        String remoteAddr = request.getRemoteAddr();

        Optional<Oauth2Entity> byEmail = oauth2UserRepository.findByEmail(userAccount.getUsername());

        if(byEmail.isPresent()) {
            byEmail.get().setUserIp(remoteAddr);
            oauth2UserRepository.save(byEmail.get());
        }


        String oauth2JwtToken = JsonWebToken.createOauth2JwtToken(authentication);

        log.info("jwt Token = {}","Bearer "+oauth2JwtToken);
        response.setContentType("application/json");
        response.addHeader("Authorization","Bearer "+oauth2JwtToken);
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("http://localhost:5178/callback?access-token=" + oauth2JwtToken);

    }
}
