package com.bdxh.common.wechatpay.common.domain;

import com.bdxh.common.utils.CDATASectionAdapter;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

/**
 * @description: 微信APP支付订单查询请求类
 * @author: xuyuan
 * @create: 2019-01-14 12:11
 **/
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderQueryRequest implements Serializable {

    private static final long serialVersionUID = 6970436863386413416L;

    /**
     * 应用ID
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String appid;

    /**
     * 商户号
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String mch_id;

    /**
     * 微信支付订单号
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String transaction_id;

    /**
     * 订单号
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String out_trade_no;

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

}
