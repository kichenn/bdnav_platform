package com.bdxh.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 支付成功后订单编号，查询订单下详情信息
 * @Author: Kang
 * @Date: 2019/6/21 12:22
 */
@Data
public class OrderItemVo1 {

    /**
     * 主键
     */
    private Long id;

    /**
     * 订单号
     */
    private Long orderNo;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 商品售价
     */
    private BigDecimal productSellPrice;

    /**
     * 商品类型 1 单品 2 套餐
     */
    private Byte productType;


    /**
     * 商品数量
     */
    private Byte productNum;

    /**
     * 商品其他信息
     */
    private String productExtra;

}
