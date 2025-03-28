package com.inspire12.likelionwebsocket.controller;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ChatWhisperController {

    private final MessageService messageService;
    @PostMapping("/call/user")
    public ChatMessage callUser(@RequestParam String userName, @RequestBody ChatMessage chatMessage) {
        messageService.callUser(userName, chatMessage);
        return chatMessage;
    }
}
