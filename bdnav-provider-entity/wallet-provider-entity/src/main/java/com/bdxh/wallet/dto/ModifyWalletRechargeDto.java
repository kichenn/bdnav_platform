package com.bdxh.wallet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
* @Description:  修改充值记录 Dto
* @Author: Kang
* @Date: 2019/7/15 14:28
*/
@Data
public class ModifyWalletRechargeDto {


    @NotNull(message = "订单号我方不能为空")
    @ApiModelProperty(value = "订单号我方")
    private Long orderNo;

    @NotEmpty(message = "第三方订单号不能为空")
    @ApiModelProperty(value = "第三方订单号")
    private String outOrderNo;

    @NotNull(message = "充值状态不能为空")
    @ApiModelProperty(value = "充值状态  1 未支付 2 支付中 3 支付成功 4 支付失败")
    private Byte rechargeStatus;

}
