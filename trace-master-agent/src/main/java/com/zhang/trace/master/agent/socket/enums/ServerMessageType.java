package com.zhang.trace.master.agent.socket.enums;

import lombok.RequiredArgsConstructor;

/**
 * server 向 agent 发送的消息类型
 *
 * @author zhang
 * @date 2024-10-16 17:18
 */
@RequiredArgsConstructor
public enum ServerMessageType {

    ;

    private final ServerMessageHandler handler;

}
