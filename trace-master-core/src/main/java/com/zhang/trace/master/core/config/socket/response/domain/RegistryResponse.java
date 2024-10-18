package com.zhang.trace.master.core.config.socket.response.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 响应的注册消息
 *
 * @author zhang
 * @date 2024-10-18 09:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RegistryResponse extends BaseResponse {

    /**
     * 实例id
     */
    private String instanceId;

}
