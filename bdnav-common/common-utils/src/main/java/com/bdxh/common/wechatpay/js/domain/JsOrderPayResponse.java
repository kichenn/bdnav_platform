package com.bdxh.common.wechatpay.js.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 前端H5调起支付 返参
 * @Author: Kang
 * @Date: 2019/6/19 17:16
 */
@Data
public class JsOrderPayResponse {

    @ApiModelProperty("公众号id")
    private String appId;

    @ApiModelProperty("时间戳")
    private String timeStamp;

    @ApiModelProperty("随机字符串")
    private String nonceStr;

    @ApiModelProperty("订单详情扩展字符串")
    private String packages;

    @ApiModelProperty("签名方式")
    private String signType = "MD5";

    @ApiModelProperty("签名")
    private String paySign;
}
