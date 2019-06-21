package com.bdxh.order.dto;

import com.bdxh.common.base.enums.BusinessStatusEnum;
import com.bdxh.order.enums.OrderPayStatusEnum;
import com.bdxh.order.enums.OrderTradeStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;



/**
 * @Description:
 * @Author: Kang
 * @Date: 2019/6/19 18:22
 */
@Data
@ApiModel("购买商品，我方订单绑定第三方订单dto")
public class ModifyPayOrderDto {

    @ApiModelProperty("订单号")
    private Long orderNo;

    @ApiModelProperty("微信第三方订单号")
    private String thirdOrderNo;

    @ApiModelProperty("支付结束时间")
    private String payEndTime;

    @ApiModelProperty("交易状态 1 进行中 2 交易成功 3 已取消 4 已删除")
    private OrderTradeStatusEnum tradeStatus;

    @ApiModelProperty("支付状态 1 未支付 2 支付中 3 支付失败 4 支付成功")
    private OrderPayStatusEnum payStatus;

    @ApiModelProperty("业务状态 1 未处理 2 已处理")
    private BusinessStatusEnum businessStatus;
}
