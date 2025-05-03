package com.example.StreamCraft.oauth2.Oauth2Service;

import com.example.StreamCraft.dto.user.UserRegisterDto;
import com.example.StreamCraft.dto.user.UserInfoResponseDto;
import com.example.StreamCraft.oauth2.Model.ProviderOauth2Converter;
import com.example.StreamCraft.oauth2.Model.ProviderUserRequest;
import com.example.StreamCraft.oauth2.ProviderUser.ProviderUser;
import com.example.StreamCraft.Repository.user.CommonRepository;
import com.example.StreamCraft.Repository.user.Oauth2UserRepository;
import com.example.StreamCraft.service.userdetails.UserDetailsInformation;
import com.example.StreamCraft.UserAccount.TransferModelMapper;
import com.example.StreamCraft.converter.UserConverter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * ✅ OAuth2 인증 성공 후 사용자 정보를 처리하는 커스텀 서비스 클래스
 * - 소셜 로그인 성공 시 사용자 정보 조회 → ProviderUser 변환 → DB 등록 및 반환
 *
 * ✅ Spring Security가 이 클래스를 통해 OAuth2User를 최종 반환받아 시큐리티 컨텍스트에 저장
 */
@Service
public class Oauth2UserService extends AbstractOauth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    /**
     * 생성자 주입 (부모 추상 클래스에 필요한 의존성 전달)
     */
    public Oauth2UserService(Oauth2UserRepository repository, CommonRepository commonRepository, UserConverter userConverter, ProviderOauth2Converter<ProviderUserRequest, ProviderUser> providerOauth2Converter, TransferModelMapper<ProviderUserRequest, UserRegisterDto> transferModelMapper) {
        super(repository, commonRepository, userConverter, providerOauth2Converter, transferModelMapper);
    }

    /**
     * ✅ OAuth2 로그인 성공 후 사용자 정보 처리
     * 1. 공급자(ClientRegistration) 및 사용자 정보(OAuth2User)를 받아옴
     * 2. ProviderUserRequest → ProviderUser로 변환
     * 3. 사용자 정보 등록 (최초 로그인 시)
     * 4. Spring Security에서 사용할 수 있도록 UserDetailsInformation 반환
     *
     * @param userRequest OAuth2 로그인 요청 정보
     * @return OAuth2User 객체 (UserDetails 구현체로 반환)
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 로그인한 소셜 공급자 정보 가져오기 (ex: google, naver)
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        // OAuth2 사용자 정보 로딩 (Spring 기본 서비스 사용)
        OAuth2UserService<OAuth2UserRequest,OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);


        // 사용자 정보와 공급자 정보를 합쳐서 커스텀 변환 객체 생성
        ProviderUserRequest providerUserRequest = new ProviderUserRequest(clientRegistration, oAuth2User);

        // 공급자에 따라 사용자 정보를 ProviderUser 객체로 변환
        ProviderUser converter = super.Converter(providerUserRequest);

       // 사용자 DB에 저장 (처음 로그인인 경우에만 등록)
       super.register(converter);


        // Security에 넘겨줄 사용자 정보를 DTO로 변환
        UserInfoResponseDto information = new UserInfoResponseDto(converter);

            UserDetailsInformation userDetailsInformation = new UserDetailsInformation(information);
            return userDetailsInformation;

    }
}
