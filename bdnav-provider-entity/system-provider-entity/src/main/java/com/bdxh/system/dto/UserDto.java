package com.bdxh.system.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserDto implements Serializable {


    private static final long serialVersionUID = 7421251545883628226L;

    /**
     * 头像
     */
    private String image;

    /**
     * 用户名
     */

    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 性别 1 男 2 女
     */
    private Byte sex;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 手机
     */
    private String phone;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 状态 1 正常 2 锁定
     */
    private Byte status;

    /**
     * 类型 1 普通用户 2 管理员
     */
    private Byte type;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 操作人
     */
    private Long operator;

    /**
     * 备注
     */
    private String remark;

}
