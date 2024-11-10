package com.zhang.trace.master.server.domain.response.base;

import lombok.Builder;
import lombok.Data;

/**
 * 返回结果的包装类
 *
 * @author zhang
 * @date 2024-11-10 12:26
 */
@Data
@Builder
public class Result<T> {

    private boolean success;

    private T data;

}
