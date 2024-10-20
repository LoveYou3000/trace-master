package com.zhang.trace.master.core.config.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

/**
 * jackson 工具类
 *
 * @author zhang
 * @date 2024-10-18 14:15
 */
public class JacksonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SneakyThrows(JsonProcessingException.class)
    public static String toJsonString(Object obj) {
        return OBJECT_MAPPER.writeValueAsString(obj);
    }

    @SneakyThrows(JsonProcessingException.class)
    public static <T> T parseObj(String json, Class<T> klz) {
        return OBJECT_MAPPER.readValue(json, klz);
    }

    @SneakyThrows(JsonProcessingException.class)
    public static <T> T parseTreeNodeObj(TreeNode treeNode, Class<T> klz) {
        return OBJECT_MAPPER.treeToValue(treeNode, klz);
    }

}
