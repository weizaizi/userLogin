package com.ncu.userLogin.pojo.request;


import lombok.Data;

import java.util.Date;
@Data
public class UserQueryRequest {
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
     * 性别 0为男性，1为女性
     */
    private Integer gender;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱账户
     */
    private String email;

    /**
     * 账户是否合法 0为正常，1为异常
     */
    private Integer isValid;

    /**
     * 创建时间
     */
    private Date startCreateTime;
    private Date endCreateTime;

    private Integer userRole;

}
