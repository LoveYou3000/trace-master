package com.zhang.trace.master.core.config.socket.response.domain;

import com.zhang.trace.master.core.config.TraceMasterAgentConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 响应的拉取配置消息
 *
 * @author zhang
 * @date 2024-10-18 09:52
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FetchConfigResponse extends BaseResponse {

    private TraceMasterAgentConfig config;

}
