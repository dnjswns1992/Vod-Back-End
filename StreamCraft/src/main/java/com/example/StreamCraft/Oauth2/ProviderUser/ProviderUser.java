package com.example.StreamCraft.Oauth2.ProviderUser;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;

public interface ProviderUser {

    String getId();
    String nickName();
    String profileImage();
    String email();
    String provider();
    List<? extends GrantedAuthority> getAuthority();
    Map<String,Object> getAttribute();
    String getNickName();
}
