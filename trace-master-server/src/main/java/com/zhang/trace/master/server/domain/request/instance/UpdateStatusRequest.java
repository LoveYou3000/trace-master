package com.zhang.trace.master.server.domain.request.instance;

import lombok.Data;

/**
 * 实例管理-更新实例启用状态请求
 *
 * @author zhang
 * @date 2024-11-10 13:22
 */
@Data
public class UpdateStatusRequest {

    private String appId;

    private String instanceId;

    private Integer status;

}
