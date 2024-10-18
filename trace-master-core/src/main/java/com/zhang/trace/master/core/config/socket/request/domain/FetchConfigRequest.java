package com.zhang.trace.master.core.config.socket.request.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 收到的拉取配置消息
 *
 * @author zhang
 * @date 2024-10-18 09:52
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FetchConfigRequest extends BaseRequest {
}
