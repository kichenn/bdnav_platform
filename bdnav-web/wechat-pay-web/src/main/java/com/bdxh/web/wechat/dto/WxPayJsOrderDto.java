package com.bdxh.web.wechat.dto;

import lombok.Data;

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

    /**
     * 用户id
     */
    @NotNull
    private Long userId;

    /**
     * 充值金额
     */
    private BigDecimal money;

    /**
     * 商品描述
     */
    private String productDetail;

    /**
     * 终端ip
     */
    private String ip;

    /**
     * 微信唯一标识
     */
    private String openid;

    /**
     * 签名
     */
    private String sign;

}
