package com.ncu.userLogin.pojo.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable{
    /**
     * 账户
     */
    private String userAccount;

    /**
     * 账户密码
     */
    private String password;
}
