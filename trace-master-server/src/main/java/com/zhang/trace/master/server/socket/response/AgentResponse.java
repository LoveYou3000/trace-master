package com.zhang.trace.master.server.socket.response;

import com.zhang.trace.master.core.config.socket.response.domain.BaseResponse;

/**
 * server 向 agent 响应的消息实体类
 *
 * @author zhang
 * @date 2024-10-18 10:45
 */
public record AgentResponse<T extends BaseResponse>(T data) {

}
