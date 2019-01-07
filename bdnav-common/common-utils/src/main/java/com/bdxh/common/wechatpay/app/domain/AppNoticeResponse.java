package com.bdxh.common.wechatpay.app.domain;

import com.bdxh.common.utils.CDATASectionAdapter;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

/**
 * @description: 微信app支付回调实体类
 * @author: xuyuan
 * @create: 2019-01-04 09:43
 **/
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppNoticeResponse implements Serializable {

    private static final long serialVersionUID = 4598522363009685874L;

    /**
     * 通信标识
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String return_code;

    /**
     * 返回信息
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String return_msg;

    /**
     * 应用id
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String appid;

    /**
     * 商户号
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String mch_id;

    /**
     * 设备号
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String device_info;

    /**
     * 随机字符串
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String nonce_str;

    /**
     * 签名
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String sign;

    /**
     * 业务结果
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String result_code;

    /**
     * 错误代码
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String err_code;

    /**
     * 错误描述
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String err_code_des;

    /**
     * 用户openid
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String openid;

    /**
     * 是否关注公众号
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String is_subscribe;

    /**
     * 交易类型 APP
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String trade_type;

    /**
     * 付款银行
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String bank_type;

    /**
     * 总金额
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String total_fee;

    /**
     * 货币种类
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String fee_type;

    /**
     * 现金金额
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String cash_fee;

    /**
     * 现金货币种类
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String cash_fee_type;

    /**
     * 代金券数量
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String coupon_count;

    /**
     * 代金券id
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String coupon_id_$n;

    /**
     * 单个代金券支付金额
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String coupon_fee_$n;

    /**
     * 微信支付订单号
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String transaction_id;

    /**
     * 商户订单号
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String out_trade_no;

    /**
     * 商家数据包
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String attach;

    /**
     * 支付完成时间
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String time_end;

}
