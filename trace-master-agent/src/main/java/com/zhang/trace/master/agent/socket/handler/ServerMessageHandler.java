package com.zhang.trace.master.agent.socket.handler;

import com.zhang.trace.master.agent.socket.handler.impl.ConfigUpdatedMessageHandler;
import com.zhang.trace.master.agent.socket.handler.impl.HeartBeatMessageHandler;
import com.zhang.trace.master.agent.socket.handler.impl.RegistryResultMessageHandler;
import com.zhang.trace.master.core.config.socket.request.ServerMessageType;
import org.java_websocket.client.WebSocketClient;

/**
 * server 向 agent 发送的消息处理接口
 *
 * @author zhang
 * @date 2024-10-16 17:19
 */
public interface ServerMessageHandler<T> {

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
     * 获取消息类型对应的消息处理器
     *
     * @param serverMessageType 消息类型
     * @return 消息处理器
     */
    static ServerMessageHandler<?> getServerRequestHandler(ServerMessageType serverMessageType) {
        switch (serverMessageType) {
            case HEARTBEAT_RESULT -> {
                return new HeartBeatMessageHandler();
            }
            case REGISTRY_RESULT -> {
                return new RegistryResultMessageHandler();
            }
            case CONFIG_UPDATED -> {
                return new ConfigUpdatedMessageHandler();
            }
            default -> throw new RuntimeException("未适配的请求类型:" + serverMessageType);
        }
    }

    /**
     * 处理 server 向 agent 发送的消息
     *
     * @param data    server 向 agent 发送的消息体
     * @param session 会话
     */
    void handle(T data, WebSocketClient session);

}
