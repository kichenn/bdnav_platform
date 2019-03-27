package com.bdxh.system.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserQueryDto extends Query {

    /**
     * 用户id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String userName;

    /**
     * 部门id
     */
    @ApiModelProperty("部门id")
    private Long deptId;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String deptFullName;
    

    /**
     * 性别 1 男 2 女
     */
    @ApiModelProperty("性别 1 男 2 女")
    private Byte sex;


    @ApiModelProperty("名称")
    private String realName;

    /**
     * 电子邮件
     */
    @ApiModelProperty("手机")
    private String email;


    /**
     * 手机
     */
    @ApiModelProperty("手机")
    private String phone;

    /**
     * 状态 1 正常 2 锁定
     */
    @ApiModelProperty("状态 1 正常 2 锁定")
    private Byte status;

    /**
     * 类型 1 普通用户 2 管理员
     */
    @ApiModelProperty("类型 1 普通用户 2 管理员")
    private Byte type;

    /**
     * 出生日期
     */
    @ApiModelProperty("出生日期")
    private String birth;

    /**
     * 头像图片
     */
    @ApiModelProperty("头像图片")
    private String Image;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

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
