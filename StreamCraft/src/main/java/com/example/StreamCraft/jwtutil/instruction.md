🛡️ JwtUtil 패키지 설명서
📦 패키지 위치
com.example.StreamCraft.JwtUtil

📌 목적
이 패키지는 JWT(Json Web Token) 기반 인증 및 사용자 정보를 처리하는 유틸리티 클래스들을 모아둔 영역입니다.
Spring Security와 연동되어 사용자 인증 상태 유지 및 검증을 담당합니다.

✅ 구성 클래스
클래스명	설명
JwtTokenProvider	JWT 생성, 파싱, 인증 정보 추출 기능을 제공하는 클래스입니다.
AuthenticatedUserUtil	현재 SecurityContext에서 인증된 사용자 정보를 꺼내오는 유틸 클래스입니다.

📘 클래스 상세 설명
1. JwtTokenProvider
   JWT 관련 주요 기능을 제공합니다:

java
Copy
Edit
public class JwtTokenProvider {
🔐 createJwtToken(String username, UserRepository repository)

Form 로그인 사용자용 JWT 생성

사용자 정보(이메일, 역할 등)를 Claims에 담아 서명된 토큰 생성

🔐 createOauth2JwtToken(Authentication authentication)

OAuth2 사용자 인증 후 JWT 생성

OAuth2 인증 객체에서 정보 추출

🔍 getToken(String token)

전달된 JWT에서 Claims 정보를 추출

예외 발생 시 유효하지 않은 토큰으로 처리

2. AuthenticatedUserUtil
   Spring Security의 SecurityContext에서 현재 로그인한 사용자의 정보를 추출합니다:

java
Copy
Edit
public class AuthenticatedUserUtil {
getUserAccount():

내부적으로 UserDetailsInformation 객체를 꺼내고,
그 안에 저장된 UserInfoResponseDto를 반환합니다.

로그인 성공 이후 인증 정보 접근 시 유용하게 사용됩니다.

💡 활용 예시
JWT 인증 필터에서 사용자 정보를 꺼내거나

OAuth2 로그인 이후 JWT를 발급하거나

Spring Security 인증 후 클라이언트로 JWT 전송 등에서 활용됩니다.

📁 연관 클래스 위치
UserDetailsInformation: com.example.StreamCraft.service.userdetails

UserInfoResponseDto: com.example.StreamCraft.dto.user