package com.bdxh.common.xiancard.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 西安一卡通充值请求类
 * @author: xuyuan
 * @create: 2019-01-11 10:07
 **/
@Data
public class AddBalanceRequest implements Serializable {

    private static final long serialVersionUID = 4670411668537194356L;

    /**
     * 商户号
     */
    private String merchant;

    /**
     * 卡号
     */
    private String iccid;

    /**
     * 证件类型 1 学号 2 身份证
     */
    private String idtype;

    /**
     * 姓名
     */
    private String name;

    /**
     * 订单号
     */
    private String orderid;

    /**
     * 金额
     */
    private String amount;

    /**
     * 充值账号
     */
    private String account;

    /**
     * 签名
     */
    private String mac;

}
