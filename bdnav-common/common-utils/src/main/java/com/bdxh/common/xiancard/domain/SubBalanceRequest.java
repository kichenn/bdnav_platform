package com.bdxh.common.xiancard.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 西安一卡通消费请求类
 * @author: xuyuan
 * @create: 2019-01-25 18:37
 **/
@Data
public class SubBalanceRequest implements Serializable {

    private static final long serialVersionUID = 8350066816885530927L;

    /**
     * 商户号
     */
    private String merchant;

    /**
     * 证件类型 1 学号 2 身份证
     */
    private String idtype;

    /**
     * 学号
     */
    private String iccid;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 卡号
     */
    private String orderid;

    /**
     * 消费金额
     */
    private String amount;

    /**
     * 账号
     */
    private String account;

    /**
     * 签名
     */
    private String mac;


}
