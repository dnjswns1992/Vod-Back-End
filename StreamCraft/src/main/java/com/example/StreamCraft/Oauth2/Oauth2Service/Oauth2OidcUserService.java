package com.example.StreamCraft.Oauth2.Oauth2Service;

import com.example.StreamCraft.converter.UserConverter;
import com.example.StreamCraft.dto.user.UserRegisterDto;
import com.example.StreamCraft.Oauth2.Model.ProviderOauth2Converter;
import com.example.StreamCraft.Oauth2.Model.ProviderUserRequest;
import com.example.StreamCraft.Oauth2.ProviderUser.ProviderUser;
import com.example.StreamCraft.Repository.CommonRepository;
import com.example.StreamCraft.Repository.Oauth2UserRepository;
import com.example.StreamCraft.UserAccount.TransferModelMapper;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class Oauth2OidcUserService extends AbstractOauth2Service implements OAuth2UserService<OidcUserRequest, OidcUser> {

    public Oauth2OidcUserService(Oauth2UserRepository repository, CommonRepository commonRepository, UserConverter userConverter, ProviderOauth2Converter<ProviderUserRequest, ProviderUser> providerOauth2Converter, TransferModelMapper<ProviderUserRequest, UserRegisterDto> transferModelMapper) {
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
