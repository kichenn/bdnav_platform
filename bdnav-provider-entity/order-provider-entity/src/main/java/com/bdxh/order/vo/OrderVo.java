package com.bdxh.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 生成订单返回类
 * @Author: Kang
 * @Date: 2019/6/21 12:21
 */
@Data
public class OrderVo implements Serializable {

    private static final long serialVersionUID = 3244051781774832266L;

    @ApiModelProperty("订单编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderNo;

    @ApiModelProperty("第三方微信订单编号")
    private String thirdOrderNo;

    @ApiModelProperty("商品id")
    private String productId;

    @ApiModelProperty("学校id")
    private Long schoolId;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("学校名称")
    private String schoolName;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("学号")
    private String cardNumber;

    @ApiModelProperty("订单总金额")
    private BigDecimal totalMoney;

    @ApiModelProperty("订单金额")
    private BigDecimal orderMoney;

    @ApiModelProperty("支付金额")
    private BigDecimal payMoney;

    @ApiModelProperty("交易状态 1 进行中 2 已取消 3 已删除 4 交易成功")
    private Byte tradeStatus;

    @ApiModelProperty("支付状态 1 未支付 2 支付中 3 支付成功 4 支付失败")
    private Byte payStatus;

    @ApiModelProperty("支付时间")
    private Date payTime;

    @ApiModelProperty("支付结束时间")
    private Date payEndTime;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("备注")
    private String remark;


}
