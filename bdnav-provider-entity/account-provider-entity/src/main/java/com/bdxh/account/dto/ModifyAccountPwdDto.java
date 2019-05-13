package com.bdxh.account.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Description: 修改密码dto
 * @Author: Kang
 * @Date: 2019/5/13 18:30
 */
@Data
public class ModifyAccountPwdDto {

    @NotEmpty(message = "用户登录名不能为空")
    @ApiModelProperty("用户登录名")
    private String loginName;

    @NotEmpty(message = "用户密码不能为空")
    @ApiModelProperty("用户密码")
    private String pwd;

    @NotEmpty(message = "重复密码不能为空")
    @ApiModelProperty("重复密码")
    private String rePwd;
}
