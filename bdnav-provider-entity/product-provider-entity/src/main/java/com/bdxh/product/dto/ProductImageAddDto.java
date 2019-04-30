package com.bdxh.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductImageAddDto implements Serializable {

    private static final long serialVersionUID = 5295579643785690138L;

    /**
     * 商品名称
     */
    @ApiModelProperty("应用id")
    private Long productId;

    /**
     * 图片地址
     */
    @ApiModelProperty("图片地址")
    private String imageUrl;

    /**
     * 图片名称
     */
    @ApiModelProperty("图片名称")
    private String imageName;

    /**
     * 图片顺序
     */
    @ApiModelProperty("图片顺序")
    private Byte sort;

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


}