package com.bdxh.wallet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Description: 钱包账户充值
 * @Author: Kang
 * @Date: 2019/7/15 11:13
 */
@Data
public class ModifyAccountMoeny {

    @NotEmpty(message = "学校编码不能为空")
    @ApiModelProperty("学校编码")
    private String schoolCode;


    @NotEmpty(message = "学号不能为空")
    @ApiModelProperty("学号、工号")
    private String cardNumber;

    @NotNull(message = "账户金额不能为空")
    @ApiModelProperty("账户金额")
    private BigDecimal amount;
}
