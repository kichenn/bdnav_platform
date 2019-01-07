package com.bdxh.web.wechat.vo;


import com.google.gson.annotations.SerializedName;
import lombok.Data;
import java.io.Serializable;

/**
 * @description: JSAPI统一下单返回实体
 * @create: 2019-01-03 18:47
 **/
@Data
public class WxPayJsOrderVo implements Serializable {

    private static final long serialVersionUID = -772080883439371393L;

    //公众账号id
    private String appId;
    //时间戳
    private String timeStamp;
    //订单号
    private String nonceStr;
    //加密类型
    private String signType="MD5";
    //下单唯一标识
    private String packages;
    //签名
    private String paySign;




}
