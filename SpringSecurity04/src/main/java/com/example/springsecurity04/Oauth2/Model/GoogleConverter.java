package com.example.springsecurity04.Oauth2.Model;

import com.example.springsecurity04.Oauth2.ProviderUser.GoogleUser;
import com.example.springsecurity04.Oauth2.ProviderUser.ProviderUser;

public class GoogleConverter implements ProviderOauth2Converter<ProviderUserRequest,ProviderUser> {

    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {

        if(!providerUserRequest.registration().getRegistrationId().equals("google")) return null;

        ConverterAttribute converterAttribute = ConverterAttribute.googleAttributeConverter(providerUserRequest.user());


        return new GoogleUser(providerUserRequest.registration(), providerUserRequest.user(),converterAttribute);
    }
}
