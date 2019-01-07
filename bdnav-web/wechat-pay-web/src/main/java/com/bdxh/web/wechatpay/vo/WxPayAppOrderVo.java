package com.bdxh.web.wechatpay.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 统一下单返回实体
 * @author: xuyuan
 * @create: 2019-01-03 18:47
 **/
@Data
public class WxPayAppOrderVo implements Serializable {

    private static final long serialVersionUID = -772080883439371393L;

    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 预支付会话标识
     */
    private String prepayId;

}
