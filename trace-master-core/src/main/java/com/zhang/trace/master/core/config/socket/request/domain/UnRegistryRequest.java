package com.zhang.trace.master.core.config.socket.request.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 收到的反注册消息
 *
 * @author zhang
 * @date 2024-10-18 09:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UnRegistryRequest extends BaseRequest {

    /**
     * 实例id
     */
    private String instanceId;

}
