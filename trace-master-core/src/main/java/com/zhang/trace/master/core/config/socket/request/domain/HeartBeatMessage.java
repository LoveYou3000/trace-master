package com.zhang.trace.master.core.config.socket.request.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 心跳消息
 *
 * @author zhang
 * @date 2024-10-18 09:43
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HeartBeatMessage extends BaseSocketMessage {

    /**
     * 心跳
     */
    private String ping;

    private String pong;

}

