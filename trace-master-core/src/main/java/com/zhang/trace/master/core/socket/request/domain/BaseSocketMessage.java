package com.zhang.trace.master.core.socket.request.domain;

import lombok.Data;

/**
 * socket消息 基类
 *
 * @author zhang
 * @date 2024-10-18 09:43
 */
@Data
public class BaseSocketMessage {

    private String appId;

    private String instanceId;

}
