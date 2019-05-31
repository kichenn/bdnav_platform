package com.bdxh.pay.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: 前台微信APP支付下单类
 * @create: 2019-01-03 16:48
 **/
@Data
@ApiModel("前台微信APP支付下单类")
public class WxPayJsOrderDto implements Serializable {

    private static final long serialVersionUID = -6622403700115132981L;

    @NotEmpty(message = "订单号不能为空")
    @ApiModelProperty("订单号")
    private String orderNo;

    @NotNull(message = "充值金额不能为空")
    @DecimalMin(value = "0.01", message = "充值金额不能小于0")
    @ApiModelProperty("充值金额")
    private BigDecimal money;

    @NotEmpty(message = "商品描述不能为空")
    @ApiModelProperty("商品描述")
    private String body;

    @NotEmpty(message = "客户端ip不能为空")
    @ApiModelProperty("终端ip")
    private String ip;

    @NotEmpty(message = "微信openid不能为空")
    @ApiModelProperty("微信唯一标识")
    private String openid;

}
