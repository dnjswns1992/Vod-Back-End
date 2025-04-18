package com.example.springsecurity04.Oauth2.Oauth2Service;


import com.example.springsecurity04.Dto.UserDto;
import com.example.springsecurity04.Oauth2.Model.ProviderOauth2Converter;
import com.example.springsecurity04.Oauth2.Model.ProviderUserRequest;
import com.example.springsecurity04.Oauth2.ProviderUser.ProviderUser;
import com.example.springsecurity04.Repository.CommonRepository;
import com.example.springsecurity04.Repository.Oauth2UserRepository;
import com.example.springsecurity04.Table.UserAccount.Oauth2Entity;
import com.example.springsecurity04.UserAccount.TransferModelMapper;
import com.example.springsecurity04.Converter.UserConverter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@RequiredArgsConstructor
@Getter
@Slf4j
public abstract class AbstractOauth2Service {

    private final Oauth2UserRepository repository;
    private final CommonRepository commonRepository;
    private final UserConverter userConverter;

    private final ProviderOauth2Converter<ProviderUserRequest,ProviderUser> providerOauth2Converter;

    private final TransferModelMapper<ProviderUserRequest, UserDto> transferModelMapper;


    protected ProviderUser Converter(ProviderUserRequest userRequest){

        ProviderUser converter = providerOauth2Converter.converter(userRequest);
            return converter;
    }
    protected void register(ProviderUser providerUser){


        Optional<Oauth2Entity> byEmail = repository.findByEmail(providerUser.email());

        if(byEmail.isEmpty()) {


            if (providerUser.provider().equals("google")) {

                Oauth2Entity oauth2Entity = Oauth2Entity.builder().nickName(providerUser.email())
                        .email(providerUser.email()).provider(providerUser.provider())
                        .role("ROLE_USER").Image(providerUser.profileImage()).build();

                repository.save(oauth2Entity);

                userConverter.Oauth2UserRegister(providerUser,oauth2Entity);





            } else if(providerUser.provider().equals("naver")) {
                Oauth2Entity oauth2Entity = Oauth2Entity.builder()
                        .nickName(providerUser.nickName())
                        .email(providerUser.email())
                        .role("ROLE_USER")
                        .Image(providerUser.profileImage())
                        .provider(providerUser.provider())
                        .build();

                repository.save(oauth2Entity);

                userConverter.Oauth2UserRegister(providerUser,oauth2Entity);
            }
        }


    }



}
