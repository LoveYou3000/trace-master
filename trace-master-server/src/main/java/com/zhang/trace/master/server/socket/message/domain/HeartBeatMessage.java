package com.zhang.trace.master.server.socket.message.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接收到的心跳消息
 *
 * @author zhang
 * @date 2024-10-18 09:43
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HeartBeatMessage extends BaseMessage {

    private String ping;

}

