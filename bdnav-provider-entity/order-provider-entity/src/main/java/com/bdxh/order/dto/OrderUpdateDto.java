package com.bdxh.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: 订单主信息dto
 * @author: xuyuan
 * @create: 2019-01-09 15:14
 **/
@Data
public class OrderUpdateDto implements Serializable {

    private static final long serialVersionUID = -8878327806119184802L;




    /**
     * 学校编码
     */
    @NotEmpty(message = "学校编码不能为空")
    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    @ApiModelProperty("用户id")
    private Long userId;

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
    @NotNull(message = "支付金额不能为空")
    @ApiModelProperty("支付金额")
    private BigDecimal payMoney;

    /**
     * 交易状态 1 进行中 2 已取消 3 已删除 4 交易成功'
     */
    @ApiModelProperty("交易状态 1 进行中 2 已取消 3 已删除 4 交易成功'")
    private Byte tradeStatus;

    /**
     * '支付状态 1 未支付 2 支付中 3 支付成功 4 支付失败'
     */
    @ApiModelProperty("支付状态 1 未支付 2 支付中 3 支付成功 4 支付失败")
    private Byte payStatus;



    /**
     * '业务状态 1 未处理 2 处理中 3 已处理'
     */
    @ApiModelProperty("'业务状态 1 未处理 2 处理中 3 已处理'")
    private Byte businessStatus;

    /**
     * 业务类型 1 微校付费服务
     */
    @NotNull(message = "业务类型不能为空")
    @ApiModelProperty("1 微校付费服务")
    private Byte businessType;


    /**
     * 支付渠道 1 微信支付
     */
    @NotNull(message = "支付类型不能为空")
    @ApiModelProperty("1 微信支付")
    private Byte payType;

    /**
     * 订单类型  1 JSAPI支付
     */
    @NotNull(message = "交易类型不能为空")
    @ApiModelProperty("1 JSAPI支付")
    private Byte tradeType;


    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;


//    @NotNull(message = "交易明细不能为空")
//    @Valid
//    List<OrderItemDto> items;
}
