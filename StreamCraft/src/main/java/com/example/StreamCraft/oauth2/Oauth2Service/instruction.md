StreamCraft - OAuth2 Login Service 플랫폼

특징

**StreamCraft의 OAuth2 로그인 구조는 Google, Naver 같은 소셜 공급자의 OAuth2 검색 / 인증 결과를 받아 
Spring Security의 사용자 가져와 현재 사용자의 정보를 DB에 저장하고, 사용자 정보를 획득해 이해할 수 있도록 구성되어 있습니다.**

---------단계적 노트의 구성--------



1. Oauth2UserService

Spring Security가 로그인 후 사용자 정보를 찾을 때 호출하는 버튼

OAuth2UserRequest 로부터 공급자 정보 + 사용자 OAuth2 정보 수집

ProviderUserRequest로 결합 -> ProviderUser 결과 받기

복급 아니면 해당 사용자 DB에 저장

역할을 담당하는 것은 AbstractOauth2Service



2. Oauth2OidcUserService

OpenID Connect(OIDC)를 가지고 있는 공급자가 로그인할 경우 적용

Google의 id_token을 통한 인증을 처리

현재는 loadUser 문맥은 Spring의 기본 정보 받기까지만 그 후 처리는 무관 (return null)

3. AbstractOauth2Service

OAuth2 현재 관리자

ProviderUserRequest 결합을 통해 ProviderUser로 변환

처음 로그인 경우 register() 메서드를 통해 Oauth2Entity + CommonEntity로 DB에 저장

Google / Naver 구분 처리 변동 불가능

도움이 되는 역할 정보

클래스 명

**설명**

Oauth2UserService

OAuth2(예: Google, Naver) 로그인 후 사용자 정보 찾기

Oauth2OidcUserService

OpenID Connect 기반(Google id_token)을 처리하는 서비스

AbstractOauth2Service

공통 로지크 관리 (변환 및 저장)



**노트**

Oauth2UserService 또는 Oauth2OidcUserService 에서 loadUser() 메서드는



Spring의 기본 OAuth2User 획득



ProviderUserRequest로 변환



구성에 따른 사용자 정보 ProviderUser 변환



처음 로그인이면 DB에 저장



Spring Security의 OAuth2User 가능한 DTO로 반환