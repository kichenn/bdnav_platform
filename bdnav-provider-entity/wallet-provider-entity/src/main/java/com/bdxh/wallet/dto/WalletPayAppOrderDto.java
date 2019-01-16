package com.bdxh.wallet.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: 微信APP支付下单类
 * @author: xuyuan
 * @create: 2019-01-03 16:48
 **/
@Data
public class WalletPayAppOrderDto implements Serializable {

    private static final long serialVersionUID = -7607447468261216863L;

    /**
     * 学校编码
     */
    @NotEmpty(message = "学校编码不能为空")
    private String schoolCode;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    private Long userId;

    /**
     * 姓名
     */
    @NotEmpty(message = "姓名不能为空")
    private String userName;

    /**
     * 学号
     */
    @NotEmpty(message = "学号不能为空")
    private String cardNumber;

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
    private String body;

    /**
     * 终端ip
     */
    @NotEmpty(message = "客户端ip不能为空")
    private String ip;

    /**
     * 签名
     */
    private String sign;

}
