package com.example.StreamCraft.oauth2.Oauth2Service;

import com.example.StreamCraft.converter.UserConverter;
import com.example.StreamCraft.dto.user.UserRegisterDto;
import com.example.StreamCraft.oauth2.Model.ProviderOauth2Converter;
import com.example.StreamCraft.oauth2.Model.ProviderUserRequest;
import com.example.StreamCraft.oauth2.ProviderUser.ProviderUser;
import com.example.StreamCraft.Repository.user.CommonRepository;
import com.example.StreamCraft.Repository.user.Oauth2UserRepository;
import com.example.StreamCraft.UserAccount.TransferModelMapper;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

/**
 * ✅ OpenID Connect(OIDC) 기반 소셜 로그인 사용자 정보를 처리하는 서비스
 * - OIDC는 OAuth2 위에 사용자 ID 토큰을 추가 제공하는 프로토콜
 * - 주로 Google 로그인 시 사용됨 (id_token 사용)
 */
@Service
public class Oauth2OidcUserService extends AbstractOauth2Service implements OAuth2UserService<OidcUserRequest, OidcUser> {


    /**
     * 생성자 - AbstractOauth2Service 에 필요한 컴포넌트 주입
     */
    public Oauth2OidcUserService(Oauth2UserRepository repository, CommonRepository commonRepository, UserConverter userConverter, ProviderOauth2Converter<ProviderUserRequest, ProviderUser> providerOauth2Converter, TransferModelMapper<ProviderUserRequest, UserRegisterDto> transferModelMapper) {
        super(repository, commonRepository, userConverter, providerOauth2Converter, transferModelMapper);
    }

    /**
     * ✅ OIDC 사용자 정보 로딩 처리 메서드
     * - OIDC 요청 수신 시 Spring의 기본 OidcUserService로 사용자 정보 가져옴
     * - 이후 ProviderUser로 변환 → DB 저장 등 추가 처리 가능
     *
     * @param userRequest OIDC 로그인 요청 정보
     * @return 아직 미구현 (추후 UserDetailsInformation 등의 반환 필요)
     */
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        // 현재 로그인 중인 클라이언트(구글 등) 등록 정보
        ClientRegistration registration = userRequest.getClientRegistration();
        // Spring 기본 OIDC 유저 서비스 사용 → OidcUser 반환

        OAuth2UserService<OidcUserRequest,OidcUser> oAuth2UserService = new OidcUserService();
        OidcUser oidcUser = oAuth2UserService.loadUser(userRequest);


        // 현재는 구현되지 않았음 (return null)
        return null;
    }
}
