package com.bdxh.xiancard.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 一卡通请求参数类
 */
public class MetroCardDto implements Serializable {

    private static final long serialVersionUID = 7707761438040681434L;

    /**
     * 学校编码
     */
    @NotNull(message = "学校编码不能为空")
    private String schoolCode;

    /**
     * 学校编码
     */
    @NotNull(message = "卡号不能为空")
    private String cardNumber;


    /**
     * 证件类型
     */
    @NotNull(message = "卡号不能为空")
    private String idtype;

    /**
     * 学生唯一标识
     */
    @NotNull(message = "学生标识不能为空")
    private String iccid;


    /**
     *名字
     */
    @NotNull(message = "名字不能为空")
    private String userName;

    /**
     * 订单编号
     */
    @NotNull(message = "订单编号不能为空")
    private String orderid;

    /**
     * 充值金额
     */
    @NotNull(message = "充值金额不能为空")
    private String amount;

    /**
     * 充值账号
     */
    private String account;

    /**
     * 安全效验码
     */

    private String mac;




}
