package com.zhang.trace.master.core.config.socket.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.zhang.trace.master.core.config.socket.request.domain.BaseSocketMessage;
import lombok.Data;

/**
 * agent 向 server 发送的消息实体类
 *
 * @author zhang
 * @date 2024-10-16 17:01
 */
@Data
@JsonDeserialize(using = AgentMessageDeserializer.class)
public class AgentMessage<T extends BaseSocketMessage> {

    /**
     * 消息体
     */
    private T data;

    /**
     * 消息的类型
     */
    private AgentMessageType type;

}
