package com.bdxh.account.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @description: 账户查询dto
 * @author: xuyuan
 * @create: 2019-03-06 11:31
 **/
@Data
@ApiModel("账户查询dto")
public class AccountQueryDto extends Query {

    /**
     * 账户id
     */
    @ApiModelProperty("账户id")
    private Long id;

    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 学校名称
     */
    @ApiModelProperty("学校名称")
    private String schoolName;

    /**
     * 用户类型 1 学生 2 老师 3 家长
     */
    @ApiModelProperty("用户类型 1 学生 2 老师 3 家长")
    private Byte userType = 1;

    /**
     * 用户信息id
     */
    @ApiModelProperty("用户信息id")
    private Long userId;

    /**
     * 用户姓名
     */
    @ApiModelProperty("用户姓名")
    private String userName;

    /**
     * 用户手机号
     */
    @ApiModelProperty("用户手机号")
    private String userPhone;

    /**
     * 用户学号
     */
    @ApiModelProperty("用户学号")
    private String cardNumber;

    /**
     * 用户登录名
     */
    @ApiModelProperty("用户登录名")
    private String loginName;

    /**
     * 修改登录名 1 未修改 2 已修改
     */
    @ApiModelProperty("修改登录名 1 未修改 2 已修改")
    private Byte loginNameUpdate;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 账户是否过期 1 正常 2 过期
     */
    @ApiModelProperty("账户是否过期 1 正常 2 过期")
    private Byte accountExpired;

    /**
     * 账户是否锁定 1 正常 2 锁定
     */
    @ApiModelProperty("账户是否锁定 1 正常 2 锁定")
    private Byte accountLocked;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createDate;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Date updateDate;

}
