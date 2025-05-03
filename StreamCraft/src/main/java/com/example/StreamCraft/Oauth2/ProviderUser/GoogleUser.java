package com.example.StreamCraft.Oauth2.ProviderUser;

import com.example.StreamCraft.Oauth2.Model.ConverterAttribute;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class GoogleUser extends AbstractProvider{


    public GoogleUser(ClientRegistration registration, OAuth2User oAuth2User, ConverterAttribute converterAttribute){
        super(registration,oAuth2User,converterAttribute.getGoogleMap());
    }

    @Override
    public String getId() {
        return getAttribute().get("sub").toString();
    }

    @Override
    public String getNickName() {
        return getAttribute().get("name").toString();
    }


}
