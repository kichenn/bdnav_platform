package com.bdxh.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductAddDto implements Serializable {

    private static final long serialVersionUID = 1295579643785690138L;

    /**
     * 商品名称
     */
    @NotEmpty(message = "商品名称不能为空")
    @ApiModelProperty("商品名称")
    private String productName;

    /**
     * 商品展示名称
     */
    @NotEmpty(message = "商品展示名称不能为空")
    @ApiModelProperty("商品展示名称")
    private String productShowName;

    /**
     * 商品定价
     */
    @NotNull(message = "商品定价不能为空")
    @ApiModelProperty("商品定价")
    private BigDecimal productPrice;

    /**
     * 商品售价
     */
    @NotNull(message = "商品售价不能为空")
    @ApiModelProperty("商品售价")
    private BigDecimal productSellPrice;

    /**
     * 商品描述
     */
    @ApiModelProperty("商品描述")
    private String productDescription;

    /**
     * 商品类型 1 单品 2 套餐
     */
    @NotNull(message = "商品类型不能为空")
    @ApiModelProperty("商品类型 1 单品 2 套餐")
    private Byte productType;

    /**
     * 商品上下架状态 1 下架 2 上架
     */
    @NotNull(message = "商品上下架不能为空")
    @ApiModelProperty("商品上下架状态 1 下架 2 上架")
    private Byte sellStatus;

    /**
     * 商品图片地址
     */
    @NotEmpty(message = "商品图片地址不能为空")
    @ApiModelProperty("商品图片地址")
    private String imgUrl;

    /**
     * 业务类型 1 微校服务
     */
    @NotNull(message = "商品业务类型不能为空")
    @ApiModelProperty("业务类型 1 微校服务'")
    private Byte businessType;

    /**
     * 操作人
     */
    @ApiModelProperty("操作人")
    private Long operator;

    /**
     * 操作姓名
     */
    @ApiModelProperty("操作姓名")
    private String operatorName;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 套餐包含商品
     */
    @ApiModelProperty("套餐包含商品")
    private String productChildIds;


    @ApiModelProperty("图片详情")
    List<ProductImageAddDto> image;

}