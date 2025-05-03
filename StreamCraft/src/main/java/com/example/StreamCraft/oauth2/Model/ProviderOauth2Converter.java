package com.example.StreamCraft.oauth2.Model;

public interface ProviderOauth2Converter <T,R>{

    R converter(T t);

}
