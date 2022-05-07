package com.lx.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class Test {

    @org.junit.jupiter.api.Test
    public void test() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123456");
        System.out.println(encode);
        String encode1 = encoder.encode("123456");
        System.out.println(encode1);
    }

    @org.junit.jupiter.api.Test
    public void test2() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean b = encoder.matches("123456",
                "$2a$10$J7LVGpkMBOv8pKPT9QMMDuLCU/3k9gc0SoC8CYFtu4F6DhpmW5kOK");
        System.out.println(b);
    }
}
