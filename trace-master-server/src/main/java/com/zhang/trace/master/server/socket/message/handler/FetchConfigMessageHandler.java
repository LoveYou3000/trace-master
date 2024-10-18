package com.zhang.trace.master.server.socket.message.handler;

import com.zhang.trace.master.server.socket.message.domain.FetchConfigMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * 拉取配置消息处理
 *
 * @author zhang
 * @date 2024-10-16 17:35
 */
public class FetchConfigMessageHandler implements AgentMessageHandler<FetchConfigMessage> {

    @Override
    public void handle(FetchConfigMessage data, WebSocketSession session) {
        // TODO 拉取配置消息处理
        // WebSocketSessionManager.sendMessage(session, new ObjectMapper().writeValueAsString(最新的配置));
    }

}
