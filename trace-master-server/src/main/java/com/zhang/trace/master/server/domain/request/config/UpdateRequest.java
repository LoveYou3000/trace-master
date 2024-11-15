package com.zhang.trace.master.server.domain.request.config;

import com.zhang.trace.master.core.config.TraceMasterAgentConfig;
import lombok.Data;

/**
 * 配置管理-更新配置请求
 *
 * @author zhang
 * @date 2024-11-13 16:31
 */
@Data
public class UpdateRequest {

    private String appId;

    private TraceMasterAgentConfig config;

}
