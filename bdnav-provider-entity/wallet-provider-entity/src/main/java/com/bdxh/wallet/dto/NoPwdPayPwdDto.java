package com.bdxh.wallet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * @Description: 设置免密支付
 * @Author: Kang
 * @Date: 2019/7/12 11:59
 */
@Data
public class NoPwdPayPwdDto {

    @NotEmpty(message = "学校code不能为空")
    @ApiModelProperty(value = "学校code")
    private String schoolCode;

    @NotEmpty(message = "cardNumber不能为空")
    @ApiModelProperty(value = "学号cardNumber")
    private String cardNumber;

    @Pattern(regexp = "^\\d{6}$", message = "请输入长度6位的数字密码")
    private String payPwd;

    @ApiModelProperty(value = "免密支付金额")
    private BigDecimal quickPayMoney;

    @ApiModelProperty(value = "操作人id")
    private Long operator;

    @ApiModelProperty(value = "操作人姓名")
    private String operatorName;

    @ApiModelProperty(value = "备注")
    private String remark;
}
