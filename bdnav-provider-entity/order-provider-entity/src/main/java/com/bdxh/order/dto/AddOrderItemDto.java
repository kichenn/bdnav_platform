package com.bdxh.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author WanMing
 * @date 2019/6/4 9:44
 */
@Data
public class AddOrderItemDto {


    @ApiModelProperty("订单编号")
    @NotNull(message = "订单编号不能为空")
    private Long OrderNo;


    @ApiModelProperty("商品id")
    private Long productId;


    @ApiModelProperty("商品名称")
    private String productName;


    @ApiModelProperty("商品价格")
    private BigDecimal productPrice;


    @ApiModelProperty("商品售价")
    private BigDecimal productSellPrice;


    @ApiModelProperty("商品数量")
    private Byte productNum;


    @ApiModelProperty("商品其他信息")
    private String productExtra;
}
