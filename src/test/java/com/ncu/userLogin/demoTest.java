package com.ncu.userLogin;


import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.ncu.userLogin.mapper.UserMapper;
import com.ncu.userLogin.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class demoTest {

    @Autowired
    private UserMapper userMapper;
    @Test
    public void test() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.isTrue(5 == userList.size(), "");
        userList.forEach(System.out::println);
    }
}
