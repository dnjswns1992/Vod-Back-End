package com.example.springsecurity04.Oauth2.Model;

import com.example.springsecurity04.Oauth2.ProviderUser.ProviderUser;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractDelegatingOauth2Converter implements ProviderOauth2Converter<ProviderUserRequest,ProviderUser>{

    private List<? extends ProviderOauth2Converter<ProviderUserRequest,ProviderUser>> converters
            = Arrays.asList(new GoogleConverter(),new NaverConverter());


    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {
        for (ProviderOauth2Converter<ProviderUserRequest, ProviderUser> converter : converters) {
            ProviderUser converter1 = converter.converter(providerUserRequest);

            if(converter1 != null) {
                return converter1;
            }
        }
        return null;
    }
}
