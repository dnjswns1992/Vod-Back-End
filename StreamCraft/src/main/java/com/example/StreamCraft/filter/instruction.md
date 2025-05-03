📦 StreamCraft filter 패키지 설명서

com.example.StreamCraft.filter 패키지는 Spring Security 기반의 인증 및 권한 처리 로직에서 핵심적인 역할을 수행하는 JWT 필터, 로그인 처리 핸들러, OAuth2 후처리 등을 포함하고 있습니다.

이 패키지의 클래스들은 요청(Request) 필터 체인에서 작동하며, JWT 유효성 검증, 인증 성공 시 토큰 발급, 권한 설정 등의 로직을 담당합니다.

🧩 구성 클래스 및 역할

1. JwtCheckFilter

기능: /authenticated/** 또는 /admin/** 경로로 접근하는 요청에 대해 JWT 유효성 검증을 수행.

동작 순서:

Authorization 헤더에서 Bearer 토큰 추출

JWT 파싱 및 provider 확인 (FormLogin, OAuth2)

DB에서 사용자 조회 (공통 테이블 or Oauth2 테이블)

UserDetails 객체 생성 및 Spring Security 인증 컨텍스트에 등록

예외 처리: 토큰이 없거나 유효하지 않을 경우 401 / 400 상태 코드 반환

2. Oauth2LoginSuccessHandler

기능: OAuth2 로그인 성공 시 후처리를 담당하는 클래스

주요 역할:

사용자 IP 업데이트 (Oauth2Entity)

JWT 토큰 생성 및 응답 헤더에 Authorization 추가

클라이언트 페이지(localhost:5173/callback)로 토큰 포함 리다이렉트

3. UserCheckFilter

기능: 일반 로그인 (Form 기반) 요청을 처리하는 Spring Security의 기본 UsernamePasswordAuthenticationFilter 커스터마이징 버전

동작 흐름:

사용자 JSON 요청 데이터를 UserEntity로 역직렬화

AuthenticationManager를 통해 인증 처리 위임

성공 시: JWT 생성 → 응답 헤더/Body에 토큰 전달

실패 시: 401 Unauthorized 응답 반환

🛠 기술 스택 및 의존성

Spring Security Filter Chain

JWT (io.jsonwebtoken)

UsernamePasswordAuthenticationFilter 확장

OncePerRequestFilter 기반 커스텀 필터

AuthenticationManager 통한 인증 처리

🔐 이 패키지의 책임과 위치

이 filter 패키지는 전체 보안 구조의 전처리 및 후처리 역할을 담당하며, 다음과 같은 책임을 집니다:

JWT 토큰의 유효성 및 정합성 검사

로그인 성공 후 사용자 인증 정보 저장

사용자 인증 성공 시 클라이언트에 토큰 반환

✅ 활용 예시

로그인 API 호출 시 UserCheckFilter 가 JWT 토큰 발급

이후 /authenticated/getUserInfo 접근 시 JwtCheckFilter 가 토큰을 파싱하고 인증 처리

OAuth2 로그인 시에는 Oauth2LoginSuccessHandler 가 토큰을 만들어 클라이언트로 전달

📁 위치 구조

com.example.StreamCraft
└── filter
├── JwtCheckFilter.java
├── UserCheckFilter.java
└── Oauth2LoginSuccessHandler.java

이 filter 패키지는 보안 인증의 핵심 관문 역할을 하며, 프로젝트의 로그인과 API 요청 흐름에 매우 밀접하게 연결되어 있습니다.