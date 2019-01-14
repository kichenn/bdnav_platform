package com.bdxh.wallet.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 统一下单返回实体
 * @author: xuyuan
 * @create: 2019-01-03 18:47
 **/
@Data
public class WalletAppOrderVo implements Serializable {

    private static final long serialVersionUID = -1081644842316357856L;

    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 订单号
     */
    private Long orderNo;

    /**
     * 预支付会话标识
     */
    private String prepayId;

}
