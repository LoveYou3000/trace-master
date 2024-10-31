package com.zhang.trace.master.core.config.socket.request.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * agent启用消息
 *
 * @author zhang
 * @date 2024-10-30 10:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AgentEnableMessage extends BaseSocketMessage {

    /**
     * 启用状态
     */
    private Boolean enable;

}
