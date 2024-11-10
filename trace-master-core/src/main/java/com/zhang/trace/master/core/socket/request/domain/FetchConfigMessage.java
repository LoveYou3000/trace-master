package com.zhang.trace.master.core.socket.request.domain;

import com.zhang.trace.master.core.config.TraceMasterAgentConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 拉取配置消息
 *
 * @author zhang
 * @date 2024-10-18 09:52
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FetchConfigMessage extends BaseSocketMessage {

    /**
     * 配置
     */
    private TraceMasterAgentConfig config;

}
