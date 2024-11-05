package com.zhang.trace.master.server.socket.handler.impl;

import com.zhang.trace.master.server.socket.WebSocketSessionManager;
import com.zhang.trace.master.server.socket.handler.AgentRequestHandler;
import com.zhang.trace.master.core.config.socket.request.domain.UnRegistryMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

/**
 * 反注册消息处理器
 *
 * @author zhang
 * @date 2024-10-16 17:24
 */
@Slf4j
public class UnRegisterRequestHandler implements AgentRequestHandler<UnRegistryMessage> {

    @Override
    public void handle(UnRegistryMessage unRegistryRequest, WebSocketSession session) {
        log.info("收到反注册消息:{}", unRegistryRequest);
        WebSocketSessionManager.removeSession(unRegistryRequest.getAppId(), unRegistryRequest.getInstanceId());
    }

}
