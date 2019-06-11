package com.bdxh.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author WanMing
 * @date 2019/6/3 14:21
 */
@Data
public class OrderItemVo {

    /**
     * 主键
     */
    @ApiModelProperty(name = "订单明细主键")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 订单号
     */
    @ApiModelProperty(name = "订单编号")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long orderNo;




    /**
     * 商品名称
     */
    @ApiModelProperty(name = "商品名称")
    private String productName;

    /**
     * 商品价格
     */
    @ApiModelProperty(name = "商品价格")
    private BigDecimal productPrice;

    /**
     * 商品售价
     */
    @ApiModelProperty(name = "商品售价")
    private BigDecimal productSellPrice;

    /**
     * 商品类型 1 单品 2 套餐
     */
    @ApiModelProperty(name = "商品类型 1 单品 2 套餐")
    private Byte productType;


    /**
     * 商品数量
     */
    @ApiModelProperty(name = "商品数量")
    private Byte productNum;

    /**
     * 商品其他信息
     */
    @ApiModelProperty(name = "商品其他信息")
    private String productExtra;


    /**
     * 创建时间
     */
    @ApiModelProperty(name = "创建时间")
    private Date createDate;

    /**
     * 备注
     */
    @ApiModelProperty(name = "备注")
    private String remark;
}
