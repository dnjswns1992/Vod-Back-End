📦 dto 패키지 설명서

이 디렉토리는 StreamCraft 프로젝트에서 사용되는 모든 데이터 전송 객체(Data Transfer Object, DTO) 를 모아둔 공간입니다.
클라이언트 ↔ 서버 간 데이터 구조 정의, API 응답 포맷 지정, 비즈니스 로직 전달용으로 활용됩니다.

패키지는 기능별로 세분화되어 있어 유지보수 및 역할 분리가 명확합니다.

✅ 서브 패키지 구조

📁 commuity

커뮤니티 게시판, 댓글 시스템 등과 관련된 DTO 모음

CommentResponseDto: 댓글 단건 응답용 DTO

PostResponseDto: 게시글 조회 응답용 DTO

PostWithDto: 게시글 + 댓글 목록 응답용 DTO (record 기반)

📁 media

영상 콘텐츠 (애니메이션, 영화, 메인 타이틀, 에피소드 등) 관련 DTO

🔸 media.animation

AnimationEpisodeResponseDto: 애니메이션 에피소드와 메인 타이틀 응답

🔸 media.movie

MovieEpisodeResponseDto: 영화 에피소드와 메인 타이틀 응답

🔸 media.upload

UploadMainTitleRequestDto: 영상 메인 타이틀 등록 요청용 DTO

VideoMetadataRequestDto: 영상 개별 메타데이터 등록 요청용 DTO

📁 user

사용자 관련 DTO - 회원가입, 인증 정보 등

UserRegisterDto: 사용자 가입 요청 시 사용하는 DTO

UserInfoResponseDto: JWT 필터 등에서 사용자 인증 결과 반환 시 사용

CommonUserDto: 공통 유저 정보 (닉네임, 생성일, 이메일 등) 응답용 DTO

📁 s3part

AWS S3 멀티파트 업로드 관련 DTO

UploadPartDto: 각 파트의 ETag 및 PartNumber 정보를 담은 DTO

✨ DTO 설계 컨벤션

모든 DTO는 단순 POJO 또는 record 형태로 구성되어 있으며, 필수 어노테이션(@Data, @Getter, @Setter, @Builder)을 통해 보일러플레이트 코드를 제거함

도메인 엔티티는 직접 반환하지 않고, 별도 DTO로 응답 구조를 정의하여 보안성과 확장성을 고려함

클라이언트와의 통신에서 응답 데이터 포맷의 일관성을 보장함

💡 활용 예시

클래스

사용 위치

UserRegisterDto

회원가입 Controller → Service → Entity 변환

UploadMainTitleRequestDto

영상 메인 타이틀 등록 API에서 사용

CommentResponseDto

댓글 작성/조회 시 응답 포맷 지정

UploadPartDto

프론트엔드에서 각 파트별 업로드 결과 전달 시