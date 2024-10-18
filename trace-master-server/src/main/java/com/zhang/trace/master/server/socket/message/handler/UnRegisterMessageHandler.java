package com.zhang.trace.master.server.socket.message.handler;

import com.zhang.trace.master.server.socket.message.domain.UnRegistryMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * 反注册消息处理
 *
 * @author zhang
 * @date 2024-10-16 17:24
 */
public class UnRegisterMessageHandler implements AgentMessageHandler<UnRegistryMessage> {

    @Override
    public void handle(UnRegistryMessage data, WebSocketSession session) {
        // TODO 反注册消息处理
    }

}
