package com.zhang.trace.master.agent.socket.handler;

import org.java_websocket.client.WebSocketClient;

/**
 * server 向 agent 发送的消息处理接口
 *
 * @author zhang
 * @date 2024-10-16 17:19
 */
public interface ServerRequestHandler<T> {

    /**
     * 处理 server 向 agent 发送的消息
     *
     * @param data    server 向 agent 发送的消息体
     * @param session 会话
     */
    @SuppressWarnings("unchecked")
    default void handleMessage(Object data, WebSocketClient session) {
        handle((T) data, session);
    }

    /**
     * 处理 server 向 agent 发送的消息
     *
     * @param data    server 向 agent 发送的消息体
     * @param session 会话
     */
    void handle(T data, WebSocketClient session);

}
