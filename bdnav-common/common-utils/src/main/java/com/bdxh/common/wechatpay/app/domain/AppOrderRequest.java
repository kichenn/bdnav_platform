package com.bdxh.common.wechatpay.app.domain;

import com.bdxh.common.utils.CDATASectionAdapter;
import com.bdxh.common.utils.XmlUtils;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

/**
 * @description: 微信app支付统一下单请求类
 * @author: xuyuan
 * @create: 2019-01-02 16:23
 **/
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppOrderRequest implements Serializable {

    private static final long serialVersionUID = -7845539846581510598L;

    /**
     * 应用ID 必传
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String appid;

    /**
     * 商户号 必传
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String mch_id;

    /**
     * 设备号 默认WEB
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String device_info = "WEB";

    /**
     * 随机字符串 必传
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String nonce_str;

    /**
     * 签名
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String sign;

    /**
     * 签名类型 默认MD5
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String sign_type = "MD5";

    /**
     * 商品描述 必传
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String body;

    /**
     * 商品详情 非必须
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String detail;

    /**
     * 附加数据 非必须
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String attach;

    /**
     * 商户订单号 必传
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String out_trade_no;

    /**
     * 货币类型 默认CNY
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String fee_type = "CNY";

    /**
     * 总金额 单位分 必传
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String total_fee;

    /**
     * 终端的ip 必传
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String spbill_create_ip;

    /**
     * 交易起始时间
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String time_start;

    /**
     * 交易失效时间
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String time_expire;

    /**
     * 代金券优惠标记
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String goods_tag;

    /**
     * 通知地址 必传
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String notify_url;

    /**
     * 交易类型 默认APP
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String trade_type="APP";

    /**
     * 指定支付方式
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String limit_pay;

    /**
     * 开发票入口开放标识
     */
    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String receipt;

    public static void main(String[] args) {
        AppOrderRequest appOrderRequest = new AppOrderRequest();
        String str = XmlUtils.toXML(appOrderRequest);
        System.out.println(str);
        AppOrderRequest t=XmlUtils.fromXML(str, AppOrderRequest.class);
        System.out.println(str);
    }

}
