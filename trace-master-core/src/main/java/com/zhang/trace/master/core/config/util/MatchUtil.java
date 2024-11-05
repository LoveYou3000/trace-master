package com.zhang.trace.master.core.config.util;

import com.zhang.trace.master.core.config.TraceMasterAgentConfig;

/**
 * 匹配工具类
 *
 * @author zhang
 * @date 2024-11-03 23:15
 */
public class MatchUtil {

    /**
     * 类名是否匹配
     *
     * @param className 类名
     * @param config    配置
     * @return 是否匹配
     */
    public static boolean classMatch(String className, TraceMasterAgentConfig config) {
        for (String excludePackage : config.getExcludePackages()) {
            if (className.startsWith(excludePackage)) {
                return false;
            }
        }
        for (String includePackage : config.getIncludePackages()) {
            if (className.startsWith(includePackage)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 方法名是否匹配
     *
     * @param methodName 方法名
     * @return 是否匹配
     */
    public static boolean methodMatch(String methodName) {
        return true;
    }

    /**
     * 该方法是否为入口
     *
     * @param klzName    类名
     * @param methodName 方法名
     * @param config     配置
     * @return 是否为入口
     */
    public static boolean entranceMatch(String klzName, String methodName, TraceMasterAgentConfig config) {
        String entrance = klzName + "." + methodName;
        return config.getMethodEntrances().contains(entrance);
    }
}
