package com.bdxh.common.wechatpay.app.domain;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @description: 微信app支付统一下单响应类
 * @author: xuyuan
 * @create: 2019-01-02 19:26
 **/
@Data
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppOrderResponse implements Serializable {

    private static final long serialVersionUID = -8274320846631281936L;

    /**
     * 返回状态码 通信标识
     */
    private String return_code;

    /**
     * 校验返回信息
     */
    private String return_msg;

    /**
     * 应用ID
     */
    private String appid;

    /**
     * 商户号
     */
    private String mch_id;

    /**
     * 设备号 默认传入WEB
     */
    private String device_info;

    /**
     * 微信返回随机字符串
     */
    private String nonce_str;

    /**
     * 签名
     */
    private String sign;

    /**
     * 业务结果
     */
    private String result_code;

    /**
     * 错误码
     */
    private String err_code;

    /**
     * 错误消息
     */
    private String err_code_des;

    /**
     * 交易类型
     */
    private String trade_type;

    /**
     * 预支付交易会话标识
     */
    private String prepay_id;

}
