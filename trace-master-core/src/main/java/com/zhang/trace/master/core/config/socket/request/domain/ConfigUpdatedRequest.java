package com.zhang.trace.master.core.config.socket.request.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 收到的配置已更新消息
 *
 * @author zhang
 * @date 2024-10-18 14:46
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ConfigUpdatedRequest extends BaseRequest {

    /**
     * 上次更新时间
     */
    private Long lastUpdate;

}
