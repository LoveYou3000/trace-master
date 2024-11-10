package com.zhang.trace.master.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * trace-master 服务端入口
 *
 * @author zhang
 * @date 2024-10-15 19:52
 */
@SpringBootApplication
@EnableWebMvc
public class TraceMasterServer {

    public static void main(String[] args) {
        SpringApplication.run(TraceMasterServer.class, args);
    }

}
