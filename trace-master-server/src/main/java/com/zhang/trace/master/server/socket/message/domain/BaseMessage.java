package com.zhang.trace.master.server.socket.message.domain;

import lombok.Data;

/**
 * 接收到的消息 基类
 *
 * @author zhang
 * @date 2024-10-18 09:43
 */
@Data
public class BaseMessage {

    private String appId;

}
