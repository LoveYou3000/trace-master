package com.zhang.trace.master.core.socket.request.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * agent启用消息
 *
 * @author zhang
 * @date 2024-10-30 10:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AgentEnableMessage extends BaseSocketMessage {

    /**
     * 启用状态
     */
    private Boolean enable;

}
