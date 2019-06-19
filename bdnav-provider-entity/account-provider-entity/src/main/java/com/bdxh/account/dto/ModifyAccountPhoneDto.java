package com.bdxh.account.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author WanMing
 * @date 2019/6/18 14:47
 */
@Data
public class ModifyAccountPhoneDto {

    @ApiModelProperty("用户的学校编号")
    @NotBlank(message = "用户的学校编号不能为空")
    private String schoolCode;

    @ApiModelProperty("用户卡号")
    @NotBlank(message = "用户卡号不能为空")
    private String cardNumber;

    @ApiModelProperty("用户的旧手机号")
    @NotBlank(message = "用户的旧手机号不能为空")
    private String oldPhone;

    @ApiModelProperty("用户的新手机号")
    @NotBlank(message = "用户的新手机号不能为空")
    private String newPhone;

    @ApiModelProperty("验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;
}
