package com.bdxh.common.wechatpay.js.JSentity;

import com.bdxh.common.utils.CDATASectionAdapter;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

/**
 * @description: 微信Jsapi支付回调返回类
 * @create: 2019-01-04 10:16
 **/
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class JSNoticeReturn implements Serializable {

    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String return_code;

    @XmlJavaTypeAdapter(CDATASectionAdapter.class)
    private String return_msg;

}
