package com.bdxh.order.dto;


import com.bdxh.common.base.enums.BaseUserTypeEnum;
import com.bdxh.common.base.enums.BusinessStatusEnum;
import com.bdxh.order.enums.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel("增加订单dto")
public class AddOrderDto {

    @ApiModelProperty("订单号")
    private Long orderNo;


    @ApiModelProperty("第三方订单号")
    @NotBlank(message = "第三方订单号不能为空")
    private String thirdOrderNo;


    @ApiModelProperty("学校主键")
    private Long schoolId;


    @ApiModelProperty("学校编码")
    private String schoolCode;


    @ApiModelProperty("学校名称")
    private String schoolName;


    @ApiModelProperty("家长主键")
    @NotNull(message = "家长的主键不能为空")
    private Long userId;


    @ApiModelProperty("学号")
    @NotBlank(message = "学号不能为空")
    private String cardNumber;


    @ApiModelProperty("用户姓名")
    @NotBlank(message = "用户姓名不能为空")
    private String userName;


    @ApiModelProperty("用户类型 1 学生 2 老师 3 家长")
    @NotNull(message = "用户类型不能为空")
    private BaseUserTypeEnum userType;


    @ApiModelProperty("用户openid")
    @NotBlank(message = "用户的openid不能为空")
    private String openId;


    @ApiModelProperty("订单总金额")
    @NotNull(message = "订单总金额不能为空")
    private BigDecimal totalMoney;


    @ApiModelProperty("订单金额")
    @NotNull(message = "订单金额不能为空")
    private BigDecimal orderMoney;


    @ApiModelProperty("支付金额")
    @NotNull(message = "支付金额不能为空")
    private BigDecimal payMoney;


    @ApiModelProperty("交易状态 1 进行中 2 交易成功 3 已取消 4 已删除")
    @NotNull(message = "交易状态不能为空")
    private OrderTradeStatusEnum tradeStatus;


    @ApiModelProperty("支付状态 1 未支付 2 支付中 3 支付失败 4 支付成功")
    @NotNull(message = "支付状态不能为空")
    private OrderPayStatusEnum payStatus;


    @ApiModelProperty("业务状态 1 未处理 2 已处理")
    @NotNull(message = "业务状态不能为空")
    private BusinessStatusEnum businessStatus;


    @ApiModelProperty("业务类型 1 校园钱包充值  2管控服务")
    @NotNull(message = "业务类型不能为空")
    private OrderBusinessTypeEnum businessType;


    @ApiModelProperty("支付渠道 1 微信支付")
    @NotNull(message = "支付渠道不能为空")
    private OrderPayTypeEnum payType;


    @ApiModelProperty("订单类型  1 JSAPI支付")
    @NotNull(message = "订单类型不能为空")
    private OrderTradeTypeEnum tradeType;


    @ApiModelProperty("支付时间")
    private Date payTime;


    @ApiModelProperty("支付结束时间")
    private Date payEndTime;


    @ApiModelProperty("产品主键")
    @NotBlank(message = "产品主键不能为空")
    private String productId;



    @ApiModelProperty("操作人")
    private Long operator;


    @ApiModelProperty("操作人姓名")
    private String operatorName;


    @ApiModelProperty("备注")
    private String remark;




}
