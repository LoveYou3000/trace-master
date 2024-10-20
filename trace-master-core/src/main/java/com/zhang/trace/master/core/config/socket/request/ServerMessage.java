package com.zhang.trace.master.core.config.socket.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.zhang.trace.master.core.config.socket.request.domain.BaseSocketMessage;
import lombok.Data;

/**
 * server 向 agent 发送的消息实体类
 *
 * @author zhang
 * @date 2024-10-16 17:18
 */
@Data
@JsonDeserialize(using = ServerMessageDeserializer.class)
public class ServerMessage<T extends BaseSocketMessage> {

    private T data;

    private ServerMessageType type;

}
