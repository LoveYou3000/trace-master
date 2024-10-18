package com.zhang.trace.master.core.config.socket.request.domain;

import lombok.Data;

/**
 * 收到的消息 基类
 *
 * @author zhang
 * @date 2024-10-18 09:43
 */
@Data
public class BaseRequest {

    private String appId;

    private String instanceId;

}
