package com.zhang.trace.master.server.socket.domain;

import com.zhang.trace.master.server.socket.enums.AgentMessageType;
import lombok.Data;

/**
 * agent 向 server 发送的消息实体类
 *
 * @author zhang
 * @date 2024-10-16 17:01
 */
@Data
public class AgentMessage {

    private String msg;

    private AgentMessageType type;

}
