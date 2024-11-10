package com.zhang.trace.master.server.domain.request.instance;

import com.zhang.trace.master.server.domain.request.base.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实例管理-实例列表请求
 *
 * @author zhang
 * @date 2024-11-10 12:31
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ListRequest extends PageRequest {

    private String appId;

    private Integer status;

}
