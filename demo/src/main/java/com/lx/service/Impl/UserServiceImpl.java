package com.lx.service.Impl;

import com.lx.pojo.LoginUser;
import com.lx.pojo.ResponseResult;
import com.lx.pojo.User;
import com.lx.service.UserService;
import com.lx.utils.JwtUtils;
import com.lx.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public ResponseResult login(User user) {
        // 1.AuthenticationManager authenticate进行用户验证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUName(),user.getPwd());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 2.验证失败，给出相应提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登陆失败");
        }
        // 3.如果认证通过，以userid生成一个jwt存入
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userIdStr = loginUser.getUser().getId().toString();
        String jwt = JwtUtils.createToken(userIdStr);
        Map<String, String> map = new HashMap<>();
        map.put("token",jwt);
        // 4.把完整的用户信息存入redis，userid为key
        redisUtils.set("login:" + userIdStr, loginUser);
        return new ResponseResult(200,"登陆成功",map);
    }

    @Override
    public ResponseResult logout() {
        // 1.获取SecurityContextHolder中的用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long id = loginUser.getUser().getId();
        redisUtils.del("login:" + id);
        // 2.删除redis中关于用户信息
        return new ResponseResult(200, "注销成功");
    }
}
