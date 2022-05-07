package com.lx.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtTest {

    @Test
    public void test() {
        String token = JwtUtils.createToken("123456");
        System.out.println(token);
        String val = JwtUtils.parseJWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxIiwic3ViIjoiYWRtaW4tdGVzdCIsImV4cCI6MTY1MTEzNTYzMCwianRpIjoiMWE2ZDZiMzQtZDdjYy00ODBlLTgxNDEtZmQ5MjU4ODhjMDg4In0.jn8udPjDuLZ5gAamcm_Pix5gKcYuB1gGu2Z9hKENC28");
        System.out.println(val);
    }
}
