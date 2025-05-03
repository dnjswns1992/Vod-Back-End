package com.example.StreamCraft.oauth2.model;

public interface ProviderOauth2Converter <T,R>{

    R converter(T t);

}
