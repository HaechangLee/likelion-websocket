package com.inspire12.likelionwebsocket.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.security.Principal;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    // 연결된 모든 세션을 저장할 스레드 안전한 Set
    @Getter
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>(); //세션 저장용 자료구조 (CopyOnWriteArraySet
    private final ObjectMapper objectMapper; // ??? objmapper 란?

    public ChatWebSocketHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session); //ws 연결 되면 세션을 set에 저장
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 수신한 메시지를 모든 세션에 브로드캐스트
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
        TextMessage messageToSend = message;
        if (chatMessage.getType() == ChatMessage.MessageType.JOIN) { //입장메시지의 경우
            ChatMessage welcomeMessage = ChatMessage.createWelcomeMessage(chatMessage.getSender());
            messageToSend = new TextMessage(objectMapper.writeValueAsBytes(welcomeMessage));
        }

//        else if (chatMessage.getType() == ChatMessage.MessageType.LEAVE) {
//            ChatMessage leavingMessage = ChatMessage.createLeavingMessage(chatMessage.getSender());
//            messageToSend = new TextMessage(objectMapper.writeValueAsBytes(leavingMessage));
//        }

        for (WebSocketSession webSocketSession : sessions) { //set내의 모든 세션들에 메시지 전송
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(messageToSend);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session); // ws 연결 종료 시 set에서 삭제
    }
}

