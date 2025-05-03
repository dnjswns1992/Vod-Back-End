# 📦 repository 패키지 구조 안내

StreamCraft 프로젝트의 데이터베이스 접근 계층입니다.  
`JpaRepository` 기반으로 도메인별 DB CRUD 및 검색 기능을 담당합니다.

---

## 🗂️ 1. episode - 에피소드(시리즈 영상) 저장소

애니메이션 및 영화의 에피소드 정보를 S3와 연동된 UploadMainTitle 기반으로 조회합니다.

repository.episode
├── animation
│ └── AnimationEpisodeRepository
│ - **애니메이션 에피소드 목록 조회**
│ - **videoType 필터링 지원**
│
└── movie
└── MovieEpisodeRepository
- 영화 에피소드 목록 조회 (JPQL 사용)

yaml
Copy
Edit

---

## 💬 2. post - 게시글 / 댓글 저장소

커뮤니티(게시판) 기능 관련 저장소입니다.

repository.post
├── **PostRepository
│ - 게시글 등록/조회/삭제
│ - 조회수 증가 쿼리 포함**
│
└── CommentRepository
- 댓글 등록/조회/삭제
- 특정 게시글(PostEntity) 기반 댓글 가져오기

yaml
Copy
Edit

---

## 👤 3. user - 사용자 통합 관리 저장소

OAuth2, 일반 로그인 유저를 구분하거나 통합하여 관리합니다.

repository.user
├── **UserRepository**
│ - 일반 로그인 사용자
│ - 이메일, 닉네임 중복 검사
│
├── **Oauth2UserRepository**
│ - 소셜 로그인 사용자 저장
│ - 이메일 기준 조회
│
└── **CommonRepository**
- JWT 기반 통합 사용자 관리
- username 기준으로 인증

yaml
Copy
Edit

---

## 🎬 4. video.upload - 업로드된 영상 메타데이터 저장소

S3에 등록된 영상의 제목, 장르 등 메타데이터를 조회합니다.

repository.video.upload
└── VideoUploadRepository
- 제목 포함 검색 (findByTitleContaining)
- 제목 + 장르 검색 (findByTitleContainingAndGenre)
- 장르 기반 검색 (findByGenre)
- 업로드된 영상을 S3에 저장후 url을 받아 DB에 aws s3의 url을 저장합니다.

yaml
Copy
Edit

---

## ✅ 설계 개요

- 각 도메인(영상, 커뮤니티, 사용자)에 맞는 리포지토리로 분리
- 향후 확장성 고려한 **기능 중심 패키지 구조**
- JPA 기본 기능 + 커스텀 쿼리(JPQL) 혼합 사용
- OAuth2 / Form 사용자 통합 설계 (`CommonRepository` 기반)

---

### 📌 패키지 구조 확장 예시
- `repository.video.stream` → 실시간 스트리밍 메타데이터 저장
- `repository.user.block` → 유저 차단, 신고 기능 추가 시 분리 가능
- `repository.stats` → 접속 통계, 조회수 기록 등 추후 통계 기능 관리

---

이 문서는 인텔리제이 내에서 `repository` 패키지에 `README.md`로 위치시키면  
**클래스 코드 없이도 전체 저장소 구조를 한눈에 파악**할 수 있습니다.