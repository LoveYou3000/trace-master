package com.zhang.trace.master.server.domain.request.config;

import com.zhang.trace.master.server.domain.request.base.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 配置管理-配置列表请求
 *
 * @author zhang
 * @date 2024-11-13 16:26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ListRequest extends PageRequest {

    private String appId;

}
