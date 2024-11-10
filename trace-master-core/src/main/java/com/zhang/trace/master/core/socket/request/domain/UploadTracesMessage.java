package com.zhang.trace.master.core.socket.request.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * 上送调用链路信息
 *
 * @author zhang
 * @date 2024-11-10 18:29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UploadTracesMessage extends BaseSocketMessage {

    private Long traceId;

    private TraceMessage rootTrace;

    @Data
    public static class TraceMessage {

        private Long id;

        private String className;

        private String methodName;

        private List<Map<String, String>> args;

        private Long cost;

        private List<TraceMessage> children;

    }

}