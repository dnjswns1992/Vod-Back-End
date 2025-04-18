package com.example.springsecurity04.Oauth2.ProviderUser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
public abstract class AbstractProvider implements ProviderUser {

    private ClientRegistration registration;
    private OAuth2User oAuth2User;
    private Map<String,Object> attribute;

    public AbstractProvider(ClientRegistration registration, OAuth2User oAuth2User, Map<String, Object> attribute) {
        this.registration = registration;
        this.oAuth2User = oAuth2User;
        this.attribute = attribute;
    }
    public String profileImage(){
        return attribute.get("picture").toString();
    }
    @Override
    public String email() {
        return attribute.get("email").toString();
    }

    @Override
    public String provider() {
        return registration.getRegistrationId();
    }

    @Override
    public List<? extends GrantedAuthority> getAuthority() {
        return oAuth2User.getAuthorities().stream().map(auth-> new SimpleGrantedAuthority(auth.getAuthority())).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getAttribute() {
        return attribute;
    }

    @Override
    public String nickName() {
        if(provider().equals("google"))  return "익명의 유저";
        else if(provider().equals("naver")) return "익명의 유저";
        return null;

    }
}
