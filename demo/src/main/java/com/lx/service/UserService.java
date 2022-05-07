package com.lx.service;

import com.lx.pojo.ResponseResult;
import com.lx.pojo.User;

public interface UserService {
    ResponseResult login(User user);

    ResponseResult logout();
}
