package com.example.StreamCraft.Handler.WebSocketHandler;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.handler.TextWebSocketHandler;
@Component
public class ProgressWebSocketHandler extends TextWebSocketHandler {

    // 클라이언트 세션을 저장하는 Map (sessionId 기준)
    private static Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();


    // WebSocket 연결 수립 시 세션 등록
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        URI uri = session.getUri();
        if (uri != null && uri.getQuery() != null) {
            String query = uri.getQuery();
            String[] parameters = query.split("&");
            for (String parameter : parameters) {
                String[] keyValue = parameter.split("=");
                if (keyValue.length == 2 && "sessionId".equals(keyValue[0])) {
                    String sessionId = keyValue[1];
                    sessions.put(sessionId, session);
                    System.out.println("Session registered: " + sessionId);
                    break;
                }
            }
        } else {
            throw new IllegalArgumentException("Session ID not found in WebSocket URI query");
        }
    }

    // 연결 종료 시 세션 제거

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.values().remove(session);
        System.out.println("Session closed: " + session.getId());
    }

    // 클라이언트에 진행률 메시지를 전송

    public void sendMessage(String sessionId, int progress) {
        WebSocketSession session = sessions.get(sessionId);
        if (session != null && session.isOpen()) {
            try {
                // JSON 형식으로 메시지 전송
                String message = String.format("{\"type\":\"progress\",\"progress\":%d}", progress);
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
