

# 📦 Service Layer 개요 - StreamCraft

`com.example.StreamCraft.service` 패키지는 StreamCraft 플랫폼의 핵심 비즈니스 로직을 처리하는 서비스 계층입니다.  
각 기능별 서비스는 다음과 같이 나누어져 있습니다.

---

## ✅ service.s3 (S3Service)

**역할**
- AWS S3를 통한 동영상, 이미지, 자막 업로드 및 멀티파트 업로드 처리
- 영상 메타데이터 저장 및 회차별 에피소드 등록
- 실시간 업로드 진행률을 WebSocket으로 전달

**주요 기능**
- `upload(MultipartFile)` : 일반 파일 업로드
- `uploadLargeFile(...)` : 대용량 멀티파트 업로드
- `MainTitleUploadService(...)` : 영상 제목/썸네일 등록
- `saveVideoMetadata(...)` : 에피소드 DB 저장
- `generatePresignedUrl(...)` : 클라이언트를 위한 Presigned URL 발급

---

## ✅ service.post (CommunityPostService / CommunityCommentService)

### 📝 CommunityPostService
**역할**
- 커뮤니티 게시글 생성, 조회, 삭제 등의 전반적인 기능 처리

**주요 기능**
- `createPost(PostResponseDto)` : 게시글 생성
- `getAllPosts()` : 게시글 전체 + 댓글 수 포함 조회
- `getPostDetails(postId)` : 게시글 + 댓글 상세 조회
- `deletePost(postId)` : 게시글 삭제

---

### 💬 CommunityCommentService
**역할**
- 게시글에 대한 댓글 CRUD 처리

**주요 기능**
- `writeComment(...)` : 댓글 작성
- `getCommentsByPostId(postId)` : 댓글 리스트 조회
- `removeComment(commentId)` : 댓글 삭제

---

## ✅ service.userdetails (CustomUserDetailsService, UserDetailsInformation)

### 👤 CustomUserDetailsService
**역할**
- Spring Security 로그인 시 UserEntity를 DB에서 조회하여 UserDetails 생성

**주요 기능**
- `loadUserByUsername()` : 사용자 이름 기반 인증 처리

---

### 🔐 UserDetailsInformation
**역할**
- 일반 로그인 및 OAuth2 사용자 정보를 공통 형식으로 저장
- `UserDetails + OAuth2User` 인터페이스 동시 구현

**주요 기능**
- `getAuthorities()` : 사용자 권한 반환
- `getUsername(), getPassword()` : 인증 정보 제공

---

## ✅ service.user (UserQueryService)

**역할**
- JWT 토큰을 기반으로 로그인한 사용자의 정보를 조회하는 서비스
- Oauth2 사용자 / 일반 사용자 모두 지원

**주요 기능**
- `getOauth2UserFromToken(String token)`
- `getFormUserFromToken(String token)`

---

## ✅ service.userRegister (UserRegisterService)

**역할**
- 사용자 회원가입 처리
- 프로필 이미지와 함께 사용자 DB 등록 처리

**주요 기능**
- `join(UserRegisterDto, HttpServletRequest, MultipartFile)` : 회원가입 로직 위임

---

📌 **TIP:**
- S3, 사용자 인증, 커뮤니티 등 기능별로 패키지와 서비스 클래스를 분리하여 유지보수성과 가독성을 높였습니다.
- 각 서비스는 `@RequiredArgsConstructor` 기반 의존성 주입으로 설계되어 테스트하기 용이합니다.

