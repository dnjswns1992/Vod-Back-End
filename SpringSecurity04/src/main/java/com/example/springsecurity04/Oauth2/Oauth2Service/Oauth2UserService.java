package com.example.springsecurity04.Oauth2.Oauth2Service;

import com.example.springsecurity04.Dto.UserDto;
import com.example.springsecurity04.Dto.UserInformation;
import com.example.springsecurity04.Oauth2.Model.ProviderOauth2Converter;
import com.example.springsecurity04.Oauth2.Model.ProviderUserRequest;
import com.example.springsecurity04.Oauth2.ProviderUser.ProviderUser;
import com.example.springsecurity04.Repository.CommonRepository;
import com.example.springsecurity04.Repository.Oauth2UserRepository;
import com.example.springsecurity04.Service.jwtCheckService.UserDetailsInformation;
import com.example.springsecurity04.UserAccount.TransferModelMapper;
import com.example.springsecurity04.Converter.UserConverter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class Oauth2UserService extends AbstractOauth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {


    public Oauth2UserService(Oauth2UserRepository repository, CommonRepository commonRepository, UserConverter userConverter, ProviderOauth2Converter<ProviderUserRequest, ProviderUser> providerOauth2Converter, TransferModelMapper<ProviderUserRequest, UserDto> transferModelMapper) {
        super(repository, commonRepository, userConverter, providerOauth2Converter, transferModelMapper);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        ClientRegistration clientRegistration = userRequest.getClientRegistration();

        OAuth2UserService<OAuth2UserRequest,OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        ProviderUserRequest providerUserRequest = new ProviderUserRequest(clientRegistration, oAuth2User);

        ProviderUser converter = super.Converter(providerUserRequest);


       super.register(converter);


            UserInformation information = new UserInformation(converter);

            UserDetailsInformation userDetailsInformation = new UserDetailsInformation(information);
            return userDetailsInformation;

    }
}
