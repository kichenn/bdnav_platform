package com.bdxh.account.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @Description: 修改账户dto
 * @Author: Kang
 * @Date: 2019/5/8 17:02
 */
@Data
@ApiModel("修改账户dto")
public class UpdateAccountDto implements Serializable {

    private static final long serialVersionUID = 1513779018376801472L;

    @NotEmpty(message = "学校编码不能为空")
    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("学校名称")
    private String schoolName;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("用户手机号")
    private String userPhone;

    @NotEmpty(message = "学号部门为空")
    @ApiModelProperty("用户学号")
    private String cardNumber;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("账户是否过期 1 正常 2 过期")
    private Byte accountExpired;

    @ApiModelProperty("账户是否锁定 1 正常 2 锁定")
    private Byte accountLocked;

}
