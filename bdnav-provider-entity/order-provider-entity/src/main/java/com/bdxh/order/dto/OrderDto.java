package com.bdxh.order.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 订单主信息dto
 * @create: 2019-01-09 15:14
 **/
@Data
public class OrderDto extends Query {


    @ApiModelProperty(value = "订单号")
    private Long orderNo;


    @ApiModelProperty("第三方订单号")
    private String thirdOrderNo;


    @ApiModelProperty("学校主键")
    private Long schoolId;


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


    @ApiModelProperty("用户类型 1 学生 2 老师 3 家长")
    private Integer userType;


    @ApiModelProperty("用户openid")
    private String openId;


    @ApiModelProperty("订单总金额")
    private BigDecimal totalMoney;


    @ApiModelProperty("订单金额")
    private BigDecimal orderMoney;


    @ApiModelProperty("支付金额")
    private BigDecimal payMoney;


    @ApiModelProperty("交易状态 1 进行中 2 已取消 3 已删除 4 交易成功")
    private Integer tradeStatus;


    @ApiModelProperty("支付状态 1 未支付 2 支付中 3 支付成功 4 支付失败")
    private Integer payStatus;


    @ApiModelProperty("业务状态 1 未处理 2 已处理")
    private Integer businessStatus;


    @ApiModelProperty("业务类型 1 校园钱包充值  2管控服务")
    private Integer businessType;


    @ApiModelProperty("支付渠道 1 微信支付")
    private Integer payType;


    @ApiModelProperty("订单类型  1 JSAPI支付")
    private Integer tradeType;


    @ApiModelProperty("支付时间")
    private Date payTime;


    @ApiModelProperty("支付结束时间")
    private Date payEndTime;


    @ApiModelProperty("产品主键")
    private String productId;


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
