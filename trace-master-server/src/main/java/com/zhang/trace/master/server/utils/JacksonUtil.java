package com.zhang.trace.master.server.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * jackson 工具类
 *
 * @author zhang
 * @date 2024-10-18 14:15
 */
@Component
public class JacksonUtil {

    private static ObjectMapper OBJECT_MAPPER;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        JacksonUtil.OBJECT_MAPPER = objectMapper;
    }

    @SneakyThrows(JsonProcessingException.class)
    public static String toJsonString(Object obj) {
        return OBJECT_MAPPER.writeValueAsString(obj);
    }

    @SneakyThrows({JsonProcessingException.class, JsonMappingException.class})
    public static <T> T parseObj(String json, Class<T> klz) {
        return OBJECT_MAPPER.readValue(json, klz);
    }

    @SneakyThrows(JsonProcessingException.class)
    public static <T> T parseTreeNodeObj(TreeNode treeNode, Class<T> klz) {
        return OBJECT_MAPPER.treeToValue(treeNode, klz);
    }

}
