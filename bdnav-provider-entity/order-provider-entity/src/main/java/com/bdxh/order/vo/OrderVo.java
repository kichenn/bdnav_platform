package com.bdxh.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 生成订单返回类
 * @author: xuyuan
 * @create: 2019-01-09 15:44
 **/
@Data
public class OrderVo implements Serializable {

    private static final long serialVersionUID = 3244051781774832266L;


    /**
     * 订单编号
     */
    @ApiModelProperty("订单编号")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long orderNo;

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
     * 用户姓名
     */
    @ApiModelProperty("用户姓名")
    private String userName;

    /**
     * 学号
     */
    @ApiModelProperty("学号")
    private String cardNumber;



    /**
     * 订单总金额
     */
    @ApiModelProperty("订单总金额")
    private BigDecimal totalMoney;

    /**
     * 订单金额
     */
    @ApiModelProperty("订单金额")
    private BigDecimal orderMoney;

    /**
     * 支付金额
     */
    @ApiModelProperty("支付金额")
    private BigDecimal payMoney;

    /**
     * 交易状态 1 进行中 2 已取消 3 已删除 4 交易成功
     */
    @ApiModelProperty("交易状态 1 进行中 2 已取消 3 已删除 4 交易成功")
    private Byte tradeStatus;

    /**
     * 支付状态 1 未支付 2 支付中 3 支付成功 4 支付失败
     */
    @ApiModelProperty("支付状态 1 未支付 2 支付中 3 支付成功 4 支付失败")
    private Byte payStatus;


    /**
     * 支付时间
     */
    @ApiModelProperty("支付时间")
    private Date payTime;

    /**
     * 支付结束时间
     */
    @ApiModelProperty("支付结束时间")
    private Date payEndTime;


    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createDate;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;


    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;






}
