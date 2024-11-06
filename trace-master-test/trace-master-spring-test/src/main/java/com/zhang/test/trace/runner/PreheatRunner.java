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
        a();
    }

    private void a() {
        b();
    }

    private void b() {
        c();
    }

    private void c() {
    }

}
