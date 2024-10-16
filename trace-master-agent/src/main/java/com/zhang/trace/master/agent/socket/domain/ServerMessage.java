package com.zhang.trace.master.agent.socket.domain;

import com.zhang.trace.master.agent.socket.enums.ServerMessageType;
import lombok.Data;

/**
 * server 向 agent 发送的消息实体类
 *
 * @author zhang
 * @date 2024-10-16 17:18
 */
@Data
public class ServerMessage {

    private ServerMessageType type;

}
