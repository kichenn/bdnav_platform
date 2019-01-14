package com.bdxh.pay.dto;

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
public class WxPayJsOrderDto implements Serializable {

    private static final long serialVersionUID = -6622403700115132981L;

    @NotEmpty(message = "订单号不能为空")
    private String orderNo;

    /**
     * 充值金额
     */
    @NotNull(message = "充值金额不能为空")
    @DecimalMin(value = "0.01",message = "充值金额不能小于0")
    private BigDecimal money;

    /**
     * 商品描述
     */
    @NotEmpty(message = "商品描述不能为空")
    private String productDetail;

    /**
     * 终端ip
     */
    @NotEmpty(message = "客户端ip不能为空")
    private String ip;

    /**
     * 微信唯一标识
     */
    @NotEmpty(message = "微信openid不能为空")
    private String openid;

    /**
     * 签名
     */
    private String sign;

}
