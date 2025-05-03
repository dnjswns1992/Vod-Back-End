package com.example.StreamCraft.Oauth2.Model;

import com.example.StreamCraft.Oauth2.ProviderUser.NaverUser;
import com.example.StreamCraft.Oauth2.ProviderUser.ProviderUser;

public class NaverConverter implements ProviderOauth2Converter<ProviderUserRequest,ProviderUser> {

    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {
        if(!providerUserRequest.registration().getRegistrationId().equals("naver")) return null;

        ConverterAttribute converterAttribute = ConverterAttribute.NaverConverterAttribute(providerUserRequest.user());

        return new NaverUser(providerUserRequest.registration(), providerUserRequest.user(),converterAttribute);
    }
}
