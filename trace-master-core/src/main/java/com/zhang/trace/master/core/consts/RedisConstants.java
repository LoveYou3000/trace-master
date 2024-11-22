package com.zhang.trace.master.core.consts;

/**
 * redis 常量类
 *
 * @author zhang
 * @date 2024-11-20 15:51
 */
public interface RedisConstants {

    String TRACE_MASTER = "tm:";

    String AGENT = "ag:";

    String SERVER = "sr:";

    String REGISTRY_LIST = TRACE_MASTER + SERVER + "reg_l";

    String CONFIG_LIST = TRACE_MASTER + SERVER + "conf_l";

    String CONFIG_DEFAULT = TRACE_MASTER + SERVER + "conf_default";

}
