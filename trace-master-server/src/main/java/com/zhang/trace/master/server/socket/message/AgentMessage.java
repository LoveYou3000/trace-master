package com.zhang.trace.master.server.socket.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

/**
 * agent 向 server 发送的消息实体类
 *
 * @author zhang
 * @date 2024-10-16 17:01
 */
@Data
@JsonDeserialize(using = AgentMessageDeserializer.class)
public class AgentMessage<T> {

    private T data;

    private AgentMessageType type;

}
