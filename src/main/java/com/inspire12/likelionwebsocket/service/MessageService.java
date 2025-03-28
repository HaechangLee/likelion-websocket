package com.inspire12.likelionwebsocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Set;

@Service
public class MessageService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public MessageService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public ChatMessage createWelcomeMessage(ChatMessage chatMessage) {
        ChatMessage welcomeMessage = ChatMessage.builder()
                .sender("System")
                .content(
                        String.format("""
                        %s 님이 들어왔습니다.
                        """, chatMessage.getSender()))
                .type(ChatMessage.MessageType.JOIN)
                .build();

        return welcomeMessage;
    }

    public void callUser(String userName, ChatMessage chatMessage) {
        simpMessagingTemplate.convertAndSend(("/user/"+userName+"/queue/private"),chatMessage);
    }
}
