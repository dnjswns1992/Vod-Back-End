package com.example.springsecurity04.Oauth2.Model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;
import java.util.Objects;
@Builder
@Getter
public class ConverterAttribute {

    private  Map<String, Object> googleMap;
    private  Map <String,Object> naverMap;

    public static ConverterAttribute googleAttributeConverter(OAuth2User oAuth2User){
        return ConverterAttribute.builder().googleMap(oAuth2User.getAttributes()).build();
    }
    public static ConverterAttribute NaverConverterAttribute(OAuth2User oAuth2User){
        Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttributes().get("response");
        return ConverterAttribute.builder().naverMap(response).build();
    }

}
