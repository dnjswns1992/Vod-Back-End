package com.example.springsecurity04.Oauth2.ProviderUser;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;

public interface ProviderUser {

    String getId();
    String nickName();

    String email();
    String provider();
    List<? extends GrantedAuthority> getAuthority();
    Map<String,Object> getAttribute();
    String getNickName();
}
