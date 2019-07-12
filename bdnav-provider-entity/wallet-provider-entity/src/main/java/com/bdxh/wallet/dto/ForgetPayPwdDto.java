package com.bdxh.wallet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @Description: 忘记密码
 * @Author: Kang
 * @Date: 2019/7/12 17:37
 */
@Data
public class ForgetPayPwdDto {

    @NotEmpty(message = "学校code不能为空")
    @ApiModelProperty(value = "学校code")
    private String schoolCode;

    @NotEmpty(message = "cardNumber不能为空")
    @ApiModelProperty(value = "学号cardNumber")
    private String cardNumber;

    @Pattern(regexp = "^\\d{6}$", message = "请输入长度6位的原密码")
    @ApiModelProperty(value = "原密码")
    private String payPwd;

    @NotEmpty(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码")
    private String code;

    @NotEmpty(message = "手机号码不能为空")
    @ApiModelProperty(value = "手机号码（无需传递，后台获取）")
    private String phone;
}
