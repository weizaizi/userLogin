package com.ncu.userLogin.service;


import cn.hutool.http.server.HttpServerResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ncu.userLogin.mapper.UserMapper;
import com.ncu.userLogin.pojo.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {


    @Resource
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testAddUser() {
        QueryWrapper<User> id = new QueryWrapper<>();
        id.eq("id", 0);
        User user = userMapper.selectOne(id);
        System.out.println(user);
    }

    @Test
    public void testUserRegister() {
        User user = new User();
        user.setUserAccount("789");
        user.setUsername("小威生吃小孩");
        user.setPassword("123abc!");
        user.setGender(0);
        user.setAvatarUrl("aaa");
        user.setPhone("123");
        user.setEmail("123");
        long l = userService.userRegister("123abc!", user);
        Assert.assertNotEquals(-1, l);
    }
}
