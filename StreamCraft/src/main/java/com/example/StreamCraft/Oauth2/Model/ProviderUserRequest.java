package com.example.StreamCraft.Oauth2.Model;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;


public record ProviderUserRequest(ClientRegistration registration, OAuth2User user) {

}

