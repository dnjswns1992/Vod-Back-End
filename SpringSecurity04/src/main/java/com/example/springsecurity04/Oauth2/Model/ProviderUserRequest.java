package com.example.springsecurity04.Oauth2.Model;

import lombok.Data;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;


public record ProviderUserRequest(ClientRegistration registration, OAuth2User user) {

}

