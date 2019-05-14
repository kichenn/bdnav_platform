package com.bdxh.account.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Description: 找回密码dto
 * @Author: Kang
 * @Date: 2019/5/14 9:43
 */
@Data
public class ForgetPwd {

    @NotEmpty(message = "手机号不能为空")
    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("登录名")
    private String loginName;

    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String pwd;

    @NotEmpty(message = "重复密码不能为空")
    @ApiModelProperty("重复密码")
    private String rePwd;

    @NotEmpty(message = "验证码不能为空")
    @ApiModelProperty("验证码")
    private String captcha;
}
