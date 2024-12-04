package com.ncu.userLogin.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncu.userLogin.mapper.UserMapper;
import com.ncu.userLogin.pojo.User;
import com.ncu.userLogin.service.UserService;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wsyhg
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-12-02 09:17:17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public long userRegister(String checkPassword, User user) {
        //各项不为空
            return -1;
        }
        //校验密码和密码相同
        if (!checkPassword.equals(user.getPassword())) {
            return -1;
        }
        //密码合法
        String pattern = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[^\\da-zA-Z\\s]).{4,9}$";
        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(user.getPassword());
        if (!matcher.find()) {
            return -1;
        }
        //账户唯一
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", user.getUserAccount());
        long count = this.count(queryWrapper);
        if (count != 0) {
            return -1;
        }
        //密码加密
        user.setPassword(MD5.create().digestHex(user.getPassword(), "UTF-8"));
        //插入数据
        boolean save = this.save(user);
        if (!save) {
            return -1;
        }
        //返回ID
        return user.getId();
    }
}




