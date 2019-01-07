package com.bdxh.web.wechatpay.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description: 支付成功请求实体类
 * @author: xuyuan
 * @create: 2019-01-07 12:09
 **/
@Data
public class WxPayAppOkDto implements Serializable {

    private static final long serialVersionUID = -6780829862401561198L;

    /**
     * 订单号
     */
    @NotNull(message = "订单号不能为空")
    private Long orderNo;

    /**
     * 支付结果 1 成功 2 失败
     */
    @NotNull(message = "支付结果不能为空")
    private byte staus;

}
