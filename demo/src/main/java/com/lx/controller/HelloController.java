package com.lx.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
//    @PreAuthorize("hasAuthority('system:test:list')")
    /**
     * 自定义的权限校验
     */
    @PreAuthorize("@sg.hasAuthority('system:test:list')")
    public String hello() {
        return "hello";
    }
}
