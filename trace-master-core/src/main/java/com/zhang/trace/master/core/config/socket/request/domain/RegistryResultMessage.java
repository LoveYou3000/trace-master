package com.zhang.trace.master.core.config.socket.request.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 注册结果消息
 *
 * @author zhang
 * @date 2024-10-18 16:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RegistryResultMessage extends BaseSocketMessage {
}
