package com.ncu.userLogin.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.server.HttpServerResponse;
import com.ncu.userLogin.pojo.User;
import com.ncu.userLogin.pojo.request.UserLoginRequest;
import com.ncu.userLogin.pojo.request.UserRegisterRequest;
import com.ncu.userLogin.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController()
@RequestMapping("/user")
public class userController {

    @Resource
    private UserService userService;


    @PostMapping("/register")
    public boolean register(@RequestBody UserRegisterRequest userRegisterRequest){
        if (userRegisterRequest == null || StringUtils.isAnyBlank(userRegisterRequest.getUserAccount() , userRegisterRequest.getCheckPassword() , userRegisterRequest.getPassword())) {
            return false;
        }
        User user = new User();
        BeanUtil.copyProperties(userRegisterRequest, user);
        long l = userService.userRegister(userRegisterRequest.getCheckPassword(), user);
        return l>0;
    }


    @PostMapping("/login")
    public User login(@RequestBody UserLoginRequest userLoginRequest , HttpServletRequest request){
        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String password = userLoginRequest.getPassword();
        if (StringUtils.isAnyBlank(userAccount , password)) {
            return null;
        }
        return userService.userLogin(userAccount, password, request);
    }
}
