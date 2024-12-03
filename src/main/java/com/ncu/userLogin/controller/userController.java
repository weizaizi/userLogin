package com.ncu.userLogin.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ncu.userLogin.constants.UserConstants;
import com.ncu.userLogin.pojo.User;
import com.ncu.userLogin.pojo.request.UserChangeRequest;
import com.ncu.userLogin.pojo.request.UserLoginRequest;
import com.ncu.userLogin.pojo.request.UserQueryRequest;
import com.ncu.userLogin.pojo.request.UserRegisterRequest;
import com.ncu.userLogin.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/user")
public class userController {

    @Resource
    private UserService userService;

    /**
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public boolean register(@RequestBody UserRegisterRequest userRegisterRequest){
        //对传来的参数进行校验
        if (userRegisterRequest == null ||
                StringUtils.isAnyBlank(userRegisterRequest.getUserAccount() ,
                        userRegisterRequest.getCheckPassword() ,
                        userRegisterRequest.getPassword())) {
            return false;
        }

        //参数构造
        User user = new User();
        BeanUtil.copyProperties(userRegisterRequest, user);

        //实现注册业务
        long l = userService.userRegister(userRegisterRequest.getCheckPassword(), user);
        return l>0;
    }


    @PostMapping("/login")
    public User login(@RequestBody UserLoginRequest userLoginRequest ,
                      HttpServletRequest request){

        //参数校验
        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String password = userLoginRequest.getPassword();
        if (StringUtils.isAnyBlank(userAccount , password)) {
            return null;
        }

        //业务实现
        return userService.userLogin(userAccount, password, request);
    }
    /**
     * 针对管理员以及用户本身的删除
     */
    @GetMapping("/delete")
    public boolean delete(@RequestParam String userAccount ,
                          HttpServletRequest request){
        //参数校验
        if(StringUtils.isAnyBlank(userAccount)){
            return false;
        }
        //身份校验
        if (extracted(userAccount, request)) return false;

        //业务实现
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        return userService.remove(queryWrapper);
    }

    /**
     * 身份认证
     */
    private static boolean extracted(String userAccount, HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute(UserConstants.login);
        Integer userRole = user.getUserRole();
        String nowUserAccount = user.getUserAccount();
        return !nowUserAccount.equals(userAccount)&&!(userRole == UserConstants.admin);
    }

    /**
     * 针对管理员和用户本人的修改
     */
    @PostMapping("/change")
    public boolean change(@RequestBody UserChangeRequest userChangeRequest,
                          HttpServletRequest request){
        //参数校验
        if(userChangeRequest == null) return false;
        String userAccount = userChangeRequest.getUserAccount();

        //身份校验
        if (StringUtils.isAnyBlank(userAccount) || extracted(userAccount, request)) return false;

        //参数构造
        User user = new User();
        BeanUtil.copyProperties(userChangeRequest, user);
        if(user.getPassword() != null){
            user.setPassword(MD5.create().digestHex(user.getPassword()));
        }
        user.setUpdateTime(Date.from(Instant.now()));

        //业务实现
        return userService.saveOrUpdate(user);
    }
    /**
     * 针对管理员的查询
     */
    @PostMapping("/query")
    public List<User> query(@RequestBody(required = false)UserQueryRequest userQueryRequest , HttpServletRequest request){
        //参数构造
        if(userQueryRequest == null){
            return userService.list();
        }
        //身份校验
        if (extracted(userQueryRequest.getUserAccount(), request)) {
            return null;
        }
        //业务实现
        Long id = userQueryRequest.getId();
        String userAccount = userQueryRequest.getUserAccount();
        String username = userQueryRequest.getUsername();
        Integer gender = userQueryRequest.getGender();
        String phone = userQueryRequest.getPhone();
        String email = userQueryRequest.getEmail();
        Integer isValid = userQueryRequest.getIsValid();
        Date startCreateTime = userQueryRequest.getStartCreateTime();
        Date endCreateTime = userQueryRequest.getEndCreateTime();
        Integer userRole = userQueryRequest.getUserRole();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id!=null,  "id" ,id);
        queryWrapper.eq(userAccount!=null,  "user_account" ,userAccount);
        queryWrapper.like(username!=null,  "username" ,username);
        queryWrapper.eq(gender!=null,  "gender" ,gender);
        queryWrapper.likeRight(phone!=null,  "phone" ,phone);
        queryWrapper.like(email!=null,  "email" ,email);
        queryWrapper.eq(isValid!=null,  "is_valid" ,isValid);
        queryWrapper.eq(userRole!=null,  "user_role" ,userRole);
        queryWrapper.ge(startCreateTime!=null, "create_time", startCreateTime);
        queryWrapper.le(endCreateTime!=null, "create_time", endCreateTime);

        //对返回值脱敏
        List<User> list = userService.list(queryWrapper);
        return list.stream()
                .peek(user -> user.setPassword(null))
                .collect(Collectors.toList());
    }
}
