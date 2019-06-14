package com.bdxh.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
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

    @ApiModelProperty(value = "图片信息")
    private List<String> imgUrl;
}
