package com.bdxh.wallet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @Description: 设置支付密码dto
 * @Author: Kang
 * @Date: 2019/7/12 11:22
 */
@Data
public class SetPayPwdDto {

    @ApiModelProperty(value = "学校code")
    private String schoolCode;

    @ApiModelProperty(value = "学号cardNumber")
    private String cardNumber;

//    @Pattern(regexp = "^\\d{6}$", message = "请输入长度位6为的数字密码")
    @ApiModelProperty(value = "密码")
    private String payPwd;
}
