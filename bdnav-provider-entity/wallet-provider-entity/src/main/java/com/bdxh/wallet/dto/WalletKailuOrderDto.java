package com.bdxh.wallet.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 凯路消费下单类
 * @author: xuyuan
 * @create: 2019-01-25 16:48
 **/
@Data
public class WalletKailuOrderDto implements Serializable {

    private static final long serialVersionUID = -5199510361933545564L;

    /**
     * 外部订单号
     */
    @NotEmpty(message = "订单号不能为空")
    private String outOrderNo;

    /**
     * 消费金额
     */
    @NotNull(message = "消费金额不能为空")
    @DecimalMin(value = "0.01",message = "充值金额不能小于0")
    private BigDecimal payMoney;

    /**
     * 学校编码
     */
    @NotEmpty(message = "学校编码不能为空")
    private String schoolCode;

    /**
     * 学校名称
     */
    @NotEmpty(message = "学校名称不能为空")
    private String schoolName;

    /**
     * 学生姓名
     */
    @NotEmpty(message = "学生姓名不能为空")
    private String name;

    /**
     * 学号
     */
    @NotEmpty(message = "学号不能为空")
    private String cardNumber;

    /**
     * 设备类型
     */
    @NotNull(message = "设置类型不能为空")
    private Byte deviceType;

    /**
     * 设备id
     */
    @NotNull(message = "设备id不能为空")
    private Long deviceId;

    /**
     * 设备名称
     */
    @NotEmpty(message = "设备名称不能为空")
    private String deviceName;

    /**
     * 消费时间
     */
    @NotNull(message = "消费时间不能为空")
    private Date consumptionTime;

    /**
     * 应用id
     */
    @NotEmpty(message = "应用id不能为空")
    private String appId;

    /**
     * 商户号
     */
    @NotEmpty(message = "商户号不能为空")
    private String mchId;

    /**
     * 随机字符串
     */
    @NotEmpty(message = "随机字符串不能为空")
    private String noticeStr;

    /**
     * 时间戳
     */
    @NotNull(message = "时间戳不能为空")
    private Date timeStamp;

    /**
     * 签名
     */
    @NotEmpty(message = "签名不能为空")
    private String sign;

}
