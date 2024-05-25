package com.example.springsecurity04.Oauth2.ProviderUser;

import com.example.springsecurity04.Oauth2.Model.ConverterAttribute;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class NaverUser extends AbstractProvider{

    public NaverUser(ClientRegistration registration, OAuth2User oAuth2User, ConverterAttribute converterAttribute) {
        super(registration, oAuth2User, converterAttribute.getNaverMap());
    }

    @Override
    public String getId() {
        return getAttribute().get("id").toString();
    }

    @Override
    public String getNickName() {
        return getAttribute().get("nickname").toString();
    }
}
