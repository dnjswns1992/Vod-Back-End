📦 controller 패키지 구조 설명

이 디렉토리는 SpringCraft 프로젝트의 웹 계층(Web Layer) 을 담당하는 컨트롤러들이 위치한 공간입니다.
모든 요청(Request)은 이 패키지의 클래스를 통해 진입하며, Service 계층과 연동하여 클라이언트와 통신합니다.

✅ 구조 및 책임

📁 auth

AuthController

사용자 회원가입 요청을 처리합니다.

UserRegisterDto, MultipartFile, HttpServletRequest 등을 활용하여 사용자와 프로필 이미지 저장을 처리합니다.

📁 advice

GlobalExceptionHandler

Controller 전역에서 발생할 수 있는 커스텀 예외 (이메일/닉네임/아이디 중복 등)를 하나의 클래스에서 통합 처리합니다.

@ControllerAdvice, @ExceptionHandler 기반 예외처리 패턴 적용

📁 check

MainTitleCheckController

영상 메인 타이틀 등록 시 중복 여부를 확인하는 API 제공

/api/MainTitle/check endpoint 제공

📁 file

MediaController

AWS S3를 이용한 영상 업로드 및 메타데이터 저장 처리

메인 타이틀, 애니메이션, 영화에 대한 리스트/에피소드 조회

멀티파트 업로드: 사전 서명 URL 생성, 파트별 업로드, 최종 완료 API 제공

📁 post

PostController

커뮤니티 게시판 기능 제공 (글 작성, 상세보기, 전체 조회, 삭제 등)

CommentController

게시글에 달리는 댓글 작성, 조회, 삭제 기능 제공

🧭 공통 특징

각 컨트롤러는 @RestController 또는 @Controller + @ResponseBody 구조를 따릅니다.

비즈니스 로직은 Service 계층에 위임하며, Controller는 단순 라우팅 및 응답 포맷에 집중합니다.

일부 컨트롤러는 JWT 인증 정보 (Authorization 헤더)를 사용해 요청자의 인증 상태를 확인합니다.

🌐 주요 API Endpoint 예시

기능

메서드

경로

회원가입

POST

/register

메인 타이틀 중복 검사

GET

/api/MainTitle/check

영상 업로드

POST

/api/file/upload

애니메이션/영화 목록

GET

/api/animation/bring 등

커뮤니티 글 등록

POST

/user/createPost

댓글 작성

POST

/user/comment/write/{postId}

💡 이 디렉토리는 RESTful API 의 진입 지점으로, Swagger/OpenAPI 문서 생성 시 핵심 역할을 합니다