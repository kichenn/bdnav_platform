package com.bdxh.order.dto;

import com.bdxh.common.base.enums.BaseUserTypeEnum;
import com.bdxh.common.base.enums.BusinessStatusEnum;
import com.bdxh.order.enums.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 订单主信息dto
 * @author: xuyuan
 * @create: 2019-01-09 15:14
 **/
@Data
@ApiModel("更改订单dto")
public class OrderUpdateDto{


    @ApiModelProperty("订单号")
    @NotNull(message = "订单号不能为空")
    private Long orderNo;


    @ApiModelProperty("学校编码")
    private String schoolCode;


    @ApiModelProperty("学校名称")
    private String schoolName;


    @ApiModelProperty("家长主键")
    private Long userId;


    @ApiModelProperty("学号")
    private String cardNumber;


    @ApiModelProperty("用户姓名")
    private String userName;


    @ApiModelProperty("用户openid")
    private String openId;


    @ApiModelProperty("订单总金额")
    private BigDecimal totalMoney;


    @ApiModelProperty("订单金额")
    private BigDecimal orderMoney;


    @ApiModelProperty("支付金额")
    private BigDecimal payMoney;


    @ApiModelProperty("交易状态 1 进行中 2 交易成功 3 已取消 4 已删除")
    @NotNull(message = "交易状态不能为空")
    private OrderTradeStatusEnum tradeStatus;


    @ApiModelProperty("支付状态 1 未支付 2 支付中 3 支付失败 4 支付成功")
    @NotNull(message = "支付状态不能为空")
    private OrderPayStatusEnum payStatus;


    @ApiModelProperty("支付时间")
    private Date payTime;


    @ApiModelProperty("支付结束时间")
    private Date payEndTime;



    @ApiModelProperty("创建时间")
    private Date createDate;


    @ApiModelProperty("修改时间")
    private Date updateDate;


    @ApiModelProperty("操作人")
    private Long operator;


    @ApiModelProperty("操作人姓名")
    private String operatorName;


    @ApiModelProperty("备注")
    private String remark;


}
