package com.zhang.trace.master.core.config.socket.request.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 收到的心跳消息
 *
 * @author zhang
 * @date 2024-10-18 09:43
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HeartBeatRequest extends BaseRequest {

    /**
     * 心跳
     */
    private String ping;

}

