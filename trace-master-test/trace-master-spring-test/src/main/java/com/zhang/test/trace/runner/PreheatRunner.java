package com.zhang.test.trace.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 预热
 *
 * @author zhang
 * @date 2024-11-06 19:51
 */
@Component
public class PreheatRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        a("1");
    }

    private void a(String id) {
        b("2");
    }

    private void b(String id) {
        c("3");
    }

    private void c(String id) {
    }

}
