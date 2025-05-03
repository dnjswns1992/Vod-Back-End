

**🌐 com.example.StreamCraft.oauth2.ProviderUser**

OAuth2 소셜 로그인(Google, Naver) 사용자 정보를 공통 구조로 감싸기 위한 계층입니다.

소셜 로그인 성공 후 공급자별로 구조가 다른 사용자 정보를 ProviderUser 
인터페이스 기반 객체로 통일시켜 Spring Security 및 프로젝트 전반에서 일관되게 활용합니다.


📁 주요 클래스 설명
🔹 ProviderUser (interface)
각 소셜 로그인 사용자의 정보를 표준화하여 제공하기 위한 공통 인터페이스

**메서드명	설명**
getId()	공급자에서 부여한 고유 사용자 ID
nickName()	사용자 닉네임
email()	이메일 주소
profileImage()	프로필 이미지 URL
provider()	로그인한 공급자 (google, naver)
getAuthority()	Spring Security용 권한 목록
getAttribute()	공급자 원본 Attribute 맵
getNickName()	중복된 닉네임 getter (※ 리팩토링 고려 가능)

🔸 AbstractProvider
ProviderUser 구현의 기반이 되는 추상 클래스

공통 필드: ClientRegistration, OAuth2User, Map<String,Object> attributes

공통 구현:

이메일, 프로필 이미지, 공급자, 권한 추출 로직

기본 닉네임은 "익명의 유저"로 설정

🔸 GoogleUser
Google 로그인 사용자를 위한 ProviderUser 구현체

sub → ID

name → 닉네임

picture → 프로필 이미지


new GoogleUser(registration, oAuth2User, converterAttribute);
🔸 NaverUser
Naver 로그인 사용자를 위한 ProviderUser 구현체

id → ID

nickname → 닉네임

profile_image → 프로필 이미지


new NaverUser(registration, oAuth2User, converterAttribute);
🔸 FormLoginOauthCombine
다양한 사용자 엔티티를 UserInfoResponseDto 로 통일시켜주는 변환 유틸리티

Oauth2Combine(Oauth2Entity)

FormLoginCombine(UserEntity)

CommonCombine(CommonEntity)

각 로그인 방식의 사용자 엔티티를 공통 DTO로 변환해 JWT 발급 및 시큐리티 인증 컨텍스트에 활용됩니다.

💡 설계 목적
소셜 로그인 사용자 정보를 공급자별로 분리하되,

내부 로직에서는 공통된 방식(ProviderUser) 으로 사용

추후 Kakao, GitHub 등 새로운 공급자 확장 용이

📌 확장 예시

public class KakaoUser extends AbstractProvider {
// getId(), getNickName() 등을 Kakao 구조에 맞게 override
}
✅ 이 구조를 기반으로, OAuth2UserService → ProviderUser → UserInfoResponseDto 변환까지 자연스럽게 연결됩니다.

필요 시 이 템플릿을 기반으로 다른 OAuth 공급자도 쉽게 확장할 수 있습니다.