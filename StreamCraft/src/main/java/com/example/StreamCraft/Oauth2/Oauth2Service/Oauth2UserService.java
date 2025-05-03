package com.example.StreamCraft.Oauth2.Oauth2Service;

import com.example.StreamCraft.dto.user.UserRegisterDto;
import com.example.StreamCraft.dto.user.UserInfoResponseDto;
import com.example.StreamCraft.Oauth2.Model.ProviderOauth2Converter;
import com.example.StreamCraft.Oauth2.Model.ProviderUserRequest;
import com.example.StreamCraft.Oauth2.ProviderUser.ProviderUser;
import com.example.StreamCraft.Repository.CommonRepository;
import com.example.StreamCraft.Repository.Oauth2UserRepository;
import com.example.StreamCraft.Service.jwtCheckService.UserDetailsInformation;
import com.example.StreamCraft.UserAccount.TransferModelMapper;
import com.example.StreamCraft.converter.UserConverter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class Oauth2UserService extends AbstractOauth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {


    public Oauth2UserService(Oauth2UserRepository repository, CommonRepository commonRepository, UserConverter userConverter, ProviderOauth2Converter<ProviderUserRequest, ProviderUser> providerOauth2Converter, TransferModelMapper<ProviderUserRequest, UserRegisterDto> transferModelMapper) {
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


            UserInfoResponseDto information = new UserInfoResponseDto(converter);

            UserDetailsInformation userDetailsInformation = new UserDetailsInformation(information);
            return userDetailsInformation;

    }
}
