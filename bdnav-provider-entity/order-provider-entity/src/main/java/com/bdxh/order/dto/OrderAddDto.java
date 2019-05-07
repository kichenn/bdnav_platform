package com.bdxh.order.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("增加订单dto")
public class OrderAddDto {

    @ApiModelProperty("订单号")
    private Long OrderNo;


    @ApiModelProperty("第三方订单号")
    private String ThirdOrderNo;


    @ApiModelProperty("学校主键")
    private Long SchoolId;


    @ApiModelProperty("学校编码")
    private String SchoolCode;


    @ApiModelProperty("学校名称")
    private String SchoolName;


    @ApiModelProperty("家长主键")
    private Long UserId;


    @ApiModelProperty("学号")
    private String CardNumber;


    @ApiModelProperty("用户姓名")
    private String UserName;


    @ApiModelProperty("用户类型 1 学生 2 老师 3 家长")
    private Integer UserType;


    @ApiModelProperty("用户openid")
    private String OpenId;


    @ApiModelProperty("订单总金额")
    private BigDecimal TotalMoney;


    @ApiModelProperty("订单金额")
    private BigDecimal OrderMoney;


    @ApiModelProperty("支付金额")
    private BigDecimal PayMoney;


    @ApiModelProperty("交易状态 1 进行中 2 已取消 3 已删除 4 交易成功")
    private Integer TradeStatus;


    @ApiModelProperty("支付状态 1 未支付 2 支付中 3 支付成功 4 支付失败")
    private Integer PayStatus;


    @ApiModelProperty("业务状态 1 未处理 2 已处理")
    private Integer BusinessStatus;


    @ApiModelProperty("业务类型 1 校园钱包充值  2管控服务")
    private Integer BusinessType;


    @ApiModelProperty("支付渠道 1 微信支付")
    private Integer PayType;


    @ApiModelProperty("订单类型  1 JSAPI支付")
    private Integer TradeType;


    @ApiModelProperty("支付时间")
    private Date PayTime;


    @ApiModelProperty("支付结束时间")
    private Date PayEndTime;


    @ApiModelProperty("产品主键")
    private String ProductId;


    @ApiModelProperty("创建时间")
    private Date CreateDate;


    @ApiModelProperty("修改时间")
    private Date UpdateDate;


    @ApiModelProperty("操作人")
    private Long Operator;


    @ApiModelProperty("操作人姓名")
    private String OperatorName;


    @ApiModelProperty("备注")
    private String Remark;


}
