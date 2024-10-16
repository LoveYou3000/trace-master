package com.zhang.trace.master.server.socket.message.handler;

import com.zhang.trace.master.server.socket.domain.AgentMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * agent 向 server 发送的消息处理接口
 *
 * @author zhang
 * @date 2024-10-16 17:15
 */
public interface AgentMessageHandler {

    /**
     * 处理 agent 向 server 发送的消息
     *
     * @param agentMessage agent 向 server 发送的消息
     * @param session      会话
     */
    void handleAgentMessage(AgentMessage agentMessage, WebSocketSession session);

}
