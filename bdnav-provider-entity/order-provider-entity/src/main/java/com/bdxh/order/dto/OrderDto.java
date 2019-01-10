package com.bdxh.order.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @description: 订单主信息dto
 * @author: xuyuan
 * @create: 2019-01-09 15:14
 **/
@Data
public class OrderDto implements Serializable {

    private static final long serialVersionUID = -8878327806119184802L;

    /**
     * 学校编码
     */
    @NotEmpty(message = "学校编码不能为空")
    private String schoolCode;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    private Long userId;

    /**
     * 姓名
     */
    @NotEmpty(message = "姓名不能为空")
    @Column(name = "user_name")
    private String userName;

    /**
     * 学号
     */
    @Column(name = "card_number")
    @NotEmpty(message = "学号不能为空")
    private String cardNumber;

    /**
     * 订单总金额
     */
    private BigDecimal totalMoney;

    /**
     * 订单金额
     */
    private BigDecimal orderMoney;

    /**
     * 支付金额
     */
    @NotNull(message = "支付金额不能为空")
    private BigDecimal payMoney;

    /**
     * 渠道类型 1 自有渠道
     */
    @NotNull(message = "渠道类型不能为空")
    private Byte channelType;

    /**
     * 业务类型 1 微校付费服务
     */
    @NotNull(message = "业务类型不能为空")
    private Byte businessType;

    /**
     * 支付渠道 1 微信支付
     */
    @NotNull(message = "支付类型不能为空")
    private Byte payType;

    /**
     * 订单类型  1 JSAPI支付
     */
    @NotNull(message = "交易类型不能为空")
    private Byte tradeType;

    /**
     * 备注
     */
    private String remark;

    @NotNull(message = "交易明细不能为空")
    @Valid
    List<OrderItemDto> items;
}
