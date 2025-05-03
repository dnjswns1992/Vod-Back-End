✅ OAuth2 Model 패키지 구조 요약
소셜 로그인(Google, Naver 등)의 사용자 정보를 공통 ProviderUser 객체로 변환하는 로직을 담당.

📁 주요 클래스 및 역할
클래스명	역할 요약
ProviderUserRequest	OAuth2User + ClientRegistration을 묶은 DTO

ProviderOauth2Converter<T, R>	공급자 변환기 인터페이스 (구현체: Google/Naver)

GoogleConverter	Google 로그인 사용자 정보를 ProviderUser로 변환

NaverConverter	Naver 로그인 사용자 정보를 ProviderUser로 변환

AbstractDelegatingOauth2Converter	어떤 Converter를 쓸지 판단 후 위임

ConverterAttribute	공급자별 사용자 정보를 Map으로 정리

ProviderUser	통합된 사용자 정보 객체 (GoogleUser, NaverUser 등 상속)

🔄 동작 흐름

로그인 성공 후 OAuth2User, ClientRegistration → ProviderUserRequest로 감쌈

AbstractDelegatingOauth2Converter가 공급자 확인 후

→ GoogleConverter 또는 NaverConverter에 변환 요청

최종적으로 ProviderUser 객체 반환

→ Spring Security에서 인증 처리에 사용됨

