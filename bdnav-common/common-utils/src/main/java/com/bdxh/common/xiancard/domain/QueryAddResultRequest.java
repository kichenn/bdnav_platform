package com.bdxh.common.xiancard.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 充值结果查询
 * @author: xuyuan
 * @create: 2019-01-11 10:36
 **/
@Data
public class QueryAddResultRequest implements Serializable {

    private static final long serialVersionUID = 8545776484662421638L;

    /**
     * 商户号
     */
    private String merchant;

    /**
     * 订单号
     */
    private String orderid;

    /**
     * 签名
     */
    private String mac;

}
