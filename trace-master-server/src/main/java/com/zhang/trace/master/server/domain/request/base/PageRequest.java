package com.zhang.trace.master.server.domain.request.base;

import lombok.Data;

/**
 * 分页列表查询请求
 *
 * @author zhang
 * @date 2024-11-10 12:37
 */
@Data
public class PageRequest {

    private Integer pageSize;

    private Integer currentPage;

    private String orderByColumn;

}
