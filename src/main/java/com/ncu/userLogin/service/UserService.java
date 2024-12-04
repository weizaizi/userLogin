package com.ncu.userLogin.service;

import com.ncu.userLogin.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author wsyhg
 * @description 针对表【user】的数据库操作Service
 * @createDate 2024-12-02 09:17:17
 */
public interface UserService extends IService<User> {
    long userRegister(String checkPassword,User user);
}
