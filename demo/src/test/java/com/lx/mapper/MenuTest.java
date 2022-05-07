package com.lx.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MenuTest {

    @Autowired
    private MenuMapper mapper;

    @Test
    public void test() {
        List<String> list = mapper.selectPermsByUserId(1L);
        for (String s : list) {
            System.out.println(s);
        }
    }
}
