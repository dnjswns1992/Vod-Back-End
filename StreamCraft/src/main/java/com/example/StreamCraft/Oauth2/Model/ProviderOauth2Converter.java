package com.example.StreamCraft.Oauth2.Model;

public interface ProviderOauth2Converter <T,R>{

    R converter(T t);

}
