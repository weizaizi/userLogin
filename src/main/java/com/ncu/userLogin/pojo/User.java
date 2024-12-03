package com.ncu.userLogin.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 主键Id
     */
    @TableId(type = IdType.ASSIGN_ID)
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

    /**
     * 账户是否合法 0为正常，1为异常
     */
    private Integer isValid;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private Integer userRole;

    /**
     * 账户是否存在
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}