![image](https://github.com/user-attachments/assets/e6823bfe-bde4-4e7a-8ee5-e4a37a7a20dd)




📺 StreamCraft - Fullstack Video Platform Backend

StreamCraft는 동영상 업로드 및 스트리밍 기능을 중심으로, 커뮤니티 게시판, JWT 기반 인증, 소셜 로그인(OAuth2), WebSocket 실시간 피드백 기능을 통합한 Spring Boot 기반 백엔드 프로젝트입니다. AWS S3를 활용한 대용량 파일 업로드, RESTful API 구조 설계, 보안 인증 계층 등 실제 서비스와 유사한 구조를 갖추고 있어 포트폴리오 및 실무 능력 검증에 적합합니다.

🧱 기술 스택

Language: Java 17

Framework: Spring Boot 3.x, Spring Security

Authentication: JWT, OAuth2 (Google, Naver)

Database: MySQL (JPA, Hibernate)

Cloud Service: AWS S3 (Multipart Upload, Presigned URL)

WebSocket: SockJS, STOMP for real-time upload progress

Build Tool: Gradle

Documentation: Swagger (OpenAPI)

CI/CD & Infra: Local 개발 기준 (향후 확장 가능)

🧠 핵심 기능 및 구성

✅ 1. 사용자 인증 및 회원가입

JWT 기반 인증 처리 (Access / Refresh Token 구조)

OAuth2 소셜 로그인 (Google, Naver) → 공통 사용자 구조로 통합 관리

사용자 가입 시 프로필 이미지 AWS S3 업로드 지원

필드 중복 검사 (아이디, 이메일, 닉네임)

✅ 2. 영상 업로드 및 메타데이터 관리

AWS S3 Presigned URL을 이용한 대용량 멀티파트 업로드 처리

영상 메인 타이틀 / 에피소드 별 업로드 처리 (영화 / 애니메이션)

업로드된 영상 메타데이터 DB 저장 및 검색 기능

✅ 3. 커뮤니티 게시판 (Post & Comment)

게시글 등록, 조회, 삭제 기능 (조회수 포함)

댓글 작성, 조회, 삭제 기능 → 인증 기반 사용자 식별 처리

사용자 인증 기반 게시글 소유권 확인 및 삭제 제어

✅ 4. 실시간 WebSocket 기능

영상 업로드 시 진행률을 WebSocket 기반 실시간 피드백으로 전송

클라이언트가 연결 시 sessionId로 식별하여 메시지 송신

모든 Origin 허용 설정으로 다양한 프론트엔드 통합 가능

🗂️ 프로젝트 구조 요약

📁 controller

REST API 진입점 (@RestController)

회원가입, 영상 업로드, 커뮤니티, 메인 타이틀 중복 체크 등 라우팅 담당

📁 service

핵심 비즈니스 로직 구현 계층

S3 업로드 처리, 사용자 인증, 커뮤니티 게시글/댓글 관리 포함

📁 repository

JPA 기반 CRUD 처리 계층

사용자, 게시글, 댓글, 영상 메타데이터 전용 리포지토리 분리 구성

📁 config

보안(Spring Security), CORS, WebSocket, S3 등 전역 설정 클래스 모음

📁 filter

JWT 인증 필터 / Form 로그인 / OAuth2 후처리 필터 포함

인증 흐름 제어 및 예외 응답 구성

📁 dto

클라이언트 ↔ 서버 간 데이터 전달용 구조화 객체

사용자, 커뮤니티, 영상 업로드, S3 멀티파트 등 세분화 설계

📁 converter

OAuth2 공급자(Google, Naver)별 사용자 정보를 공통 구조로 변환

사용자 가입 시 DTO → Entity 변환 및 프로필 업로드 처리 담당

📁 handler

예외 핸들러 (@ControllerAdvice), WebSocket 핸들러 클래스 분리

🌐 주요 API 예시

기능

Method

Endpoint

회원가입

POST

/register

메인 타이틀 중복 검사

GET

/api/MainTitle/check

영상 업로드

POST

/api/file/upload

커뮤니티 게시글 등록

POST

/user/createPost

댓글 작성

POST

/user/comment/write/{postId}

🖼️ 시스템 구성 다이어그램



💬 학습 포인트 & 기술 경험 요약

✅ Spring Security 기반 사용자 인증 흐름 전체 구현 경험 (JWT + OAuth2)

✅ AWS S3 연동 및 Presigned URL, 멀티파트 업로드 처리 실습

✅ WebSocket 기반 실시간 서버-클라이언트 통신 구조 이해

✅ JPA 기반 Repository 분리 설계 및 커스텀 쿼리 적용

✅ RESTful API 설계 및 Swagger 문서화 경험

✅ 계층 구조에 맞는 역할 분리로 유지보수성과 확장성 고려한 설계
