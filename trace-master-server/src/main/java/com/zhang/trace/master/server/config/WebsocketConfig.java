package com.zhang.trace.master.server.config;

import com.zhang.trace.master.server.socket.handler.TraceMasterServerWebSocketHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.List;

/**
 * websocket 配置
 *
 * @author zhang
 * @date 2024-10-15 20:05
 */
@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebsocketConfig implements WebSocketConfigurer {


    @Override
    public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry registry) {
        registry.addHandler(new TraceMasterServerWebSocketHandler(), "/")
                .setAllowedOrigins("*");
    }

}
