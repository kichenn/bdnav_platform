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

@Data
@ApiModel("购买商品，增加订单dto")
public class AddPayOrderDto {

    @ApiModelProperty("第三方订单号")
    private String thirdOrderNo;

    @ApiModelProperty("学号")
    @NotBlank(message = "学号不能为空")
    private String cardNumber;

    @ApiModelProperty("用户姓名")
    @NotBlank(message = "用户姓名不能为空")
    private String userName;

    @ApiModelProperty("商品描述")
    private String body;

    @ApiModelProperty("用户openid")
    @NotBlank(message = "用户的openid不能为空")
    private String openId;

    @ApiModelProperty("支付金额")
    @NotNull(message = "支付金额不能为空")
    private BigDecimal payMoney;

    @ApiModelProperty("支付时间")
    private Date payTime;

    @ApiModelProperty("产品主键")
    @NotBlank(message = "产品主键不能为空")
    private String productId;

    @ApiModelProperty("ip")
    private String ip;

    @ApiModelProperty("操作人")
    private Long operator;

    @ApiModelProperty("操作人姓名")
    private String operatorName;


    @ApiModelProperty("备注")
    private String remark;


}
