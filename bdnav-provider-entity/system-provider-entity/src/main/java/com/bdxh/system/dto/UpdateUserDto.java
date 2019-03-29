package com.bdxh.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import java.io.Serializable;

@Data
public class UpdateUserDto implements Serializable {

    private static final long serialVersionUID = 566703849379026270L;

    @ApiModelProperty("用户id")
    private Long id;

    /**
     * 头像图片
     */
    @ApiModelProperty("用户头像")
    private String image;

    /**
     * 文件地址
     */
    @ApiModelProperty("文件地址")
    private String ImgFileAddress;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 姓名
     */

    @ApiModelProperty("姓名")
    private String realName;
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String userName;

    /**
     * 性别 1 男 2 女
     */
    @ApiModelProperty("性别 1 男 2 女")
    private Byte sex;

    /**
     * 出生日期
     */
    @ApiModelProperty("出生日期")
    private String birth;

    /**
     * 手机
     */
    @ApiModelProperty("手机")
    private String phone;

    /**
     * 电子邮件
     */
    @ApiModelProperty("电子邮件")
    @Email
    private String email;

    /**
     * 部门id
     */
    @ApiModelProperty("部门id")
    private Long deptId;

    /**
     * 分配的角色id
     */
    @ApiModelProperty("分配的角色id")
    private String roleIds;

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
     * 操作人
     */
    @ApiModelProperty("操作人")
    private Long operator;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}
