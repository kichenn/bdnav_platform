package com.bdxh.account.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @description: 增加账户dto
 * @author: xuyuan
 * @create: 2019-03-06 11:00
 **/
@Data
@ApiModel("修改账户dto")
public class UpdateAccountDto implements Serializable {

    private static final long serialVersionUID = 1513779018376801472L;

    /**
     * 学校编码
     */
    @NotEmpty(message = "学校编码不能为空")
    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 学校名称
     */
    @ApiModelProperty("学校名称")
    private String schoolName;

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
    @NotEmpty(message = "学号部门为空")
    @ApiModelProperty("用户学号")
    private String cardNumber;

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

}
