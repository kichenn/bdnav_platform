package com.bdxh.wallet.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: JSAPI统一下单返回实体
 * @create: 2019-01-03 18:47
 **/
@Data
public class WalletJsOrderVo implements Serializable {

    private static final long serialVersionUID = 2193066521865054219L;

    /**
     * 应用id
     */
    private String appId;

    /**
     * 时间戳
     */
    private String timeStamp;

    /**
     * 随机字符串
     */
    private String nonceStr;

    /**
     * 加密类型 默认MD5
     */
    private String signType="MD5";

    /**
     * 下单唯一标识
     */
    private String packages;

    /**
     * 签名
     */
    private String paySign;

}
