package com.zhang.trace.master.core.socket.request.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 反注册消息
 *
 * @author zhang
 * @date 2024-10-18 09:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UnRegistryMessage extends BaseSocketMessage {

}
