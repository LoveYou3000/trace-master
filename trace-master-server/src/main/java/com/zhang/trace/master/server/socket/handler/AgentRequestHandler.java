package com.zhang.trace.master.server.socket.handler;

import org.springframework.web.socket.WebSocketSession;

/**
 * agent 向 server 发送的消息处理接口
 *
 * @author zhang
 * @date 2024-10-16 17:15
 */
public interface AgentRequestHandler<T> {

    /**
     * 处理 agent 向 server 发送的消息
     *
     * @param data    agent 向 server 发送的消息体
     * @param session 会话
     */
    @SuppressWarnings("unchecked")
    default void handleMessage(Object data, WebSocketSession session) {
        handle((T) data, session);
    }

    /**
     * 处理 agent 向 server 发送的消息
     *
     * @param data    agent 向 server 发送的消息体
     * @param session 会话
     */
    void handle(T data, WebSocketSession session);

}
