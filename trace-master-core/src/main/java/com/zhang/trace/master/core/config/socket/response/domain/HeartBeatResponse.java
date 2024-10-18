package com.zhang.trace.master.core.config.socket.response.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 发送的心跳消息
 *
 * @author zhang
 * @date 2024-10-18 09:43
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HeartBeatResponse extends BaseResponse {

    private String pong;

}

