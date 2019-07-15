package com.bdxh.wallet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @Description: 修改支付密码dto
 * @Author: Kang
 * @Date: 2019/7/12 14:27
 */
@Data
public class ModifyPayPwdDto {

    @NotEmpty(message = "学校code不能为空")
    @ApiModelProperty(value = "学校code")
    private String schoolCode;

    @NotEmpty(message = "cardNumber不能为空")
    @ApiModelProperty(value = "学号cardNumber")
    private String cardNumber;

    @Pattern(regexp = "^\\d{6}$", message = "请输入长度6位的原密码")
    @ApiModelProperty(value = "原密码")
    private String payPwd;

    @Pattern(regexp = "^\\d{6}$", message = "请输入长度6位的新密码")
    @ApiModelProperty(value = "新密码")
    private String newPayPwd;
}
