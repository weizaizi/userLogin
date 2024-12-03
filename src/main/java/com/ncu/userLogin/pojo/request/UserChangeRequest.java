package com.ncu.userLogin.pojo.request;

import lombok.Data;

@Data
public class UserChangeRequest {
    /**
     * 主键Id
     */
    private Long id;

    /**
     * 账户
     */
    private String userAccount;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 账户密码
     */
    private String password;

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

    private Integer userRole;

}
