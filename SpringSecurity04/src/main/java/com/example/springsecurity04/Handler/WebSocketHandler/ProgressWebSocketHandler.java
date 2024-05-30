package com.example.springsecurity04.Handler.WebSocketHandler;

import java.net.URI;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.handler.TextWebSocketHandler;
@Component
public class ProgressWebSocketHandler extends TextWebSocketHandler {
    private static Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

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

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.values().remove(session);
        System.out.println("Session closed: " + session.getId());
    }

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
