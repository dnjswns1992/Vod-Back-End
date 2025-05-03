package com.example.StreamCraft.oauth2.Oauth2Service;


import com.example.StreamCraft.dto.user.UserRegisterDto;
import com.example.StreamCraft.oauth2.Model.ProviderOauth2Converter;
import com.example.StreamCraft.oauth2.Model.ProviderUserRequest;
import com.example.StreamCraft.oauth2.ProviderUser.ProviderUser;
import com.example.StreamCraft.Repository.user.CommonRepository;
import com.example.StreamCraft.Repository.user.Oauth2UserRepository;
import com.example.StreamCraft.Entity.user.Oauth2Entity;
import com.example.StreamCraft.UserAccount.TransferModelMapper;
import com.example.StreamCraft.converter.UserConverter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * ✅ 소셜 로그인(OAuth2) 관련 공통 로직을 담당하는 추상 서비스 클래스
 * - ProviderUser 변환
 *
 * - 최초 로그인 시 회원 등록 처리
 *
 * ✅ 상속 구조로 GoogleOauth2Service, NaverOauth2Service 등에서 확장 가능
 */
@RequiredArgsConstructor
@Getter
@Slf4j
public abstract class AbstractOauth2Service {
    // OAuth2 사용자 정보를 저장하는 Repository (구글/네이버 사용자)
    private final Oauth2UserRepository repository;
    // 공용 사용자 정보 저장소
    private final CommonRepository commonRepository;
    // 회원가입/공용 등록 처리 도구
    private final UserConverter userConverter;
    // 공급자에 따라 ProviderUser 로 변환해주는 인터페이스
    private final ProviderOauth2Converter<ProviderUserRequest,ProviderUser> providerOauth2Converter;
    // DTO 매핑 도구 (필요 시 확장 가능)
    private final TransferModelMapper<ProviderUserRequest, UserRegisterDto> transferModelMapper;

    /**
     * ✅ ProviderUserRequest → ProviderUser 로 변환
     * @param userRequest OAuth2 로그인 요청 정보
     * @return 변환된 ProviderUser (GoogleUser, NaverUser 등)
     */
    protected ProviderUser Converter(ProviderUserRequest userRequest){

        ProviderUser converter = providerOauth2Converter.converter(userRequest);
            return converter;
    }
    /**
     * ✅ OAuth2 최초 로그인 시 사용자 등록 로직
     * - 이미 등록된 이메일이 없을 경우만 수행
     * - 공급자(Google/Naver)에 따라 저장 방식 다름
     *
     * @param providerUser 소셜 로그인에서 변환된 사용자 정보
     */
    protected void register(ProviderUser providerUser){


        Optional<Oauth2Entity> byEmail = repository.findByEmail(providerUser.email());

        // 중복 방지 - 이미 등록된 유저는 등록 안함
        if(byEmail.isEmpty()) {

            // 구글 로그인인 경우

            if (providerUser.provider().equals("google")) {

                Oauth2Entity oauth2Entity = Oauth2Entity.builder().nickName(providerUser.email())
                        .email(providerUser.email()).provider(providerUser.provider())
                        .role("ROLE_USER").Image(providerUser.profileImage()).build();

                repository.save(oauth2Entity);

                // 공용 테이블(CommonEntity)에도 등록
                userConverter.Oauth2UserRegister(providerUser,oauth2Entity);




                // 네이버 로그인인 경우
            } else if(providerUser.provider().equals("naver")) {
                Oauth2Entity oauth2Entity = Oauth2Entity.builder()
                        .nickName(providerUser.nickName())
                        .email(providerUser.email())
                        .role("ROLE_USER")
                        .Image(providerUser.profileImage())
                        .provider(providerUser.provider())
                        .build();

                //Oauth테이블 등록
                repository.save(oauth2Entity);

                // 공용 테이블(CommonEntity)에도 등록
                userConverter.Oauth2UserRegister(providerUser,oauth2Entity);
            }
        }


    }



}
