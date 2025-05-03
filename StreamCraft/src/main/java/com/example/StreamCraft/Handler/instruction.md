📦 handler 패키지 설명서

이 패키지는 예외 처리(ErrorHandler)와 WebSocket 처리(WebSocketHandler)에 관련된 컴포넌트를 담당합니다.

📁 com.example.StreamCraft.Handler.ErrorHandler

커스텀 런타임 예외 클래스들을 정의하여 전역 예외 처리기(@ControllerAdvice)에서 활용


📌 주요 클래스:

DuplicateEmailException: 이메일 중복 시 발생

DuplicateNickNameException: 닉네임 중복 시 발생

DuplicateUsernameException: 아이디 중복 시 발생

DuplicateUploadMainTitleException: 업로드된 영상 제목 중복 시 발생

✅ 모두 RuntimeException을 상속하며, 해당 예외는 @ExceptionHandler에서 개별 처리됩니다.

📁 com.example.StreamCraft.Handler.WebSocketHandler

업로드 진행률과 같은 실시간 정보를 클라이언트에 전송하기 위한 WebSocket 핸들러


📌 주요 클래스:

ProgressWebSocketHandler : 업로드 상태(progress)를 실시간으로 클라이언트에게 전달하는 핸들러

✔️ 동작 방식

WebSocket 연결 시 URI 쿼리로 sessionId를 파싱하고 이를 기반으로 세션을 저장

sendMessage(String sessionId, int progress)를 통해 해당 세션으로 진행률 메시지를 전송함

클라이언트와 서버 간 실시간 업로드 진행률을 제공하는 데 사용됨

📌 예시 URI

ws://localhost:8080/ws/video-progress?sessionId=abc123