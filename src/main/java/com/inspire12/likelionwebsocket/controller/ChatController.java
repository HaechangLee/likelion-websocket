package com.inspire12.likelionwebsocket.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.config.WebSocketConfig;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.service.ChatWebSocketHandler;
import com.inspire12.likelionwebsocket.service.MessageService;
import org.apache.logging.log4j.message.Message;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

//import java.util.Set;

@RestController
public class ChatController {
    private final MessageService messageService;


    public ChatController(MessageService messageService) {
        this.messageService = messageService;
    } // 컨트롤러 동작 시 MessageService를 받아옴 (Session set, objectmapper)

    @PostMapping("/call")
    public ChatMessage call(@RequestBody ChatMessage chatMessage) throws IOException { // call을 통한 post 요청 처리
        return messageService.sendMessage(chatMessage); //message service에서 세부로직 처리
    }
}
