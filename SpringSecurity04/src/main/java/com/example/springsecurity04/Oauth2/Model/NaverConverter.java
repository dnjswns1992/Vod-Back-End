package com.example.springsecurity04.Oauth2.Model;

import com.example.springsecurity04.Oauth2.ProviderUser.NaverUser;
import com.example.springsecurity04.Oauth2.ProviderUser.ProviderUser;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;

public class NaverConverter implements ProviderOauth2Converter<ProviderUserRequest,ProviderUser> {

    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {
        if(!providerUserRequest.registration().getRegistrationId().equals("naver")) return null;

        ConverterAttribute converterAttribute = ConverterAttribute.NaverConverterAttribute(providerUserRequest.user());

        return new NaverUser(providerUserRequest.registration(), providerUserRequest.user(),converterAttribute);
    }
}
