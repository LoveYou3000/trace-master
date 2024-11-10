package com.zhang.trace.master.server.socket.handler;

import com.zhang.trace.master.core.socket.request.SocketMessageType;
import com.zhang.trace.master.server.socket.handler.impl.FetchConfigRequestHandler;
import com.zhang.trace.master.server.socket.handler.impl.HeartBeatRequestHandler;
import com.zhang.trace.master.server.socket.handler.impl.RegisterRequestHandler;
import com.zhang.trace.master.server.socket.handler.impl.UnRegisterRequestHandler;
import com.zhang.trace.master.server.socket.handler.impl.UploadTracesRequestHandler;
import org.springframework.web.socket.WebSocketSession;

/**
 * agent 向 server 发送的消息处理器接口
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
     * 获取消息类型对应的消息处理器
     *
     * @param agentMessageType 消息类型
     * @return 消息处理器
     */
    static AgentRequestHandler<?> getAgentRequestHandler(SocketMessageType agentMessageType) {
        switch (agentMessageType) {
            case HEARTBEAT -> {
                return new HeartBeatRequestHandler();
            }
            case REGISTER -> {
                return new RegisterRequestHandler();
            }
            case UNREGISTER -> {
                return new UnRegisterRequestHandler();
            }
            case FETCH_CONFIG -> {
                return new FetchConfigRequestHandler();
            }
            case UPLOAD_TRACES -> {
                return new UploadTracesRequestHandler();
            }
            default -> throw new RuntimeException("未适配的请求类型:" + agentMessageType);
        }
    }

    /**
     * 处理 agent 向 server 发送的消息
     *
     * @param data    agent 向 server 发送的消息体
     * @param session 会话
     */
    void handle(T data, WebSocketSession session);

}
