package com.bdxh.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;


@Data
public class AddUserDto implements Serializable {

    private static final long serialVersionUID = 7421251545883628226L;

    /**
     * id
     */
    @ApiModelProperty("用户id")
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    @NotEmpty(message = "用户名不能为空")
    private String userName;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    @NotEmpty(message = "密码不能为空")
    private String password;


    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String realName;

    /**
     * 性别 1 男 2 女
     */
    @ApiModelProperty("性别 1 男 2 女")
    @NotNull(message = "性别不能为空")
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
    @NotEmpty(message = "手机不能为空")
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
     * 状态 1 正常 2 锁定
     */
    @ApiModelProperty("状态 1 正常 2 锁定")
    private Byte status = 1;

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
     * 分配的角色id
     */
    @ApiModelProperty("分配的角色ids")
    private String roleIds;

    /**
     * 类型 1 普通用户 2 管理员
     */
    @ApiModelProperty("类型 1 普通用户 2 管理员")
    private Byte type = 1;

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
    @ApiModelProperty("操作人")
    private Long operator;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}
