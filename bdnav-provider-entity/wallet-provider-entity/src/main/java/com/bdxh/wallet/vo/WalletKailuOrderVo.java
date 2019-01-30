package com.bdxh.wallet.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 凯路消费下单返回实体
 * @author: xuyuan
 * @create: 2019-01-25 18:47
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WalletKailuOrderVo implements Serializable {

    private static final long serialVersionUID = -8896230779882723940L;

    /**
     * 平台订单号
     */
    private Long orderNo;

    /**
     * 外部订单号
     */
    private String outOrderNo;

    /**
     * 签名
     */
    private String sign;

    private WalletKailuOrderVo walletKailuOrderVo;

}
