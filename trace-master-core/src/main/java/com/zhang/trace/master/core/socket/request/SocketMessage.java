package com.zhang.trace.master.core.socket.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.zhang.trace.master.core.socket.request.domain.BaseSocketMessage;

/**
 * socket 发送的消息
 *
 * @param data 消息体
 * @param type 消息类型
 * @author zhang
 * @date 2024-10-21 17:04
 */
@JsonDeserialize(using = SocketMessageDeserializer.class)
public record SocketMessage<T extends BaseSocketMessage>(T data, SocketMessageType type) {

}

