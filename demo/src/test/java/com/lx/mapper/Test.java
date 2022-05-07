package com.lx.mapper;

import com.lx.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootTest
public class Test {

    @Autowired
    private UserMapper userMapper;

    @org.junit.jupiter.api.Test
    public void test() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @org.junit.jupiter.api.Test
    public void test2() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean b = encoder.matches("123456",
                "$2a$10$J7LVGpkMBOv8pKPT9QMMDuLCU/3k9gc0SoC8CYFtu4F6DhpmW5kOK");
        System.out.println(b);
    }
}
