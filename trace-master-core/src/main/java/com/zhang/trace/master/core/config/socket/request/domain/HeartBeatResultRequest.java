package com.zhang.trace.master.core.config.socket.request.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 响应的心跳消息
 *
 * @author zhang
 * @date 2024-10-18 17:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HeartBeatResultRequest extends BaseRequest {

    private String pong;

}
