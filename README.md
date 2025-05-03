




# 🎬 StreamCraft - 영상 스트리밍 플랫폼

**Spring Boot + AWS S3 + WebSocket 기반 영상 스트리밍/커뮤니티 플랫폼**

> 사용자는 애니메이션, 영화 영상을 볼 수 있으며,
> 관리자는 영상을 올릴 수 있으며 등록된 관리자만 영상을 올릴 수 있습니다.
> 또한 대용량 업로드 같은 경우도 지원합니다. 예(2시간이 넘는 영화)등
> OAuth2 및 JWT 기반 보안 시스템으로 사용자 인증을 처리합니다.

---

## 🧱 프로젝트 아키텍처

![image](https://github.com/user-attachments/assets/e6823bfe-bde4-4e7a-8ee5-e4a37a7a20dd)

---

## 📦 주요 기술 스택

| 분야         | 기술                                                                 |
|--------------|----------------------------------------------------------------------|
| Backend      | Spring Boot, Spring Security, Spring Web, Spring Data JPA            |
| 인증         | OAuth2, JWT, Security Filter Chain                                   |
| 인프라       | AWS S3 (Presigned URL, Multipart Upload), WebSocket                  |
| 데이터베이스 | MySQL                                                                 |
| 빌드         | Gradle                                                                |
| 문서화       | Swagger/OpenAPI                                                       |
| 개발 도구    | IntelliJ IDEA, GitHub, Postman                                        |

---

## 📁 패키지 구조 요약

### 🔧 config

- `core`: 공통 Bean 등록 (ModelMapper, Converter, Manager 등)
- `cors`: CORS 정책 설정 (localhost:5173 허용)
- `s3`: AWS S3 Client 및 Presigner 설정
- `security`: JWT 인증, OAuth2 로그인 설정, 보안 필터 구성
- `websocket`: WebSocket 설정 및 핸들러 등록

### 🧩 controller

- `auth`: 회원가입 처리 (Form + 이미지 업로드)
- `file`: 영상 업로드, 메타데이터 처리 (S3와 연동)
- `check`: 메인 타이틀 중복 검사 API
- `post`: 게시글 CRUD, 댓글 처리
- `advice`: 전역 예외 처리 핸들러

### 🧬 service

- `userRegister`: 회원가입 처리, 프로필 이미지 업로드
- `userdetails`: Spring Security UserDetailsService 구현
- `user`: JWT 기반 사용자 정보 조회
- `post`: 게시글/댓글 작성/조회/삭제 처리
- `s3`: S3 업로드/메타데이터 저장/진행률 전달

### 🧰 converter

- `UserConverter`: Form/OAuth2 회원가입 로직 처리
- `ProviderOauth2Converter`: 소셜 로그인 사용자 정보 변환

### 📡 filter

- `JwtCheckFilter`: JWT 유효성 검사
- `UserCheckFilter`: 일반 로그인 인증 처리
- `Oauth2LoginSuccessHandler`: OAuth2 로그인 후 토큰 발급

### 💬 handler

- `ErrorHandler`: 이메일/닉네임/아이디 중복 예외 정의
- `WebSocketHandler`: 업로드 진행률 WebSocket 핸들러

### 📄 dto

- `user`, `media`, `community`, `s3part`: 응답 및 요청 DTO 전담
- DTO는 모두 POJO 또는 record 기반, ModelMapper 변환 지원

### 🗃️ repository

- `user`: 일반 유저 / OAuth2 유저 / 공통 유저 통합 관리
- `post`: 게시글/댓글 저장소
- `video.upload`: 영상 메타데이터 및 장르/제목 검색
- `episode`: 애니메이션/영화 에피소드 조회

---

## 🚀 기능 요약

| 기능명              | 설명                                                                 |
|---------------------|----------------------------------------------------------------------|
| 회원가입            | 이미지 업로드 + 사용자 정보 저장, 중복 검사 포함                     |
| JWT 로그인          | 일반 로그인 및 OAuth2 로그인 후 토큰 발급 및 인증                     |
| 영상 업로드         | Presigned URL 기반 멀티파트 업로드 + 메타데이터 등록                 |
| 실시간 업로드 진행률 | WebSocket 기반으로 클라이언트에게 업로드 상태 실시간 전달              |
| 커뮤니티 기능        | 게시글 작성/조회/삭제 + 댓글 작성/조회/삭제                           |
| 영상 검색           | 제목/장르 기반 영상 검색 API 제공                                     |

---

## 💡 담당 역할 및 경험 기술 강조

- **Spring Security 기반 OAuth2/JWT 인증 처리 전반 구현**
  - 커스텀 필터 설계 및 사용자 인증 흐름 제어
  - JWT 토큰 생성/검증/리프레시 처리

- **AWS S3 연동 영상 업로드 시스템 구축**
  - Presigned URL 생성 및 대용량 파일 멀티파트 업로드
  - WebSocket으로 실시간 업로드 진행률 전달

- **도메인 기반 설계 및 계층적 패키지 구조 구현**
  - Controller → Service → Converter → Repository 계층 분리
  - DTO/Entity 분리 및 ModelMapper 자동 변환 적용

- **ExceptionHandler 기반의 예외 관리 통합**
  - 사용자 친화적인 예외 메시지 제공 및 로깅

- **RESTful API 설계 및 Swagger 기반 문서화 경험**

---

## 🧪 실행 방법

```bash
# 1. 프로젝트 클론
git clone https://github.com/your-id/StreamCraft.git

# 2. application.yml 설정 (AWS 키, DB 연결 등)

# 3. 실행
./gradlew bootRun


✅ JPA 기반 Repository 분리 설계 및 커스텀 쿼리 적용

✅ RESTful API 설계 및 Swagger 문서화 경험

✅ 계층 구조에 맞는 역할 분리로 유지보수성과 확장성 고려한 설계
