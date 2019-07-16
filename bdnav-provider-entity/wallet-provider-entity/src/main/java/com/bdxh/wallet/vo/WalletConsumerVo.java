package com.bdxh.wallet.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author WanMing
 * @date 2019/7/12 17:10
 */
@Data
public class WalletConsumerVo {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 学校名称
     */
    @ApiModelProperty("学校名称")
    private String schoolName;


    /**
     * 学生姓名
     */
    @ApiModelProperty("学生姓名")
    private String userName;

    /**
     * 学号/工号/卡号
     */
    @ApiModelProperty("学号/工号/卡号")
    private String cardNumber;

    /**
     * 物理卡号（刷卡消费是不为空）
     */
    @ApiModelProperty("物理卡号（刷卡消费是不为空）")
    private String physicalNumber;

    /**
     * 收费部门id
     */
    @ApiModelProperty("收费部门id")
    private Long chargeDeptId;

    /**
     * 收费部门名称
     */
    @ApiModelProperty("收费部门名称")
    private String chargeDeptName;

    /**
     * 消费机的设备编码
     */
    @ApiModelProperty("消费机的设备编码")
    private String deviceNumber;

    /**
     * 消费时的设备名称
     */
    @ApiModelProperty("消费时的设备名称")
    private String deviceName;

    /**
     * 平台订单号
     */
    @ApiModelProperty("平台订单号")
    private String orderNo;

    /**
     * 外部设备生成的订单号
     */
    @ApiModelProperty("外部设备生成的订单号")
    private String outOrderNo;

    /**
     * 消费金额
     */
    @ApiModelProperty("消费金额")
    private BigDecimal consumerAmount;

    /**
     * 消费支付时间
     */
    @ApiModelProperty("消费支付时间")
    private Date consumerTime;


    /**
     * 支付状态 1 未扣款  2扣款成功  3 扣款中 4 扣款失败
     */
    @ApiModelProperty("支付状态 1 未扣款  2扣款成功  3 扣款中 4 扣款失败")
    private Byte consumerStatus;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createDate;

}
