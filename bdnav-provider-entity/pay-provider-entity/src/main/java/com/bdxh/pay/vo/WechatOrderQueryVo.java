package com.bdxh.pay.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 微信支付订单查询接口返回对象
 * @author: xuyuan
 * @create: 2019-01-18 14:23
 **/
@Data
public class WechatOrderQueryVo implements Serializable {

    private static final long serialVersionUID = 6373526098164651710L;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 第三方订单号
     */
    private String thirdOrderNo;

    /**
     * 支付结果
     */
    private String payResult;

    /**
     * 支付结束时间
     */
    private String timeEnd;

    /**
     * 签名
     */
    private String sign;

    /**
     * appId
     */
    private String appId;

    /**
     * nonce_str随机字符串
     */
    private String nonceStr;

}
