package com.bdxh.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductUpdateDto implements Serializable {

    private static final long serialVersionUID = 1295579643785690138L;

    @NotNull(message = "商品主键不能为空")
    @ApiModelProperty("商品主键")
    private Long id;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品展示名称")
    private String productShowName;

    @ApiModelProperty("商品定价")
    private BigDecimal productPrice;

    @ApiModelProperty("商品售价")
    private BigDecimal productSellPrice;

    @ApiModelProperty("商品描述")
    private String productDescription;

    @ApiModelProperty("视频地址")
    private String videoUrl;

    @ApiModelProperty("商品类型 1 单品 2 套餐")
    private Byte productType;

    @ApiModelProperty("商品上下架状态 1 下架 2 上架")
    private Byte sellStatus;

    @ApiModelProperty("商品图片地址")
    private String imgUrl;

    @ApiModelProperty("业务类型 1 微校服务")
    private Byte businessType;

    @ApiModelProperty("操作人")
    private Long operator;

    @ApiModelProperty("操作姓名")
    private String operatorName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("套餐包含商品")
    private String productChildIds;

    @ApiModelProperty("图片详情")
    List<ProductImageUpdateDto> image;

}