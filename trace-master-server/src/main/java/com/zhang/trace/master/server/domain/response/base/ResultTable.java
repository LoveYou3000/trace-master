package com.zhang.trace.master.server.domain.response.base;

import lombok.Builder;
import lombok.Data;

/**
 * 分页查询返回结果包装类
 *
 * @author zhang
 * @date 2024-11-10 12:27
 */
@Data
@Builder
public class ResultTable<T> {

    private boolean success;

    private ResultPage<T> data;

}