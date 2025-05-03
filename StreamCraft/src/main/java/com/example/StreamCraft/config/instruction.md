📦 config 패키지 구조 설명

이 디렉토리는 SpringCraft 프로젝트의 전반적인 설정(configuration)을 담당합니다. 보안, CORS, S3, WebSocket 등의 설정 클래스들이 계층적으로 정리되어 있습니다. 각 서브 패키지 및 클래스는 다음과 같은 책임을 가집니다.

📁 core

전반적인 Bean 등록과 의존성 주입 설정을 담당하는 기본 설정 클래스입니다.

SpringConfig.java

ProviderOauth2Converter, TransferModelMapper, UserConverter, PostManager, CommentManager 등을 빈으로 등록합니다.

의존성 주입이 필요한 다양한 유틸성 클래스/매니저 객체들을 중앙에서 관리합니다.

📁 cors

프론트엔드와의 연동을 위한 CORS 설정을 담당합니다.

CorsConfig.java

특정 도메인(예: localhost:5173)에서 들어오는 요청을 허용합니다.

모든 메서드/헤더를 허용하고, 인증 쿠키도 포함 가능하게 설정합니다.

📁 s3

AWS S3와의 통신 및 동영상 업로드를 위한 설정을 담당합니다.

S3Config.java

S3Client 및 S3Presigner 객체를 빈으로 등록하여 AWS SDK를 통해 업로드/서명 기능을 제공합니다.

환경변수에서 AWS 자격 증명을 로딩합니다.

📁 security

Spring Security와 관련된 보안 필터 및 JWT 기반 인증 설정을 담당합니다.

JwtConfig.java

전체 Security Filter Chain 설정을 정의합니다.

UserCheckFilter, JwtCheckFilter, Oauth2LoginSuccessHandler 등을 필터에 등록합니다.

세션 비활성화(STATLESS), CSRF 비활성화, 역할/권한 기반 인증 처리 등을 포함합니다.

정적 자원 경로는 보안 필터 제외 설정 포함.

📁 websocket

WebSocket 기반 실시간 기능 설정을 담당합니다.

WebSocketConfig.java

/ws/video-progress 경로에 대해 WebSocket 핸들러를 등록합니다.

모든 Origin 허용 (setAllowedOrigins("*"))

업로드 진행률 등 실시간 피드백 처리에 사용됩니다.

✅ 요약

패키지

설명

core

공통 Bean 등록, 매니저/유틸 클래스 등록

cors

프론트엔드와의 CORS 정책 설정

s3

AWS S3 연결 및 인증/업로드 설정

security

JWT 기반 Spring Security 설정 전체

websocket

WebSocket 설정 및 핸들러 등록