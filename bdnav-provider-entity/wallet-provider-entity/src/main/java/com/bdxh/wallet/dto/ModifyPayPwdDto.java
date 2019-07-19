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

    @ApiModelProperty(value = "学校code")
    private String schoolCode;

    @ApiModelProperty(value = "学号cardNumber")
    private String cardNumber;

//    @Pattern(regexp = "^\\d{6}$", message = "请输入长度6位的原密码")
    @ApiModelProperty(value = "原密码")
    private String payPwd;

}
