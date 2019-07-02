package com.bdxh.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description: 商品列表返参vo
 * @Author: Kang
 * @Date: 2019/6/14 14:42
 */
@Data
public class ProductListVo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "商品展示名称")
    private String productShowName;

    @ApiModelProperty(value = "商品定价")
    private BigDecimal productPrice;

    @ApiModelProperty(value = "商品售价")
    private BigDecimal productSellPrice;

    @ApiModelProperty(value = "商品描述")
    private String productDescription;

    @ApiModelProperty(value = "商品类型 1 单品 2 套餐")
    private Byte productType;

    @ApiModelProperty(value = "商品上下架状态 1 下架 2 上架")
    private Byte sellStatus;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "图片信息")
    private List<String> imgUrl;

    @ApiModelProperty(value = "图片类型 1图标图片  2 商品详情信息")
    private Byte imgType;
}
