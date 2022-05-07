package com.lx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lx.mapper.MenuMapper;
import com.lx.mapper.UserMapper;
import com.lx.pojo.LoginUser;
import com.lx.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUName,username);
        User user = userMapper.selectOne(wrapper);

        // 如果没有查询到用户，就抛出异常
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名或密码错误");
        }

        //TODO 查询对应的权限信息
//        List<String> list = new ArrayList<>(Arrays.asList("admin", "test"));
        List<String> list = menuMapper.selectPermsByUserId(user.getId());
        // 查询到了，把数据封装成UserDetail返回
        return new LoginUser(user, list);
    }
}
