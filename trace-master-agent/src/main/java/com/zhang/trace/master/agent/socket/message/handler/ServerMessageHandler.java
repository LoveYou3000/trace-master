package com.zhang.trace.master.agent.socket.message.handler;

import com.zhang.trace.master.agent.socket.domain.ServerMessage;

/**
 * server 向 agent 发送的消息处理接口
 *
 * @author zhang
 * @date 2024-10-16 17:19
 */
public interface ServerMessageHandler {

    /**
     * 处理 server 向 agent 发送的消息
     *
     * @param serverMessage server 向 agent 发送的消息
     */
    void handleServerMessage(ServerMessage serverMessage);

}
