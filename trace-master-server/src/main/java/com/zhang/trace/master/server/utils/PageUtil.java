package com.zhang.trace.master.server.utils;

import com.zhang.trace.master.server.domain.request.base.PageRequest;
import com.zhang.trace.master.server.domain.response.base.ResultPage;

import java.util.List;

/**
 * 内存分页工具类
 *
 * @author zhang
 * @date 2024-11-15 08:57
 */
public class PageUtil {

    public static <T> ResultPage<T> page(List<T> totalList, PageRequest pageRequest) {
        int startIdx = (pageRequest.getCurrentPage() - 1) * pageRequest.getPageSize();
        int endIdx = Math.min(startIdx + pageRequest.getPageSize(), totalList.size());
        List<T> list = totalList.subList(startIdx, endIdx);
        return ResultPage.<T>builder()
                .total((long) totalList.size())
                .currentPage(pageRequest.getCurrentPage())
                .pageSize(pageRequest.getPageSize())
                .list(list)
                .build();
    }

}
