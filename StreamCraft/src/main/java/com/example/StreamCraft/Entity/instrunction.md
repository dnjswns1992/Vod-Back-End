📦 Entity 패키지 구조 설명 (도메인 모델)

StreamCraft 프로젝트의 Entity 패키지는 데이터베이스와 매핑되는 JPA 엔티티 클래스들을 포함하며, 도메인 모델 구조를 명확히 나타냅니다. 각 패키지는 기능 또는 도메인 단위로 분리되어 있어, 역할별로 명확하게 이해하고 유지보수할 수 있도록 구성되어 있습니다.

1. entity.user

폼 로그인 사용자(UserEntity) 및 OAuth2 로그인 사용자(Oauth2Entity)를 정의합니다.

UserEntity : 일반 회원 가입을 통해 등록된 사용자의 정보를 담고 있음 (username, password, email 등)

Oauth2Entity : 구글/네이버 등 소셜 로그인 사용자의 정보를 담고 있음 (email, provider, nickname 등)

두 엔티티는 MergedUserEntity 와 1:1로 연결되어 통합 관리됩니다.

2. entity.common

일반 유저와 Oauth2 유저를 통합한 엔티티를 관리합니다.

MergedUserEntity : 폼 로그인과 OAuth2 사용자를 하나의 테이블로 통합해 관리하는 엔티티입니다.

유저 구분 없이 JWT 기반 인증 처리를 위한 공통 기준이 됩니다.

유저 정보(username, provider, role 등)와 게시글(PostEntity) 관계를 가짐

3. entity.communitypost

커뮤니티 게시글(Post), 댓글(Comment)에 대한 도메인 엔티티를 정의합니다.

PostEntity : 커뮤니티 글을 표현하는 엔티티이며, 유저 정보와 1:N 관계를 가집니다.

CommentEntity : 게시글에 달린 댓글을 표현하는 엔티티로 Post와 N:1 관계

4. entity.episode.animation

애니메이션 에피소드 정보를 정의하는 도메인입니다.

AnimationEpisodeEntity : 애니메이션 회차(episode)별 상세정보 저장 (에피소드 번호, 설명, 영상 URL, 자막 등)

UploadMainTitleEntity와 N:1 관계로 연결되어 어떤 타이틀(작품)에 속한 에피소드인지 구분

5. entity.episode.movie

영화 에피소드 정보를 정의하는 도메인입니다.

MovieEpisodeEntity : 영화 회차별 데이터 관리 (ex. 시리즈 형식의 영화도 지원 가능)

UploadMainTitleEntity와 N:1 관계로 연결됨

6. entity.video

영상 메타데이터를 정의하는 메인 타이틀 엔티티를 관리합니다.

UploadMainTitleEntity :

영상(애니메이션, 영화) 등록 시 메타 정보(title, season, imageUrl 등)를 저장

애니메이션/영화 에피소드 엔티티들과 1:N 관계를 가짐

7. entity.file

테스트 또는 공통 파일 업로드 결과를 저장하는 파일 도메인입니다.

FileEntity : 업로드된 파일 URL 정보를 저장하는 단순 테스트용 엔티티

✅ 리팩토링 제안 요약

기존 패키지명

제안 패키지명

이유

entity.commom

entity.auth 또는 entity.user.unified

통합 사용자 관리이므로 auth 또는 unified로 표현

entity.Video

entity.media 또는 entity.video.main

메인 타이틀 및 영상 메타데이터에 초점 맞춤

entity.Test

entity.file 또는 entity.utility.file

테스트보다는 목적 중심으로 이름 변경 권장