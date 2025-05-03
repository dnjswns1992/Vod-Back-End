


✅ 주요 클래스 설명
PostManager
게시글 생성 및 삭제에 대한 핵심 로직을 담당합니다.

단순 CRUD 이상의 사용자 인증 및 소유자 검증이 포함됩니다.


핵심 메서드:

메서드명	설명
createPost(PostResponseDto dto)	게시글을 DB에 저장합니다. 사용자 인증 정보를 기반으로 작성자를 설정합니다.
removePost(int postId)	현재 로그인한 사용자가 해당 게시글의 작성자인지 검증한 후 삭제합니다.

CommentManager
게시글에 대한 댓글 작성 로직을 처리합니다.

JWT 토큰을 이용한 사용자 식별, 게시글 존재 여부 확인, Entity 변환 등의 과정을 포함합니다.

핵심 메서드:

메서드명	설명
createComment(CommentResponseDto dto, int postId, String token)	댓글을 작성하고 해당 게시글과 연관된 상태로 저장합니다. JWT 토큰에서 사용자 정보를 추출합니다.

🔧 기술 포인트
TransferModelMapper를 이용해 DTO → Entity 변환을 자동화하여 중복 코드 감소

JWT 기반 사용자 인증 (JwtTokenProvider)

게시글 또는 사용자 존재 여부를 반드시 검증한 후 처리

📌 예시 흐름
댓글 작성 시:

클라이언트가 JWT 토큰과 댓글 내용을 전송

CommentManager.createComment() 호출

토큰에서 사용자 추출 → CommonEntity 조회

댓글 Entity 생성 및 게시글과 연관 설정

DB에 저장