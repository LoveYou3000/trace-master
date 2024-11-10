package com.zhang.trace.master.server.domain.response.base;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 分页查询返回结果包装类
 *
 * @author zhang
 * @date 2024-11-10 12:29
 */
@Data
@Builder
public class ResultPage<T> {

    private List<T> list;

    private Long total;

    private Integer pageSize;

    private Integer currentPage;

}
