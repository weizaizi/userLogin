package com.ncu.userLogin.service;


import com.ncu.userLogin.pojo.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {


    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setId(0L);
        user.setUserAccount("123");
        user.setUsername("小威生吃小孩");
        user.setPassword("123");
        user.setGender(0);
        user.setAvatarUrl("aaa");
        user.setPhone("123");
        user.setEmail("123");
        user.setIsValid(0);

        boolean save = userService.save(user);
        Assert.assertTrue(save);
    }

    @Test
    public void testUserLogin() {
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
