package com.example.springsecurity04.Oauth2.Oauth2Service;

import com.example.springsecurity04.Converter.UserConverter;
import com.example.springsecurity04.Dto.UserDto;
import com.example.springsecurity04.Oauth2.Model.ProviderOauth2Converter;
import com.example.springsecurity04.Oauth2.Model.ProviderUserRequest;
import com.example.springsecurity04.Oauth2.ProviderUser.ProviderUser;
import com.example.springsecurity04.Repository.CommonRepository;
import com.example.springsecurity04.Repository.Oauth2UserRepository;
import com.example.springsecurity04.UserAccount.TransferModelMapper;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class Oauth2OidcUserService extends AbstractOauth2Service implements OAuth2UserService<OidcUserRequest, OidcUser> {

    public Oauth2OidcUserService(Oauth2UserRepository repository, CommonRepository commonRepository, UserConverter userConverter, ProviderOauth2Converter<ProviderUserRequest, ProviderUser> providerOauth2Converter, TransferModelMapper<ProviderUserRequest, UserDto> transferModelMapper) {
        super(repository, commonRepository, userConverter, providerOauth2Converter, transferModelMapper);
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        ClientRegistration registration = userRequest.getClientRegistration();

        OAuth2UserService<OidcUserRequest,OidcUser> oAuth2UserService = new OidcUserService();
        OidcUser oidcUser = oAuth2UserService.loadUser(userRequest);




        return null;
    }
}
