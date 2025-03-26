package com.inspire12.likelionwebsocket.holder;

import lombok.Getter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.socket.WebSocketSession;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class WebSocketSessionHolder {

    public static Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    public static Set<WebSocketSession> getSessions() {
        return sessions;
    }

    public static void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public static void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }

}
