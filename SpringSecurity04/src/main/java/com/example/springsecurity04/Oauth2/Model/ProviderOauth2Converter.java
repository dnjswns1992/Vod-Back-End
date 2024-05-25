package com.example.springsecurity04.Oauth2.Model;

public interface ProviderOauth2Converter <T,R>{

    R converter(T t);

}
