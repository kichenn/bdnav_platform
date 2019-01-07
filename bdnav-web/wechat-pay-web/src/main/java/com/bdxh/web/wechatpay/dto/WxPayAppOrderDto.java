package com.bdxh.web.wechatpay.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: 微信APP支付下单类
 * @author: xuyuan
 * @create: 2019-01-03 16:48
 **/
@Data
public class WxPayAppOrderDto implements Serializable {

    private static final long serialVersionUID = -6622403700115132981L;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    private Long userId;

    /**
     * 充值金额
     */
    @NotNull(message = "充值金额不能为空")
    @DecimalMin(value = "0.01",message = "充值金额不能小于0")
    private BigDecimal money;

    /**
     * 商品描述
     */
    @NotNull(message = "商品描述不能为空")
    private String productDetail;

    /**
     * 终端ip
     */
    @NotNull(message = "客户端ip不能为空")
    private String ip;

    /**
     * 签名
     */
    private String sign;

}
