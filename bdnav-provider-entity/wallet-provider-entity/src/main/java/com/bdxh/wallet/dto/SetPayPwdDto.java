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

    @NotEmpty(message = "学校code不能为空")
    @ApiModelProperty(value = "学校code")
    private String schoolCode;

    @NotEmpty(message = "cardNumber不能为空")
    @ApiModelProperty(value = "卡号cardNumber")
    private String cardNumber;

    @Pattern(regexp = "^\\d{6}$", message = "请输入长度位6为的数字密码")
    private String payPwd;
}
