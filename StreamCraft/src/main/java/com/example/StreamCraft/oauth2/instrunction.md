🌐 StreamCraft OAuth2 패키지 설명서
소셜 로그인 (Google / Naver) 통합 인증 및 사용자 정보 변환 처리 구조

📦 패키지 구조 개요
cpp
Copy
Edit
com.example.StreamCraft.oauth2
├── Model               // 공급자 구분 및 사용자 정보 변환 처리
├── Oauth2Service       // 로그인 후 사용자 정보 처리 서비스
├── ProviderUser        // 공급자별 사용자 정보 클래스
🔁 전체 동작 흐름
소셜 로그인 성공

Spring Security가 OAuth2UserService를 통해 사용자 정보 조회

공급자(Google/Naver) 정보 확인 → 해당 Converter 호출

사용자 정보를 ProviderUser 로 변환

DB에 사용자 저장 (최초 로그인 시)

UserDetails 형식으로 Spring Security에 인증 완료

📂 Model 패키지
공급자(Google/Naver)에 따라 다른 구조의 사용자 정보를 통일된 ProviderUser 형태로 변환

클래스명	역할
ProviderUserRequest	OAuth2User + ClientRegistration DTO
ProviderOauth2Converter<T, R>	공급자별 사용자 변환 인터페이스
GoogleConverter	Google 사용자 정보를 ProviderUser로 변환
NaverConverter	Naver 사용자 정보를 ProviderUser로 변환
AbstractDelegatingOauth2Converter	어떤 Converter를 사용할지 판단 후 위임
ConverterAttribute	공급자별 사용자 속성을 Map으로 정리

✅ 최종적으로 ProviderUser 객체를 생성 → 인증 및 저장 로직에 사용

📂 ProviderUser 패키지
Google / Naver 사용자의 정보를 공통 인터페이스로 감싸는 계층

클래스/인터페이스	설명
ProviderUser	사용자 정보 공통 인터페이스
AbstractProvider	ProviderUser의 기본 구현체 (공통 속성 처리)
GoogleUser	Google 사용자용 구현 클래스
NaverUser	Naver 사용자용 구현 클래스
FormLoginOauthCombine	일반 로그인/Form 로그인 → DTO 변환 유틸리티

🔄 GoogleUser, NaverUser 모두 AbstractProvider를 상속받아 공통 필드 및 메서드 재사용

📂 Oauth2Service 패키지
Spring Security가 로그인 후 사용자 정보를 조회하고, DB 저장 및 인증처리를 담당하는 서비스 계층

클래스명	설명
AbstractOauth2Service	공통 처리 로직 추상화 (변환 + 저장)
Oauth2UserService	일반 OAuth2 로그인용 서비스 (Google/Naver)
Oauth2OidcUserService	OpenID Connect (OIDC) 기반 로그인 처리 (Google id_token)

✅ 인증 흐름 요약
loadUser() 호출

OAuth2User + ClientRegistration → ProviderUserRequest 생성

공급자 확인 → ProviderUser 변환

최초 로그인 시 Oauth2Entity + CommonEntity 저장

최종적으로 UserDetails 구현체 반환

💡 확장성
Kakao, GitHub 등 새로운 소셜 로그인을 추가하려면 다음만 구현하면 됨:

java
Copy
Edit
public class KakaoUser extends AbstractProvider {
@Override
public String getId() {
return getAttribute().get("id").toString();
}
@Override
public String getNickName() {
return getAttribute().get("nickname").toString();
}
}
KakaoConverter를 구현하고 AbstractDelegatingOauth2Converter에 등록하면 자동 처리됩니다.

✅ 핵심 정리
목적	구성
공급자별 사용자 속성 파싱	GoogleConverter, NaverConverter
통일된 사용자 정보 객체	ProviderUser (GoogleUser, NaverUser 등)
로그인 후 DB 저장	AbstractOauth2Service.register()
인증 객체 반환	UserDetailsInformation

✅ 위 구조를 통해, 공급자별 인증 로직의 분리, 통일된 사용자 객체 제공, 재사용 가능한 서비스 아키텍처가 구성됩니다.

이 구조는 Spring Security의 인증 체계와 잘 맞물리며, 실서비스에서도 쉽게 확장할 수 있도록 설계되었습니다.

필요 시 이 템플릿을 복사하여 README.md 또는 Notion 문서로 사용 가능합니다.
추가적으로 전체 흐름을 다이어그램으로 표현하면 더욱 명확해집니다. 필요하시면 구성해드릴게요.