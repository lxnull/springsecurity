package com.lx.controller;

import com.lx.pojo.ResponseResult;
import com.lx.pojo.User;
import com.lx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user) {
        ResponseResult login = userService.login(user);
        return login;
    }

    @GetMapping("/logout")
    public ResponseResult logout() {
        return userService.logout();
    }
}
