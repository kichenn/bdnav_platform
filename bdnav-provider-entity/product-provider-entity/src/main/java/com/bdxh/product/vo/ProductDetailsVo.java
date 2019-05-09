package com.bdxh.product.vo;

import com.bdxh.product.entity.Product;
import com.bdxh.product.entity.ProductImage;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-09 11:26
 **/
@Data
public class ProductDetailsVo implements Serializable {

    private static final long serialVersionUID = 1964073671578729394L;
    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;
    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String productName;

    /**
     * 商品展示名称
     */
    @ApiModelProperty("商品展示名称")
    private String productShowName;

    /**
     * 商品定价
     */
    @ApiModelProperty("商品定价")
    private BigDecimal productPrice;

    /**
     * 商品售价
     */
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
    @ApiModelProperty("商品类型 1 单品 2 套餐")
    private Byte productType;

    /**
     * 商品上下架状态 1 下架 2 上架
     */
    @ApiModelProperty("商品上下架状态 1 下架 2 上架")
    private Byte sellStatus;

    /**
     * 商品图片地址
     */
    @ApiModelProperty("商品图片地址")
    private String imgUrl;

    /**
     * 业务类型 1 微校服务
     */
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
     * 创建时间
     */
    @ApiModelProperty(name = "创建时间")
    private Date createDate;

    /**
     * 修改时间
     */
    @ApiModelProperty(name = "修改时间")
    private Date updateDate;

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

    /**
     * 图片详情
     */
    @ApiModelProperty("图片详情")
    List<ProductImage> image;

    /**
     * 如果商品类型为套餐那么就有子商品集合
     */
    @ApiModelProperty("如果商品类型为套餐那么就有子商品集合")
    List<Product> productList;
}