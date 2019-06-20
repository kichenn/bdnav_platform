package com.bdxh.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


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
}
