package com.ncu.userLogin.service;

import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import com.ncu.userLogin.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wsyhg
 * @description 针对表【user】的数据库操作Service
 * @createDate 2024-12-02 09:17:17
 */
public interface UserService extends IService<User> {
    /**
     *
     * @param checkPassword 校验密码
     * @param user 注册的用户信息
     * @return 插入值的ID
     */
    long userRegister(String checkPassword,User user);

    User userLogin(String userAccount,String password , HttpServletRequest request);
}
