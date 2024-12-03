package com.ncu.userLogin.service.impl;

import cn.hutool.crypto.digest.MD5;
import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncu.userLogin.constants.UserConstants;
import com.ncu.userLogin.mapper.UserMapper;
import com.ncu.userLogin.pojo.User;
import com.ncu.userLogin.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wsyhg
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-12-02 09:17:17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public long userRegister(String checkPassword, User user) {
        //各项不为空
        if (user == null || StringUtils.isAnyBlank(user.getUserAccount(), user.getPassword(), checkPassword)) {
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

    @Override
    public User userLogin(String userAccount, String password, HttpServletRequest request) {
        //各项不为空
        if (StringUtils.isAnyBlank(password, userAccount)) {
            return null;
        }
        //密码合法
        /*String pattern = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[^\\da-zA-Z\\s]).{4,9}$";
        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(password);
        if (!matcher.find()) {
            return null;
        }*/

        //从数据库中查询
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("password", MD5.create().digestHex(password));
        userQueryWrapper.eq("user_account", userAccount);
        userQueryWrapper.eq("is_valid", UserConstants.valid);
        User user = userMapper.selectOne(userQueryWrapper);
        System.out.println(user);
        if (user == null) {
            return null;
        }

        //脱敏
        User safetyUser = new User();
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setGender(user.getGender());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setCreateTime(user.getCreateTime());
        safetyUser.setUpdateTime(user.getUpdateTime());

        //将登录状态放入session中
        request.getSession().setAttribute(UserConstants.login, safetyUser);
        System.out.println(safetyUser);
        return safetyUser;
    }
}




