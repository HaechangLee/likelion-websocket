package com.inspire12.likelionwebsocket.handshake;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class CustomHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        // 예시: UUID 기반 임시 Principal 생성 (익명일 경우)
        String token = getTokenFromRequest(request);

        if (token != null && validateToken(token)) {
            String username = extractUsernameFromToken(token);
            return () -> username;
        }

        return null;
    }

    private String getTokenFromRequest(ServerHttpRequest request) {
        // URL 파라미터에서 토큰 추출
        String query = request.getURI().getQuery();
        if (query != null && query.contains("token=")) {
            return query.split("token=")[1];
        }
        return null;
    }

    private boolean validateToken(String token) {
        // JWT 토큰 검증 로직 구현 (JWT 라이브러리 이용)
        return true;
    }

    private String extractUsernameFromToken(String token) {
        // JWT 토큰에서 사용자 정보(username) 추출 로직 구현
        return token; // 예시로 간략히 처리
    }

}
