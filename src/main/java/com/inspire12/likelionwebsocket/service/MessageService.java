package com.inspire12.likelionwebsocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.holder.WebSocketSessionHolder;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Set;

@Service
public class MessageService {
    private final ChatWebSocketHandler chatWebSocketHandler;
    private final ObjectMapper objectMapper;

    public MessageService(ChatWebSocketHandler chatWebSocketHandler, ObjectMapper objectMapper) {
        this.chatWebSocketHandler = chatWebSocketHandler;
        this.objectMapper = objectMapper;
    } // messageService는 websocket 최초 연결 시에 생성됨. => ChatWebSocketHandler와 WebSocketConfig에서 담당

    public ChatMessage sendMessage(ChatMessage chatMessage) {
        // 메시지 주고받는 로직
        try {
            TextMessage messageToSend = new TextMessage(objectMapper.writeValueAsBytes(chatMessage));
            Set<WebSocketSession> sessions = WebSocketSessionHolder.getSessions();
            for (WebSocketSession session : sessions) {
                session.sendMessage(messageToSend);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return chatMessage;
    }
}
