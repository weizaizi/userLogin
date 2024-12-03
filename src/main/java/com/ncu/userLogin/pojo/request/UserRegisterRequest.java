package com.ncu.userLogin.pojo.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {
    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 校验密码
     */
    private String checkPassword;

    /**
     * 用户名称
     */
    private String username;


    /**
     * 性别 0为男性，1为女性
     */
    private Integer gender;

    /**
     * 头像的URL
     */
    private String avatarUrl;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱账户
     */
    private String email;

}
