package com.bdxh.product.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductUpdateDto implements Serializable {

    private static final long serialVersionUID = 1295579643785690138L;

    @NotNull(message = "商品主键不能为空")
    private Long id;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品展示名称
     */
    private String productShowName;

    /**
     * 商品定价
     */
    private BigDecimal productPrice;

    /**
     * 商品售价
     */
    private BigDecimal productSellPrice;

    /**
     * 商品描述
     */
    private String productDescription;

    /**
     * 商品类型 1 单品 2 套餐
     */
    private Byte productType=1;

    /**
     * 商品上下架状态 1 下架 2 上架
     */
    private Byte sellStatus=1;

    /**
     * 商品图片地址
     */
    private String imgUrl;

    /**
     * 业务类型 1 微校服务
     */
    private Byte businessType=1;

    /**
     * 备注
     */
    private String remark;

    /**
     * 套餐包含商品
     */
    private String productChildIds;

}