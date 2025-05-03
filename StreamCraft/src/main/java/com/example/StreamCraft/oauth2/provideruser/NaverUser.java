package com.example.StreamCraft.oauth2.provideruser;

import com.example.StreamCraft.oauth2.model.ConverterAttribute;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

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
